package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

public interface ContentIdentificationProvider {

    /**
     * Creates a valid ContentIdentifier for the specified PacketContent's class.
     *
     * @param data the PacketContent to be identified
     * @return the ContentIdentifier or null, if no Identifier was found for the
     * specified PacketContent's class.
     */
    ContentIdentifier getContentIdentifier(PacketContent data);
}
