package de.vsy.shared_module.packet_validation.content_validation.status;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.status.MessengerTearDownDTO;

public class MessengerTearDownValidator extends BasePacketContentValidator<MessengerTearDownDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Invalid messenger tear down object. ";

    public MessengerTearDownValidator() {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public MessengerTearDownDTO castAndValidateContent(PacketContent input)
            throws PacketValidationException {
        return super.castContent(MessengerTearDownDTO.class, input);
    }
}
