package de.vsy.chat.shared_module.exception_processing;

import de.vsy.chat.shared_module.packet_exception.PacketHandlingException;
import de.vsy.chat.shared_module.packet_exception.handler.ErrorResponseCreator;
import de.vsy.chat.shared_module.packet_exception.handler.PacketAnswerabilityCheck;
import de.vsy.chat.shared_transmission.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Uebernimmt die Verarbeitung von PacketProcessingExceptions. */
public
class PacketHandlingExceptionProcessor {

    /** The LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();
    private final PacketAnswerabilityCheck answerableCheck;
    private final ErrorResponseCreator responseCreator;

    /** Instantiates a new PacketHandling exception handler. */
    public
    PacketHandlingExceptionProcessor (final PacketAnswerabilityCheck answerableCheck,
                                      ErrorResponseCreator responseCreator) {
        this.answerableCheck = answerableCheck;
        this.responseCreator = responseCreator;
    }

    /**
     * Erstellt ein Fehlerpaket oder einen Log-Eintrag aus den angegebenen
     * Parametern.
     *
     * @param phe PacketProcessingException, die Fehlermeldung und -ursache
     *         enthaelt
     * @param affectedPacket Paket, bei dessen Verarbeitung der Fehler
     *         auftrat
     *
     * @return Ein Fehler-Paket vom Typ Packet wenn das Paket erstellt werden konnte;
     *         sonst null
     */
    public
    Packet processException (final PacketHandlingException phe,
                             final Packet affectedPacket) {
        Packet errorResponse = null;

        if (answerableCheck.checkPacketAnswerable(affectedPacket)) {
            errorResponse = responseCreator.createErrorResponsePacket(phe,
                                                                      affectedPacket);
        } else {
            final var errorLogEntry = createErrorLog(phe, affectedPacket);
            LOGGER.error(errorLogEntry);
        }
        return errorResponse;
    }

    /**
     * Erstellt einen einheitlichen Log-Eintrag für Fehler, die nicht an einen Client
     * gemeldet werden können.
     *
     * @param phe Die Exception, die während der ursprünglichen
     *         Paketverarbeitung geworfen wurde.
     * @param affectedPacket the affected packet
     *
     * @return Log-Eintrag zu der übergebenen Exception.
     */
    private
    String createErrorLog (final PacketHandlingException phe,
                           final Packet affectedPacket) {
        return "\nMeldung: " + phe.getMessage() + "\n-> Paket\n" + affectedPacket +
               "\n wird verworfen.";
    }
}
