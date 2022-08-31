package de.vsy.shared_module.shared_module.packet_validation.content_validation.chat;

import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;

import java.util.ArrayList;

import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;
import static de.vsy.shared_module.shared_module.data_element_validation.StringCheck.checkString;

public final
class TextMessageValidator extends BasePacketContentValidator<TextMessageDTO> {

    private static final String STANDARD_ERROR_MESSAGE =
            "Textnachricht wurde " + "nicht verarbeitet. ";

    public
    TextMessageValidator () {
        super(STANDARD_ERROR_MESSAGE);
    }

    @Override
    public
    TextMessageDTO castAndValidateContent (PacketContent input)
    throws PacketValidationException {
        final var errorStrings = new ArrayList<String>();
        final var textContent = super.castContent(TextMessageDTO.class, input);
        final var chatMessage = textContent.getMessage();
        var checkString = checkData(textContent.getOriginatorId());

        checkString.ifPresent(errorStrings::add);

        checkString = checkString(chatMessage);

        checkString.ifPresent(errorStrings::add);

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return textContent;
    }
}
