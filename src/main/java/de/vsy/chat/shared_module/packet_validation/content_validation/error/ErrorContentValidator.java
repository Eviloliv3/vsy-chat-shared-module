package de.vsy.chat.shared_module.packet_validation.content_validation.error;

import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.error.ErrorDTO;

import java.util.ArrayList;

import static de.vsy.chat.shared_module.data_element_validation.StringCheck.checkString;

public
class ErrorContentValidator extends BasePacketContentValidator<ErrorDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Fehlernachricht. ";

    public
    ErrorContentValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ErrorDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var errorContent = super.castContent(ErrorDTO.class, inputContent);
        final var errorStrings = new ArrayList<String>();
        final var errorMessage = errorContent.getMessage();
        final var checkString = checkString(errorMessage);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return errorContent;
    }
}
