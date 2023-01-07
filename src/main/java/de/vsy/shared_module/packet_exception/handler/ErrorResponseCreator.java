package de.vsy.shared_module.packet_exception.handler;

import de.vsy.shared_module.packet_exception.PacketHandlingException;
import de.vsy.shared_transmission.packet.Packet;

public interface ErrorResponseCreator {

    Packet createErrorResponsePacket(PacketHandlingException phe, Packet toProcess);
}
