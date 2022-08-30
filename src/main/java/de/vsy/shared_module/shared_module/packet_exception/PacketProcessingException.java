package de.vsy.shared_module.shared_module.packet_exception;

import java.io.Serial;

public
class PacketProcessingException extends PacketHandlingException {

    @Serial
    private static final long serialVersionUID = -1383173866000208232L;

    /**
     * Instantiates a new PacketHandling exception.
     *
     * @param errorMessage the error message
     */
    public
    PacketProcessingException (String errorMessage) {
        super(errorMessage);
    }
}
