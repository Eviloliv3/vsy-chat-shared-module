package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationRequestDTO;

public class ContactRelationRequestTranslator {

  private ContactRelationRequestTranslator() {
  }

  public static String translate(Translatable relationRequest) {
    String friendshipRequest = null;
    if (relationRequest instanceof ContactRelationRequestDTO requestData) {
      final var contactData = requestData.getRequestingClient();

      if (requestData.getDesiredState()) {
        friendshipRequest = contactData.getDisplayLabel() + " wants to be your friend.";
      } else {
        friendshipRequest = contactData.getDisplayLabel() + " has upended your friendship.";
      }
    }
    return friendshipRequest;
  }
}
