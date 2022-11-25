package de.vsy.shared_module.packet_exception;

import java.io.Serial;

public class PacketTransmissionException extends PacketProcessingException {

  @Serial
  private static final long serialVersionUID = -4799519381754784156L;

  public PacketTransmissionException(String errorMessage) {
    super(errorMessage);
  }
}
