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
          friendshipRequest.append(contactName).append(" möchte mit Ihnen befreundet sein.");
        } else {
          friendshipRequest.append("Sie sind nicht mehr mit ").append(contactName)
              .append(" befreundet");
        }
      } else {
        friendshipRequest = null;
      }
    }

    return (friendshipRequest != null) ? friendshipRequest.toString() : null;
  }
}
