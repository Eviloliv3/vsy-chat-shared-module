package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.PacketBuilder;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.PacketPropertiesBuilder;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public class PacketCompiler {

  private static OriginatingEntityProvider originatorProvider;
  private static ContentIdentificationProvider contentIdentificationProvider;

  private PacketCompiler() {
  }

  public static void addOriginatorEntityProvider(OriginatingEntityProvider originator) {
    PacketCompiler.originatorProvider = originator;
  }

  public static OriginatingEntityProvider getOriginatorEntityProvider() {
    return PacketCompiler.originatorProvider;
  }

  public static void addContentIdentificationProvider(
      ContentIdentificationProvider identificationProvider) {
    PacketCompiler.contentIdentificationProvider = identificationProvider;
  }

  /**
   * Creates a response Packet ready for dispatch, from the specified data. The recipient will be
   * determined by examining the specified request Packet. Best practice is to derive the
   * ContentIdentifier from the PacketContent.
   *
   * @param data    the PacketContent
   * @param request the request
   * @return Packet
   * @throws NullPointerException  if one of the parameters is null, request argument has no
   *                               properties/sender entity/packet hash
   * @throws IllegalStateException if no OriginatingEntityProvider was set or no
   *                               ContentIdentificationProvider was set
   */
  public static Packet createResponse(PacketContent data, Packet request) {
    var properties = request.getPacketProperties();
    var sender = PacketCompiler.originatorProvider.getOriginatorEntity();
    var recipient = properties.getSender();
    var requestPacketHash = request.getPacketHash();

    return compilePacket(sender, recipient, data, requestPacketHash);
  }

  /**
   * Helper that compiles the specified arguments in a Packet.
   *
   * @param recipient         CommunicationEndpoint
   * @param data              PacketContent
   * @param requestPacketHash String
   * @return Packet
   * @throws IllegalStateException if no OriginatingEntityProvider was set or no
   *                               ContentIdentificationProvider was set
   */
  private static Packet compilePacket(CommunicationEndpoint sender, CommunicationEndpoint recipient,
      PacketContent data, String requestPacketHash) {
    var props = new PacketPropertiesBuilder().withSender(sender).withRecipient(recipient)
        .withIdentifier(PacketCompiler.contentIdentificationProvider.getContentIdentifier(data))
        .build();
    return new PacketBuilder().withContent(data).withProperties(props)
        .withRequestPacket(requestPacketHash).build();
  }

  /**
   * Creates a request Packet ready for dispatch, from the specified data.
   *
   * @param recipient CommunicationEndpoint
   * @param data      PacketContent
   * @return Packet
   * @throws NullPointerException  if one of the arguments is null
   * @throws IllegalStateException if no OriginatingEntityProvider was set or no
   *                               ContentIdentificationProvider was set
   */
  public static Packet createRequest(CommunicationEndpoint recipient, PacketContent data) {
    var sender = PacketCompiler.originatorProvider.getOriginatorEntity();
    return compilePacket(sender, recipient, data, null);
  }

  /**
   * Creates a Packet ready to dispatch, from the specified data.
   *
   * @param recipient CommunicationEndpoint
   * @param data      PacketContent
   * @return Packet
   * @throws NullPointerException  if one of the arguments is null
   * @throws IllegalStateException if no OriginatingEntityProvider was set or no
   *                               ContentIdentificationProvider was set
   */
  public static Packet createFollowUpRequest(CommunicationEndpoint recipient, PacketContent data,
      Packet request) {
    var sender = request.getPacketProperties().getSender();
    var requestHash = request.getRequestPacketHash();

    return compilePacket(sender, recipient, data, requestHash);
  }
}
