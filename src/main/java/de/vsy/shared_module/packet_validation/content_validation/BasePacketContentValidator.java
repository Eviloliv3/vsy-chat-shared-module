package de.vsy.shared_module.packet_validation.content_validation;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_transmission.packet.content.PacketContent;

import java.util.List;

public abstract class BasePacketContentValidator<T extends PacketContent> implements
        PacketContentValidator<T> {

    protected final String standardErrorMessage;

    protected BasePacketContentValidator(final String standardErrorMessage) {
        this.standardErrorMessage = standardErrorMessage;
    }

    protected T castContent(Class<? extends T> expectedClass, PacketContent inputContent)
            throws PacketValidationException {

        if (expectedClass.isInstance(inputContent)) {
            return expectedClass.cast(inputContent);
        } else {
            throw new PacketValidationException(
                    InvalidContentMessageCreator.createIllegalTypeMessage(expectedClass, inputContent));
        }
    }

    protected String createErrorMessage(final List<String> failureMessages) {
        var errorMessageBuilder = new StringBuilder();

        errorMessageBuilder.append(standardErrorMessage);

        for (final var currentMessage : failureMessages) {
            errorMessageBuilder.append(currentMessage);
        }
        return errorMessageBuilder.toString();
    }
}
