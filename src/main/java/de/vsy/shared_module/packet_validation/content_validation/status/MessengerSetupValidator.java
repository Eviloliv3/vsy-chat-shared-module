package de.vsy.shared_module.packet_validation.content_validation.status;

import de.vsy.shared_module.collection_content_validation.MapCheck;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.status.MessengerSetupDTO;
import java.util.ArrayList;

public class MessengerSetupValidator extends BasePacketContentValidator<MessengerSetupDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Ungültiges Messenger-Einrichtungs-Anfrage. ";

  public MessengerSetupValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public MessengerSetupDTO castAndValidateContent(PacketContent input)
      throws PacketValidationException {
    final var errorStrings = new ArrayList<String>(1);
    final var setupContent = super.castContent(MessengerSetupDTO.class, input);
    final var messageHistory = setupContent.getOldMessages();
    final var contacts = setupContent.getActiveContacts();
    var checkString = MapCheck.checkMessageHistory(messageHistory);

    checkString.ifPresent(errorStrings::add);

    checkString = MapCheck.checkActiveContacts(contacts);

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return setupContent;
  }
}
