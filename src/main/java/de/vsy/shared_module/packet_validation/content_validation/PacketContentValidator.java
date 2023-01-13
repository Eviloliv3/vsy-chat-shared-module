package de.vsy.shared_module.packet_validation.content_validation;

import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_transmission.packet.content.PacketContent;

public interface PacketContentValidator<T extends PacketContent> {

    /**
     * Returns a cast version of the specified PacketContent, if the specified
     * PacketContent is of a specific subtype of
     * PacketContent and if the contained values are valid.
     *
     * @param input the PacketContent
     * @return the PacketContent cast to subtype
     * @throws PacketValidationException if PacketContent is not of the specific
     *                                   subtype or contains invalid values.
     */
    T castAndValidateContent(PacketContent input) throws PacketValidationException;
}
