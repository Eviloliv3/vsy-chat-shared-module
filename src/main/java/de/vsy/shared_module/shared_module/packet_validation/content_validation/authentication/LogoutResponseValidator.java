package de.vsy.shared_module.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.authentication.LogoutResponseDTO;

public
class LogoutResponseValidator extends BasePacketContentValidator<LogoutResponseDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Logout-Antwort. ";

    public
    LogoutResponseValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    LogoutResponseDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        return super.castContent(LogoutResponseDTO.class, inputContent);
    }
}
