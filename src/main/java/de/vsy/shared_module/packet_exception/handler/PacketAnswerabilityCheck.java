package de.vsy.shared_module.packet_exception.handler;

import de.vsy.shared_transmission.packet.Packet;

public interface PacketAnswerabilityCheck {

    /**
     * Checks whether the specified Packet is answerable.
     *
     * @param toCheck Packet
     * @return True, if answerable; false otherwise.
     */
    boolean checkPacketAnswerable(final Packet toCheck);
}
