package de.vsy.chat.shared_module.exception_processing;

import de.vsy.chat.shared_module.packet_exception.handler.BasicAnswerabilityCheck;
import de.vsy.chat.shared_module.packet_exception.handler.BasicErrorResponseCreator;

public
class PacketHandlingExceptionCreator {

    protected
    PacketHandlingExceptionCreator () {}

    public static
    PacketHandlingExceptionProcessor getHandlerExceptionProcessor () {
        return new PacketHandlingExceptionProcessor(
                BasicAnswerabilityCheck::checkPacketAnswerable,
                new BasicErrorResponseCreator());
    }
}
