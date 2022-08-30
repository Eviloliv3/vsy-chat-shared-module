package de.vsy.shared_module.shared_module.packet_validation.content_validation.relation;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationResponseDTO;

import java.util.ArrayList;

import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;

public
class ContactRelationResponseValidator
        extends BasePacketContentValidator<ContactRelationResponseDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Kontaktantwort. ";

    public
    ContactRelationResponseValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ContactRelationResponseDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        final var errorStrings = new ArrayList<String>();
        final var relationResponseContent = super.castContent(
                ContactRelationResponseDTO.class, inputContent);
        final var contactData = relationResponseContent.getContactData();
        final var contactType = relationResponseContent.getContactType();
        final var recipientId = relationResponseContent.getRecipientId();
        final var originatorId = relationResponseContent.getOriginatorId();
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
        return relationResponseContent;
    }
}
