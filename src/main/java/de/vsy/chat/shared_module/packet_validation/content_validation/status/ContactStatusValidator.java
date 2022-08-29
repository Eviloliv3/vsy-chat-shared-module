package de.vsy.chat.shared_module.packet_validation.content_validation.status;

import de.vsy.chat.shared_module.collection_content_validation.ListCheck;
import de.vsy.chat.shared_module.data_element_validation.BeanChecker;
import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.status.ContactMessengerStatusDTO;

import java.util.ArrayList;

public
class ContactStatusValidator
        extends BasePacketContentValidator<ContactMessengerStatusDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Kontaktstatusmitteilung. ";

    public
    ContactStatusValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ContactMessengerStatusDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        String checkString;
        final var errorStrings = new ArrayList<String>();
        final var contactStatusContent = super.castContent(
                ContactMessengerStatusDTO.class, inputContent);
        final var serviceType = contactStatusContent.getServiceType();
        final var contactType = contactStatusContent.getContactType();
        final var contactData = contactStatusContent.getContactData();
        final var messageHistory = contactStatusContent.getMessages();

        if (serviceType == null) {
            errorStrings.add("Kein Servicetyp angegeben. ");
        }

        if (contactType == null) {
            errorStrings.add("Kein Kontakttyp angegeben. ");
        }
        checkString = BeanChecker.checkBean(contactData);

        if (checkString != null) {
            errorStrings.add(checkString);
        }
        checkString = ListCheck.checkMessageDataList(messageHistory);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return contactStatusContent;
    }
}
