package de.vsy.chat.shared_module.packet_creation.identification_provider;

import de.vsy.chat.shared_transmission.packet.content.error.ErrorDTO;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.ErrorIdentifier;

import static de.vsy.chat.shared_transmission.packet.property.packet_type.ErrorType.SIMPLE_ERROR;

public
class ErrorIdentificationProvider extends AbstractIdentificationProvider {

    {
        identifiers.put(ErrorDTO.class, () -> new ErrorIdentifier(SIMPLE_ERROR));
    }
}
