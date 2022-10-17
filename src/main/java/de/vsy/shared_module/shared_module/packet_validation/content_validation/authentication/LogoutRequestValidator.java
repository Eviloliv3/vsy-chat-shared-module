package de.vsy.shared_module.shared_module.packet_validation.content_validation.authentication;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.authentication.LogoutRequestDTO;
import java.util.ArrayList;

public final class LogoutRequestValidator extends BasePacketContentValidator<LogoutRequestDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Logout-Anfrage. ";

  public LogoutRequestValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public LogoutRequestDTO castAndValidateContent(PacketContent input)
      throws PacketValidationException {
    final var logoutContent = super.castContent(LogoutRequestDTO.class, input);
    final var errorStrings = new ArrayList<String>(1);
    var checkString = BeanChecker.checkBean(logoutContent.getClientData());

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }

    return logoutContent;
  }
}
