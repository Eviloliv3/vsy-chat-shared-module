package de.vsy.shared_module.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.authentication.LoginResponseDTO;

import java.util.ArrayList;

public
class LoginResponseValidator extends BasePacketContentValidator<LoginResponseDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Login-Antwort. ";

    public
    LoginResponseValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    LoginResponseDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var loginContent = super.castContent(LoginResponseDTO.class,
                                                   inputContent);
        final var errorStrings = new ArrayList<String>();
        final var communicatorData = loginContent.getClientData();
        var checkString = BeanChecker.checkBean(communicatorData);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return loginContent;
    }
}
