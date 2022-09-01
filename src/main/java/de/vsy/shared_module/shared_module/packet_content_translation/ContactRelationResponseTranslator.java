package de.vsy.shared_module.shared_module.packet_content_translation;

import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationResponseDTO;

public
class ContactRelationResponseTranslator extends PacketContentTranslatorBase {

    public
    String translate (ContactRelationResponseDTO relationDTO) {
        var friendshipResponse = new StringBuilder();
        var contactData = relationDTO.getRespondingClient();

        if (contactData != null) {
            var contactName = contactData.getDisplayLabel();
            final var requestData = relationDTO.getRequestData();

            if (requestData.getDesiredState() && !relationDTO.getDecision()) {
                friendshipResponse.append(contactName)
                                  .append(" möchte nicht mit Ihnen befreundet sein.");
            } else if (requestData.getDesiredState() && relationDTO.getDecision()) {
                friendshipResponse.append("Sie sind jetzt mit ")
                                  .append(contactName)
                                  .append(" befreundet");
            }
        } else {
            friendshipResponse = null;
        }

        return (friendshipResponse != null) ? friendshipResponse.toString() : null;
    }
}
