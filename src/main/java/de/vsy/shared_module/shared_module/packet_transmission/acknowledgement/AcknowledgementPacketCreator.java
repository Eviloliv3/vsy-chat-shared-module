package de.vsy.shared_module.shared_module.packet_transmission.acknowledgement;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.shared_transmission.packet.PacketImpl;
import de.vsy.shared_transmission.shared_transmission.packet.property.PacketPropertiesBuilder;

public
class AcknowledgementPacketCreator {

    private
    AcknowledgementPacketCreator () {}

    public static
    Packet createAcknowledgement (Packet receivedPacket) {
        var receivedProperties = receivedPacket.getPacketProperties();
        var properties = new PacketPropertiesBuilder().withSender(
                                                              receivedProperties.getRecipient())
                                                      .withRecipient(
                                                              receivedProperties.getSender())
                                                      .withIdentifier(
                                                              new AcknowledgementIdentifier(
                                                                      AcknowledgementType.PACKET_RECEIVED))
                                                      .build();
        return new PacketImpl(properties, null, receivedPacket.getPacketHash());
    }
}
