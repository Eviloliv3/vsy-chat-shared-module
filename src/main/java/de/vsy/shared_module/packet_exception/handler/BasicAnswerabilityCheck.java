package de.vsy.shared_module.packet_exception.handler;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.shared_transmission.packet.property.communicator.EligibleCommunicationEntity;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

import static de.vsy.shared_transmission.packet.property.packet_category.PacketCategory.NOTIFICATION;

public class BasicAnswerabilityCheck {

    public static boolean checkPacketAnswerable(Packet toCheck) {
        ContentIdentifier identifier;
        CommunicationEndpoint senderEntity;
        final var answerable = true;
        var properties = toCheck.getPacketProperties();

        if (properties != null) {
            senderEntity = properties.getSender();

            if ((senderEntity != null) && (senderEntity.getEntity()
                    .equals(EligibleCommunicationEntity.CLIENT))) {
                identifier = properties.getPacketIdentificationProvider();

                if ((identifier != null) && (identifier.getPacketCategory().equals(NOTIFICATION))) {
                    return !answerable;
                }
                return answerable;
            }
        }
        return !answerable;
    }
}
