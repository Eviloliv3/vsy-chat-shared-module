package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationResponseDTO;

public class ContactRelationResponseTranslator {

  private ContactRelationResponseTranslator() {
  }

  public static String translate(Translatable responseData) {
    var friendshipResponse = new StringBuilder();

    if (responseData instanceof final ContactRelationResponseDTO responseDTO) {
      var contactData = responseDTO.getRespondingClient();

      if (contactData != null) {
        var contactName = contactData.getDisplayLabel();
        final var requestData = responseDTO.getRequestData();

        if (requestData.getDesiredState() && !(responseDTO.getDecision())) {
          friendshipResponse.append(contactName).append(" möchte nicht mit Ihnen befreundet sein.");
        } else if (requestData.getDesiredState() && responseDTO.getDecision()) {
          friendshipResponse.append("Sie sind jetzt mit ").append(contactName)
              .append(" befreundet");
        }
      } else {
        friendshipResponse = null;
      }
    }

    return (friendshipResponse != null) ? friendshipResponse.toString() : null;
  }
}
