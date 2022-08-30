/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

/** Provides Packetdispatching interface. */
public
interface OutputBuffer {

    /**
     * Adds the Packet
     *
     * @param output the output
     *
     * @return true, if successful
     */
    boolean appendPacket (Packet output);

    /**
     * Prepend Packet
     *
     * @param output the output
     *
     * @return true, if successful
     */
    boolean prependPacket (Packet output);
}
