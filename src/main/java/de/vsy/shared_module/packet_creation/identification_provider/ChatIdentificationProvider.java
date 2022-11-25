package de.vsy.shared_module.packet_creation.identification_provider;

import static de.vsy.shared_transmission.packet.property.packet_type.ChatType.TEXT_MESSAGE;

import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.packet.property.packet_identifier.ChatIdentifier;

public class ChatIdentificationProvider extends AbstractIdentificationProvider {

  {
    identifiers.put(TextMessageDTO.class, () -> new ChatIdentifier(TEXT_MESSAGE));
  }
}
