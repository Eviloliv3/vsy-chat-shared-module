package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.PacketBuilder;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.PacketPropertiesBuilder;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public class NonStaticPacketCompiler {

    private OriginatingEntityProvider originator;
    private ContentIdentificationProvider contentIdentificationProvider;

    public void addOriginatorEntityProvider(OriginatingEntityProvider originator) {
        this.originator = originator;
    }

    public void addContentIdentificationProvider(
            ContentIdentificationProvider identificationProvider) {
        this.contentIdentificationProvider = identificationProvider;
    }

    /**
     * Creates a response Packet ready for dispatch, from the specified data. The recipient will be
     * determined by examining the specified request Packet. Best practice is to derive the
     * ContentIdentifier from the PacketContent.
     *
     * @param data    the PacketContent
     * @param request the request
     * @return Packet
     * @throws NullPointerException  if one of the arguments is null or the request argument has no
     *                               properties/sender entity/packet hash
     * @throws IllegalStateException if no OriginatingEntityProvider was set or no
     *                               ContentIdentificationProvider was set
     */
    public Packet createResponse(PacketContent data, Packet request) {
        var properties = request.getPacketProperties();
        var recipient = properties.getSender();
        var requestPacketHash = request.getPacketHash();

        return compilePacket(recipient, data, requestPacketHash);
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
    private Packet compilePacket(CommunicationEndpoint recipient, PacketContent data,
                                 String requestPacketHash) {
        var sender = originator.getOriginatorEntity();
        var props = new PacketPropertiesBuilder().withSender(sender).withRecipient(recipient)
                .withIdentifier(contentIdentificationProvider.getContentIdentifier(data)).build();
        return new PacketBuilder().withContent(data).withProperties(props)
                .withRequestPacket(requestPacketHash).build();
    }

    /**
     * Creates a request Packet ready for dispatch, from the specified data. The originator will be
     * set per instance. Best practice is to derive the ContentIdentifier from the PacketContent.
     *
     * @param recipient CommunicationEndpoint
     * @param data      PacketContent
     * @return Packet
     * @throws NullPointerException  if one of the arguments is null
     * @throws IllegalStateException if no OriginatingEntityProvider was set or no
     *                               ContentIdentificationProvider was set
     */
    public Packet createRequest(CommunicationEndpoint recipient, PacketContent data) {
        return compilePacket(recipient, data, null);
    }

    /**
     * Creates a response Packet ready for dispatch, from specified data. The request Packet hash
     * value will be determined by examining the request packet.
     *
     * @param recipient CommunicationEndpoint
     * @param data      PacketContent
     * @param request   Packet
     * @return Paket
     * @throws NullPointerException  if one of the parameters is null.
     * @throws IllegalStateException if no OriginatingEntityProvider was set or no
     *                               ContentIdentificationProvider was set
     */
    public Packet createCustomRecipientResponse(CommunicationEndpoint recipient, PacketContent data,
                                                Packet request) {
        var requestHash = request.getRequestPacketHash();

        return compilePacket(recipient, data, requestHash);
    }
}
