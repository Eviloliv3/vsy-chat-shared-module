package de.vsy.chat.shared_module.packet_validation.content_validation.status;

import de.vsy.chat.shared_module.data_element_validation.BeanChecker;
import de.vsy.chat.shared_module.packet_exception.PacketValidationException;
import de.vsy.chat.shared_module.packet_validation.content_validation.BasePacketContentValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.status.ClientStatusChangeDTO;

import java.util.ArrayList;

public
class ClientStatusValidator
        extends BasePacketContentValidator<ClientStatusChangeDTO> {

    private static final String STANDARD_VALIDATION_MESSAGE = "Ungültige Klientenstatusmitteilung. ";

    public
    ClientStatusValidator () {
        super(STANDARD_VALIDATION_MESSAGE);
    }

    @Override
    public
    ClientStatusChangeDTO castAndValidateContent (PacketContent inputContent)
    throws PacketValidationException {
        String checkString;
        final var errorStrings = new ArrayList<String>();
        final var statusContent = super.castContent(ClientStatusChangeDTO.class,
                                                    inputContent);
        final var serviceType = statusContent.getServiceType();
        final var contactData = statusContent.getContactData();

        if (serviceType == null) {
            errorStrings.add("Kein Servicetyp angegeben. ");
        }
        checkString = BeanChecker.checkBean(contactData);

        if (checkString != null) {
            errorStrings.add(checkString);
        }

        if (!errorStrings.isEmpty()) {
            throw new PacketValidationException(
                    super.createErrorMessage(errorStrings));
        }
        return statusContent;
    }
}
