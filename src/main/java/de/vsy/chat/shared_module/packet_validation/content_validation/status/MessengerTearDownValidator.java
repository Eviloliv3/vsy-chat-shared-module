package de.vsy.chat.shared_module.packet_validation.content_validation.status;

import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.status.MessengerTearDownDTO;

public
class MessengerTearDownValidator
        extends BasePacketContentValidator<MessengerTearDownDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Messenger-Abbruchsantwort. ";

    public
    MessengerTearDownValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    MessengerTearDownDTO castAndValidateContent (PacketContent input)
    throws PacketValidationException {
        return super.castContent(MessengerTearDownDTO.class, input);
    }
}
