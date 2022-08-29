package de.vsy.chat.shared_module.packet_exception.handler;

import de.vsy.chat.shared_module.packet_creation.PacketCompiler;
import de.vsy.chat.shared_module.packet_exception.PacketHandlingException;
import de.vsy.chat.shared_transmission.packet.Packet;
import de.vsy.chat.shared_transmission.packet.content.error.ErrorDTO;

import static de.vsy.chat.shared_module.packet_creation.PacketCompiler.createResponse;

public
class BasicErrorResponseCreator implements ErrorResponseCreator {

    @Override
    public
    Packet createErrorResponsePacket (PacketHandlingException phe,
                                      Packet toProcess) {
        final var errorData = createSimpleErrorData(phe, toProcess);
        return PacketCompiler.createResponse(errorData, toProcess);
    }

    protected
    ErrorDTO createSimpleErrorData (PacketHandlingException phe, Packet toProcess) {
        return new ErrorDTO(phe.getMessage(), toProcess);
    }
}
