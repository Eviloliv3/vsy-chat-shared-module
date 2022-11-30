package de.vsy.shared_module.packet_validation.content_validation.status;

import de.vsy.shared_module.collection_content_validation.ListCheck;
import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.status.ContactMessengerStatusDTO;
import java.util.ArrayList;

public class ContactStatusValidator extends BasePacketContentValidator<ContactMessengerStatusDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Invalid contact status notification. ";

  public ContactStatusValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public ContactMessengerStatusDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var errorStrings = new ArrayList<String>();
    final var contactStatusContent = super.castContent(ContactMessengerStatusDTO.class,
        inputContent);
    final var serviceType = contactStatusContent.getServiceType();
    final var contactType = contactStatusContent.getContactType();
    final var contactData = contactStatusContent.getContactData();
    final var messageHistory = contactStatusContent.getMessages();
    var checkString = BeanChecker.checkBean(contactData);

    if (serviceType == null) {
      errorStrings.add("No service type specified. ");
    }

    if (contactType == null) {
      errorStrings.add("No contact type specified. ");
    }

    checkString.ifPresent(errorStrings::add);

    checkString = ListCheck.checkMessageDataList(messageHistory);

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return contactStatusContent;
  }
}
