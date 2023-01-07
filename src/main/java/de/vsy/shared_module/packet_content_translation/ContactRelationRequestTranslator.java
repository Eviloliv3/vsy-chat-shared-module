package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationRequestDTO;

public class ContactRelationRequestTranslator {

    private ContactRelationRequestTranslator() {
    }

    /**
     * @param relationRequest the translatable relation data
     * @return the translation in the form of a string
     */
    public static String translate(Translatable relationRequest) {
        var friendshipRequest = new StringBuilder();

        if (relationRequest instanceof final ContactRelationRequestDTO requestDTO) {
            var contactData = requestDTO.getRequestingClient();

            if (contactData != null) {
                var contactName = contactData.getDisplayLabel();

                if (requestDTO.getDesiredState()) {
                    friendshipRequest.append(contactName).append(" wants to be your friend.");
                } else {
                    friendshipRequest.append(contactName).append(" has upended your friendship.");
                }
            } else {
                friendshipRequest = null;
            }
        }

        return (friendshipRequest != null) ? friendshipRequest.toString() : null;
    }
}
