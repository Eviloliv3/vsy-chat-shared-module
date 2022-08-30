package de.vsy.shared_module.shared_module.packet_creation.identification_provider;

import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.ChatIdentifier;

import static de.vsy.shared_transmission.shared_transmission.packet.property.packet_type.ChatType.TEXT_MESSAGE;

public
class ChatIdentificationProvider extends AbstractIdentificationProvider {

    {
        identifiers.put(TextMessageDTO.class,
                        () -> new ChatIdentifier(TEXT_MESSAGE));
    }
}
