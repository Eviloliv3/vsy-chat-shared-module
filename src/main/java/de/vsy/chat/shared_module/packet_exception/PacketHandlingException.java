package de.vsy.chat.shared_module.packet_exception;

import java.io.Serial;

/** Exception thrown on PacketHandling errors. */
public
class PacketHandlingException extends Exception {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = -2546836659353793441L;

    /**
     * Instantiates a new PacketHandling exception.
     *
     * @param errorMessage the error message
     */
    public
    PacketHandlingException (final String errorMessage) {
        super(errorMessage);
    }
}
