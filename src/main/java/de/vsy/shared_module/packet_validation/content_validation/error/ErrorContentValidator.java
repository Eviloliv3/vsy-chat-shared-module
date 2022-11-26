package de.vsy.shared_module.packet_validation.content_validation.error;

import static de.vsy.shared_module.data_element_validation.StringCheck.checkString;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.error.ErrorDTO;
import java.util.ArrayList;

public class ErrorContentValidator extends BasePacketContentValidator<ErrorDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Fehlernachricht. ";

  public ErrorContentValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public ErrorDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var errorContent = super.castContent(ErrorDTO.class, inputContent);
    final var errorStrings = new ArrayList<String>();
    final var errorMessage = errorContent.getErrorMessage();
    final var checkString = checkString(errorMessage);

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return errorContent;
  }
}