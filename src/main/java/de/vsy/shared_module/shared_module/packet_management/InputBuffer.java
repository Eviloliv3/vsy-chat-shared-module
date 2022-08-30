/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

/** Provides Packetreading interface. */
public
interface InputBuffer {

    /**
     * Gets the Packet
     *
     * @return the packet
     */
    Packet getPacket ()
    throws InterruptedException;
}
