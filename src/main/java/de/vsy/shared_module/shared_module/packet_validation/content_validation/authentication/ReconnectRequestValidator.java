package de.vsy.shared_module.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.authentication.ReconnectRequestDTO;

import java.util.ArrayList;

public
class ReconnectRequestValidator
        extends BasePacketContentValidator<ReconnectRequestDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Reconnect-Anfrage. ";

    public
    ReconnectRequestValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ReconnectRequestDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var errorStrings = new ArrayList<String>();
        final var reconnectContent = super.castContent(ReconnectRequestDTO.class,
                                                       inputContent);
        final var communicatorData = reconnectContent.getClientData();
        var checkString = BeanChecker.checkBean(communicatorData);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }

        return reconnectContent;
    }
}
