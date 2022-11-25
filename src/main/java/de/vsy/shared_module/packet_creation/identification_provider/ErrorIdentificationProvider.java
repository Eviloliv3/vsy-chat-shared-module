package de.vsy.shared_module.packet_creation.identification_provider;

import static de.vsy.shared_transmission.packet.property.packet_type.ErrorType.SIMPLE_ERROR;

import de.vsy.shared_transmission.packet.content.error.ErrorDTO;
import de.vsy.shared_transmission.packet.property.packet_identifier.ErrorIdentifier;

public class ErrorIdentificationProvider extends AbstractIdentificationProvider {

  {
    identifiers.put(ErrorDTO.class, () -> new ErrorIdentifier(SIMPLE_ERROR));
  }
}
