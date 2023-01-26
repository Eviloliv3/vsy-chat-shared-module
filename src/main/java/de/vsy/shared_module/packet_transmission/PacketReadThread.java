package de.vsy.shared_module.packet_transmission;

import de.vsy.shared_module.packet_creation.ContentIdentificationProviderImpl;
import de.vsy.shared_module.packet_creation.PacketCompiler;
import de.vsy.shared_module.packet_management.OutputBuffer;
import de.vsy.shared_module.packet_management.ThreadPacketBufferLabel;
import de.vsy.shared_module.packet_management.ThreadPacketBufferManager;
import de.vsy.shared_module.packet_transmission.acknowledgement.AcknowledgementPacketCreator;
import de.vsy.shared_module.packet_transmission.cache.UnconfirmedPacketTransmissionCache;
import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.PacketBuilder;
import de.vsy.shared_transmission.packet.content.notification.SimpleInformationDTO;
import de.vsy.shared_transmission.packet.property.PacketPropertiesBuilder;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.shared_utility.logging.ThreadContextRunnable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint.getClientEntity;
import static de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint.getServerEntity;
import static de.vsy.shared_utility.standard_value.StandardIdProvider.STANDARD_CLIENT_ID;
import static de.vsy.shared_utility.standard_value.StandardIdProvider.STANDARD_SERVER_ID;

/**
 * Reads Packet from an ObjectInputStream and writes them to a PacketBuffer.
 */
public class PacketReadThread extends ThreadContextRunnable {

  protected static final Logger LOGGER = LogManager.getLogger();
  private final UnconfirmedPacketTransmissionCache packetCache;
  private OutputBuffer inboundBuffer;
  private ObjectInputStream inStream;
  private OutputBuffer outboundBuffer;

  /**
   * Instantiates a new read thread.
   *
   * @param input       the input
   * @param ioBuffers   the io buffers
   * @param packetCache the PacketCache
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public PacketReadThread(final UnconfirmedPacketTransmissionCache packetCache,
      final InputStream input,
      final ThreadPacketBufferManager ioBuffers) throws IOException {

    this.packetCache = packetCache;
    setupIOChannels(input, ioBuffers);
  }

  /**
   * Lists the up input stream.
   *
   * @param inputStream the input stream
   * @param ioBuffers   the io buffers
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void setupIOChannels(final InputStream inputStream,
      final ThreadPacketBufferManager ioBuffers)
      throws IOException {
    this.outboundBuffer = ioBuffers.getPacketBuffer(ThreadPacketBufferLabel.OUTSIDE_BOUND);
    this.inboundBuffer = ioBuffers.getPacketBuffer(ThreadPacketBufferLabel.HANDLER_BOUND);
    this.inStream = new ObjectInputStream(inputStream);
  }

  @Override
  public void runWithContext() {

    while (!(Thread.currentThread().isInterrupted())) {
      Packet input = readNextPacket();

      if (input != null) {
        processIncomingPacket(input);
      }
    }
  }

  /**
   * Read next Packet
   *
   * @return the packet
   */
  private Packet readNextPacket() {

    for (int remainingTries = 3; remainingTries > 0; remainingTries--) {
      Object readObject = null;

      try {
        readObject = this.inStream.readObject();
      } catch (final IOException ioe) {
        Thread.currentThread().interrupt();
        LOGGER.error("IOException: connection aborted.");
        break;
      } catch (final ClassNotFoundException cnf) {
        LOGGER.error("Error! Invalid PacketType.\n{}", cnf.getMessage());
        return createUnknownPacketResponse(cnf);
      }

      if (readObject instanceof Packet readPacket) {
        return readPacket;
      }
    }
    return null;
  }

  /**
   * Send reception confirmation.
   *
   * @param input the input
   */
  protected void processIncomingPacket(final Packet input) {

    if (input.getPacketContent() != null) {
      final var confirmationPacket = AcknowledgementPacketCreator.createAcknowledgement(input);
      this.outboundBuffer.prependPacket(confirmationPacket);
      LOGGER.trace("Response created: {}", confirmationPacket.getRequestPacketHash());
      this.inboundBuffer.appendPacket(input);
      LOGGER.trace("Packet received: {}", input);
    } else {
      this.packetCache.removePacket(input.getRequestPacketHash());
      LOGGER.trace("Response received: {}", input.getRequestPacketHash());
    }
  }

  protected Packet createUnknownPacketResponse(ClassNotFoundException cnf){
    var errorContent = new SimpleInformationDTO("Unknown Packet formats will not be processed. " + cnf.getMessage());
    var contentIdentifier = new ContentIdentificationProviderImpl();
    var errorProperties = new PacketPropertiesBuilder().withSender(getServerEntity(STANDARD_SERVER_ID))
            .withRecipient(getClientEntity(STANDARD_CLIENT_ID))
            .withIdentifier(contentIdentifier.getContentIdentifier(errorContent))
            .build();
    return new PacketBuilder().withContent(errorContent).withProperties(errorProperties).build();
  }
}
