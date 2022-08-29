package de.vsy.chat.shared_module.packet_validation.content_validation.authentication;

import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.authentication.ReconnectResponseDTO;

public
class ReconnectResponseValidator
        extends BasePacketContentValidator<ReconnectResponseDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Reconnect-Antwort. ";

    public
    ReconnectResponseValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ReconnectResponseDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        return super.castContent(ReconnectResponseDTO.class, inputContent);
    }
}
