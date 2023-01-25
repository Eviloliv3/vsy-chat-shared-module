package de.vsy.shared_module.exception_processing;

import de.vsy.shared_module.packet_exception.handler.BasicAnswerabilityCheck;
import de.vsy.shared_module.packet_exception.handler.BasicErrorResponseCreator;

public class PacketHandlingExceptionCreator {

  private static final PacketHandlingExceptionProcessor SINGLE_PROCESSOR;

  static {
    SINGLE_PROCESSOR = new PacketHandlingExceptionProcessor(
        BasicAnswerabilityCheck::checkPacketAnswerable,
        new BasicErrorResponseCreator());
  }

  protected PacketHandlingExceptionCreator() {
  }

  public static PacketHandlingExceptionProcessor getHandlerExceptionProcessor() {
    return SINGLE_PROCESSOR;
  }
}
