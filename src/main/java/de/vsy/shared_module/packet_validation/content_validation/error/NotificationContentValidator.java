package de.vsy.shared_module.packet_validation.content_validation.error;

import static de.vsy.shared_module.data_element_validation.StringCheck.checkString;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.notification.SimpleInformationDTO;
import java.util.ArrayList;

public class NotificationContentValidator extends BasePacketContentValidator<SimpleInformationDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Invalid notification content. ";

  public NotificationContentValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public SimpleInformationDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var notificationContent = super.castContent(SimpleInformationDTO.class, inputContent);
    final var errorStrings = new ArrayList<String>();
    final var errorMessage = notificationContent.getInformationString();
    final var checkString = checkString(errorMessage);

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return notificationContent;
  }
}
