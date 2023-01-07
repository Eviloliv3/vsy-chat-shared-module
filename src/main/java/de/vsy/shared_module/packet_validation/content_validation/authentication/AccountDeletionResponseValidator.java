package de.vsy.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.authentication.AccountDeletionResponseDTO;

public class AccountDeletionResponseValidator extends
        BasePacketContentValidator<AccountDeletionResponseDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Invalid account deletion response. ";

    public AccountDeletionResponseValidator() {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public AccountDeletionResponseDTO castAndValidateContent(PacketContent input)
            throws PacketValidationException {
        return super.castContent(AccountDeletionResponseDTO.class, input);
    }
}
