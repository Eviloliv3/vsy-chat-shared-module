package de.vsy.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.authentication.AccountDeletionRequestDTO;

public class AccountDeletionRequestValidator extends
        BasePacketContentValidator<AccountDeletionRequestDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Invalid account deletion request. ";

    public AccountDeletionRequestValidator() {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public AccountDeletionRequestDTO castAndValidateContent(PacketContent input)
            throws PacketValidationException {
        return super.castContent(AccountDeletionRequestDTO.class, input);
    }
}
