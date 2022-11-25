package de.vsy.shared_module.exception_processing;

import de.vsy.shared_module.packet_exception.handler.BasicAnswerabilityCheck;
import de.vsy.shared_module.packet_exception.handler.BasicErrorResponseCreator;

public class PacketHandlingExceptionCreator {

  protected PacketHandlingExceptionCreator() {
  }

  public static PacketHandlingExceptionProcessor getHandlerExceptionProcessor() {
    return new PacketHandlingExceptionProcessor(BasicAnswerabilityCheck::checkPacketAnswerable,
        new BasicErrorResponseCreator());
  }
}
