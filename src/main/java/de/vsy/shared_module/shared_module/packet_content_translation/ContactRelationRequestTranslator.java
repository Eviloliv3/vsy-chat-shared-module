package de.vsy.shared_module.shared_module.packet_content_translation;

import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationRequestDTO;

public
class ContactRelationRequestTranslator extends PacketContentTranslatorBase {

    /**
     * @param relationDTO the relationData to translate
     *
     * @return the translation in the form of a string
     */
    public
    String translate (ContactRelationRequestDTO relationDTO) {
        var friendshipRequest = new StringBuilder();
        var contactData = relationDTO.getContactData();

        if (contactData != null) {
            var contactName = contactData.getDisplayLabel();

            if (relationDTO.getDesiredState()) {
                friendshipRequest.append(contactName)
                                 .append(" möchte mit Ihnen befreundet sein.");
            } else {
                friendshipRequest.append("Sie sind nicht mehr mit ")
                                 .append(contactName)
                                 .append(" befreundet");
            }
        } else {
            friendshipRequest = null;
        }

        return (friendshipRequest != null) ? friendshipRequest.toString() : null;
    }
}
