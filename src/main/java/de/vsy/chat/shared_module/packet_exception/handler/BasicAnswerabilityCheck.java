package de.vsy.chat.shared_module.packet_exception.handler;

import de.vsy.chat.shared_transmission.packet.Packet;
import de.vsy.chat.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.chat.shared_transmission.packet.property.communicator.EligibleCommunicationEntity;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

import static de.vsy.chat.shared_transmission.packet.property.packet_category.PacketCategory.ERROR;

public
class BasicAnswerabilityCheck {

    public static
    boolean checkPacketAnswerable (Packet toCheck) {
        ContentIdentifier identifier;
        CommunicationEndpoint senderEntity;
        final var answerable = true;
        var properties = toCheck.getPacketProperties();

        if (properties != null) {
            senderEntity = properties.getSenderEntity();

            if ((senderEntity != null) && (senderEntity.getEntity()
                                                       .equals(EligibleCommunicationEntity.CLIENT))) {
                identifier = properties.getContentIdentifier();

                if ((identifier != null) &&
                    (identifier.getPacketCategory().equals(ERROR))) {
                    return !answerable;
                }
                return answerable;
            }
        }
        return !answerable;
    }
}
