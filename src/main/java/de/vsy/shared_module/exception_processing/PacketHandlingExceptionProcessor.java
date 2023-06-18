package de.vsy.shared_module.exception_processing;

import de.vsy.shared_module.packet_exception.PacketHandlingException;
import de.vsy.shared_module.packet_exception.handler.ErrorResponseCreator;
import de.vsy.shared_module.packet_exception.handler.PacketAnswerabilityCheck;
import de.vsy.shared_transmission.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Processes PacketHandlingExceptions.
 */
public class PacketHandlingExceptionProcessor {

  private static final Logger LOGGER = LogManager.getLogger();
  private final PacketAnswerabilityCheck answerableCheck;
  private final ErrorResponseCreator responseCreator;

  public PacketHandlingExceptionProcessor(final PacketAnswerabilityCheck answerableCheck,
      ErrorResponseCreator responseCreator) {
    this.answerableCheck = answerableCheck;
    this.responseCreator = responseCreator;
  }

  /**
   * Creates an notification Packet or log entry from the specified data.
   *
   * @param phe            PacketProcessingException
   * @param affectedPacket Packet
   * @return Packet if ErrorDTO could be created; null otherwise
   */
  public Packet processException(final PacketHandlingException phe, final Packet affectedPacket) {
    Packet errorResponse = null;

    if (answerableCheck.checkPacketAnswerable(affectedPacket)) {
      errorResponse = responseCreator.createErrorResponsePacket(phe, affectedPacket);
    } else {
      final var errorLogEntry = createErrorLog(phe, affectedPacket);
      LOGGER.error(errorLogEntry);
    }
    return errorResponse;
  }

  /**
   * Creates uniform log entries for exceptions, that cannot or should not be reported to clients.
   *
   * @param phe            PacketHandlingException
   * @param affectedPacket Packet
   * @return Loggable String message
   */
  private String createErrorLog(final PacketHandlingException phe, final Packet affectedPacket) {
    return "Exception: " + phe.getMessage() + "\n-> Packet will be discarded:\n" + affectedPacket;
  }
}
