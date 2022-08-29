package de.vsy.chat.shared_module.packet_validation;

import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

/** The Interface SemanticPacketValidationProvider. */
public
interface SemanticPacketValidationProvider {

    /**
     * Content matches identifier.
     *
     * @param identifier the identifier
     * @param content the content
     *
     * @return true, if successful
     */
    boolean contentMatchesIdentifier (ContentIdentifier identifier,
                                      PacketContent content);

    /**
     * Type matches category.
     *
     * @param identifier the identifier
     *
     * @return true, if successful
     */
    boolean typeMatchesCategory (ContentIdentifier identifier);
}
