package de.vsy.chat.shared_module.packet_creation.identification_provider;

import de.vsy.chat.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.ChatIdentifier;

import static de.vsy.chat.shared_transmission.packet.property.packet_type.ChatType.TEXT_MESSAGE;

public
class ChatIdentificationProvider extends AbstractIdentificationProvider {

    {
        identifiers.put(TextMessageDTO.class,
                        () -> new ChatIdentifier(TEXT_MESSAGE));
    }
}
