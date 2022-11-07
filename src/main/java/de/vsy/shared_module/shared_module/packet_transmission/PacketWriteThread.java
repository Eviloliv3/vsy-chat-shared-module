/*
 *
 */
package de.vsy.shared_module.shared_module.packet_transmission;

import de.vsy.shared_module.shared_module.packet_management.InputBuffer;
import de.vsy.shared_module.shared_module.packet_transmission.cache.UnconfirmedPacketTransmissionCache;
import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_utility.logging.ThreadContextRunnable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reads Packet from a PacketBuffer to a ObjectOutputStream.
 */
class PacketWriteThread extends ThreadContextRunnable {

  private static final Logger LOGGER = LogManager.getLogger();
  private final UnconfirmedPacketTransmissionCache packetCache;
  private final InputBuffer queueAccess;
  private ObjectOutputStream outStream;

  /**
   * Instantiates a new write thread.
   *
   * @param out         the out
   * @param queueAccess the queue accessLimiter
   * @param packetCache the PacketCache
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public PacketWriteThread(final UnconfirmedPacketTransmissionCache packetCache,
      final OutputStream out,
      final InputBuffer queueAccess) throws IOException {
    this.queueAccess = queueAccess;
    this.packetCache = packetCache;
    setupOutputStream(out);
  }

  /**
   * Sets the up output stream.
   *
   * @param out the new up output stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void setupOutputStream(final OutputStream out) throws IOException {
    this.outStream = new ObjectOutputStream(out);
  }

  @Override
  public void runWithContext() {
    Packet input;

    while (!Thread.currentThread().isInterrupted()) {
      input = readNextPacket();

      if (input != null) {

        if (input.getPacketContent() != null) {
          this.packetCache.appendPacket(input);
        }
        writeContent(input);
      }
    }
  }

  private Packet readNextPacket() {
    Packet input = null;

    try {
      input = this.queueAccess.getPacket();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for next Packet.");
    }
    return input;
  }

  /**
   * Write content.
   *
   * @param output the output
   */
  private void writeContent(final Packet output) {

    try {
      this.outStream.writeObject(output);
      this.outStream.flush();
      LOGGER.trace("Packet written: {}", output);
    } catch (final IOException ioe) {
      Thread.currentThread().interrupt();
      LOGGER.error("IOException: connection aborted.");
    }
  }
}
