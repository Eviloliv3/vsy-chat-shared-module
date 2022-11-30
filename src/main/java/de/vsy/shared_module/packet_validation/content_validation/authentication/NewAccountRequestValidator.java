package de.vsy.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.authentication.NewAccountRequestDTO;
import java.util.ArrayList;

public class NewAccountRequestValidator extends BasePacketContentValidator<NewAccountRequestDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Invalid account creation request. ";

  public NewAccountRequestValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public NewAccountRequestDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var newAccountContent = super.castContent(NewAccountRequestDTO.class, inputContent);

    final var errorStrings = new ArrayList<String>();
    var accountCreation = newAccountContent.getAccountCreationData();

    if (accountCreation == null) {
      errorStrings.add("No credentials or personal data specified.");
    } else {
      final var personalData = accountCreation.getPersonalData();
      final var authenticationData = accountCreation.getAuthenticationData();
      var checkString = BeanChecker.checkBean(personalData);

      checkString.ifPresent(errorStrings::add);

      checkString = BeanChecker.checkBean(authenticationData);

      checkString.ifPresent(errorStrings::add);
    }

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return newAccountContent;
  }
}
