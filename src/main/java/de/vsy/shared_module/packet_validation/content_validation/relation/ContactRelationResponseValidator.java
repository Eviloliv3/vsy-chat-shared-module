package de.vsy.shared_module.packet_validation.content_validation.relation;

import static de.vsy.shared_module.data_element_validation.IdCheck.checkData;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationResponseDTO;
import java.util.ArrayList;

public class ContactRelationResponseValidator extends
    BasePacketContentValidator<ContactRelationResponseDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Invalid friendship response. ";

  public ContactRelationResponseValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public ContactRelationResponseDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var errorStrings = new ArrayList<String>();
    final var relationResponseContent = super.castContent(ContactRelationResponseDTO.class,
        inputContent);
    final var contactData = relationResponseContent.getRespondingClient();
    final var requestData = relationResponseContent.getRequestData();
    final var contactType = requestData.getContactType();
    final var recipientId = requestData.getRecipientId();
    final var originatorId = requestData.getOriginatorId();
    var checkString = BeanChecker.checkBean(contactData);

    checkString.ifPresent(errorStrings::add);

    if (contactType == null) {
      errorStrings.add("No contact type specified. ");
    }
    checkString = checkData(recipientId);

    checkString.ifPresent(errorStrings::add);

    checkString = checkData(originatorId);

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return relationResponseContent;
  }
}
