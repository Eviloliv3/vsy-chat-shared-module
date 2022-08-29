package de.vsy.chat.shared_module.packet_validation.content_validation.relation;

import de.vsy.chat.shared_module.data_element_validation.BeanChecker;
import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.relation.ContactRelationRequestDTO;

import java.util.ArrayList;

import static de.vsy.chat.shared_module.data_element_validation.IdCheck.checkData;

public
class ContactRelationRequestValidator
        extends BasePacketContentValidator<ContactRelationRequestDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Kontaktanfrage. ";

    public
    ContactRelationRequestValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ContactRelationRequestDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var errorStrings = new ArrayList<String>();
        final var relationRequestContent = super.castContent(
                ContactRelationRequestDTO.class, inputContent);
        final var contactData = relationRequestContent.getContactData();
        final var contactType = relationRequestContent.getContactType();
        final var recipientId = relationRequestContent.getRecipientId();
        final var originatorId = relationRequestContent.getOriginatorId();
        var checkString = BeanChecker.checkBean(contactData);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (contactType == null) {
            errorStrings.add("Kein Empfängertyp angegeben. ");
        }
        checkString = checkData(recipientId);

        if (checkString != null) {
            errorStrings.add(checkString);
        }
        checkString = checkData(originatorId);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return relationRequestContent;
    }
}
