package de.vsy.shared_module.shared_module.packet_validation.content_validation;

import de.vsy.shared_module.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;

public interface PacketContentValidator<T extends PacketContent> {

  T castAndValidateContent(PacketContent input) throws PacketValidationException;
}
