package de.vsy.chat.shared_module.packet_transmission.acknowledgement;

import de.vsy.chat.shared_transmission.packet.Packet;
import de.vsy.chat.shared_transmission.packet.PacketImpl;
import de.vsy.chat.shared_transmission.packet.property.PacketPropertiesBuilder;

import static de.vsy.chat.shared_module.packet_transmission.acknowledgement.AcknowledgementType.PACKET_RECEIVED;

public
class AcknowledgementPacketCreator {

    private
    AcknowledgementPacketCreator () {}

    public static
    Packet createAcknowledgement (Packet receivedPacket) {
        var receivedProperties = receivedPacket.getPacketProperties();
        var properties = new PacketPropertiesBuilder().withSender(
                                                              receivedProperties.getRecipientEntity())
                                                      .withRecipient(
                                                              receivedProperties.getSenderEntity())
                                                      .withIdentifier(
                                                              new AcknowledgementIdentifier(
                                                                      PACKET_RECEIVED))
                                                      .build();
        return new PacketImpl(properties, null, receivedPacket.getPacketHash());
    }
}
