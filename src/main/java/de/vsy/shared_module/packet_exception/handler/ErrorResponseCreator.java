package de.vsy.shared_module.packet_exception.handler;

import de.vsy.shared_module.packet_exception.PacketHandlingException;
import de.vsy.shared_transmission.packet.Packet;

public interface ErrorResponseCreator {

    /**
     * Creates Error response Packet using the thrown PacketHandlingException and
     * the source Packet. If no response Packet can be created, at least a log
     * message should be created.
     *
     * @param phe       the throws PacketHandlingException
     * @param toProcess the Packet causing the PacketHandlingException
     * @return the Packet carrying the NotificationDTO
     */
    Packet createErrorResponsePacket(PacketHandlingException phe, Packet toProcess);
}
