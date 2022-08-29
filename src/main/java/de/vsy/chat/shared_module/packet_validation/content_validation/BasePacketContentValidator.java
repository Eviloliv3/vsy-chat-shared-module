package de.vsy.chat.shared_module.packet_validation.content_validation;

import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;

import java.util.List;

import static de.vsy.chat.shared_module.packet_validation.content_validation.InvalidContentMessageCreator.createIllegalTypeMessage;

public abstract
class BasePacketContentValidator<T extends PacketContent>
        implements PacketContentValidator<T> {

    protected final String standardErrorMessage;

    protected
    BasePacketContentValidator (final String standardErrorMessage) {
        this.standardErrorMessage = standardErrorMessage;
    }

    protected
    T castContent (Class<? extends T> expectedClass, PacketContent inputContent)
    throws PacketValidationException {

        if (expectedClass.isInstance(inputContent)) {
            return expectedClass.cast(inputContent);
        } else {
            throw new PacketValidationException(
                    createIllegalTypeMessage(expectedClass, inputContent));
        }
    }

    protected
    String createErrorMessage (final List<String> failureMessages) {
        var errorMessageBuilder = new StringBuilder();

        errorMessageBuilder.append(standardErrorMessage);

        for (final var currentMessage : failureMessages) {
            errorMessageBuilder.append(currentMessage);
        }
        return errorMessageBuilder.toString();
    }
}
