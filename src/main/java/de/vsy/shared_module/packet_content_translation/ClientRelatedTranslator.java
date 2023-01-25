package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationResponseDTO;

public class ClientRelatedTranslator {

  private ClientRelatedTranslator() {
  }

  public static String translate(Translatable clientRelated) {
    if (clientRelated instanceof final ClientRelatedTranslatable<?> translatable) {
      PacketContent toTranslate = translatable.getContent();

      if (toTranslate instanceof final ContactRelationResponseDTO responseDTO) {
        return translate(translatable.getClientData(), responseDTO);
      }
    }
    return null;
  }

  public static String translate(final CommunicatorDTO clientData,
      final ContactRelationResponseDTO relationResponse) {
    String friendshipResponse = null;
    CommunicatorDTO contactData;
    final var requestData = relationResponse.getRequestData();
    final var clientIsOriginator = requestData.getRequestingClient().getCommunicatorId()
        == clientData.getCommunicatorId();

    if (clientIsOriginator) {
      contactData = relationResponse.getRespondingClient();
    } else {
      contactData = requestData.getRequestingClient();
    }

    if (requestData.getDesiredState() && !(relationResponse.getDecision())) {
      friendshipResponse = "You are no longer friends with " + contactData.getDisplayLabel() + ".";
    } else if (requestData.getDesiredState() && relationResponse.getDecision()) {
      friendshipResponse = "You are now friends with " + contactData.getDisplayLabel() + ".";
    }
    return friendshipResponse;
  }
}
