/*
 *
 */
package de.vsy.shared_module.shared_module.packet_validation;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.PacketProperties;

/**
 * Erlaubt die Ueberpruefung von eines ganzen Pakets oder den einzelnen Eigenschaften
 * bzw. des Inhaltes.
 */
public
interface PacketCheck {

    /**
     * Check Packet
     *
     * @param toCheck the to check
     *
     * @return the string
     */
    String checkPacket (Packet toCheck);

    /**
     * Check PacketDataManagement.
     *
     * @param toCheck the to check
     *
     * @return the string
     */
    String checkPacketContent (PacketContent toCheck);

    /**
     * Check PacketProperties.
     *
     * @param toCheck the to check
     *
     * @return the string
     */
    String checkPacketProperties (PacketProperties toCheck);
}
