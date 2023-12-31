package de.vsy.shared_module.packet_validation.content_validation.status;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.status.ClientStatusChangeDTO;
import java.util.ArrayList;

public class ClientStatusValidator extends BasePacketContentValidator<ClientStatusChangeDTO> {

  private static final String STANDARD_VALIDATION_MESSAGE = "Invalid client status notification. ";

  public ClientStatusValidator() {
    super(STANDARD_VALIDATION_MESSAGE);
  }

  @Override
  public ClientStatusChangeDTO castAndValidateContent(PacketContent inputContent)
      throws PacketValidationException {
    final var errorStrings = new ArrayList<String>();
    final var statusContent = super.castContent(ClientStatusChangeDTO.class, inputContent);
    final var serviceType = statusContent.getServiceType();
    final var contactData = statusContent.getContactData();
    final var checkString = BeanChecker.checkBean(contactData);

    if (serviceType == null) {
      errorStrings.add("No service type specified. ");
    }

    checkString.ifPresent(errorStrings::add);

    if (!errorStrings.isEmpty()) {
      throw new PacketValidationException(super.createErrorMessage(errorStrings));
    }
    return statusContent;
  }
}
