package de.vsy.shared_module.shared_module.packet_exception;

import java.io.Serial;

public class PacketValidationException extends PacketHandlingException {

  @Serial
  private static final long serialVersionUID = 8138040012805035700L;

  /**
   * Instantiates a new PacketHandling exception.
   *
   * @param errorMessage the error message
   */
  public PacketValidationException(String errorMessage) {
    super(errorMessage);
  }
}
