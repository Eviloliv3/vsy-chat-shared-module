package de.vsy.chat.shared_module.packet_validation.content_validation.authentication;

import de.vsy.chat.shared_module.data_element_validation.BeanChecker;
import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.authentication.NewAccountRequestDTO;

import java.util.ArrayList;

public
class NewAccountRequestValidator
        extends BasePacketContentValidator<NewAccountRequestDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Kontörstellungsanfrage. ";

    public
    NewAccountRequestValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    NewAccountRequestDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var newAccountContent = super.castContent(NewAccountRequestDTO.class,
                                                        inputContent);

        final var errorStrings = new ArrayList<String>();
        var accountCreation = newAccountContent.getAccountCreationData();

        if (accountCreation == null) {
            errorStrings.add("Keine Daten gefunden.");
        } else {
            final var personalData = accountCreation.getPersonalData();
            final var authenticationData = accountCreation.getAuthenticationData();
            var checkString = BeanChecker.checkBean(personalData);

            if (checkString != null) {
                errorStrings.add(checkString);
            }
            checkString = BeanChecker.checkBean(authenticationData);

            if (checkString != null) {
                errorStrings.add(checkString);
            }
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return newAccountContent;
    }
}
