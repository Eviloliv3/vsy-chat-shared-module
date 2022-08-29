/*
 *
 */
package de.vsy.chat.shared_module.packet_management;

import de.vsy.chat.shared_transmission.packet.Packet;

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
