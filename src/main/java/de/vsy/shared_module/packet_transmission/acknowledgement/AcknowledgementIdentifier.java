package de.vsy.shared_module.packet_transmission.acknowledgement;

import de.vsy.shared_transmission.packet.property.packet_category.PacketCategory;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifierImpl;
import de.vsy.shared_transmission.packet.property.packet_type.PacketType;

import java.io.Serial;

import static de.vsy.shared_transmission.packet.property.packet_category.PacketCategory.STATUS;

public class AcknowledgementIdentifier extends ContentIdentifierImpl {

    /**
     * Instantiates a new PacketIdentifier.
     */
    private static final PacketCategory CATEGORY = STATUS;
    @Serial
    private static final long serialVersionUID = -2324017215087427473L;

    public AcknowledgementIdentifier(PacketType packetType) {
        super(CATEGORY, packetType);
    }
}
