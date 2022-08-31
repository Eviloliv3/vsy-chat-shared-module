package de.vsy.shared_module.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.authentication.LoginRequestDTO;

import java.util.ArrayList;

public
class LoginRequestValidator extends BasePacketContentValidator<LoginRequestDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Login-Anfrage. ";

    public
    LoginRequestValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    LoginRequestDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var loginContent = super.castContent(LoginRequestDTO.class,
                                                   inputContent);
        final var errorStrings = new ArrayList<String>();
        final var loginData = loginContent.getAuthenticationData();
        var checkString = BeanChecker.checkBean(loginData);

        checkString.ifPresent(errorStrings::add);

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return loginContent;
    }
}
