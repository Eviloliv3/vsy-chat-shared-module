package de.vsy.shared_module.packet_processing;

import de.vsy.shared_module.packet_exception.PacketProcessingException;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_module.packet_validation.PacketCheck;
import de.vsy.shared_transmission.packet.Packet;

public class PacketSyntaxCheckLink extends AbstractPacketProcessorLink {

    private static final String SYNTAX_ERROR_STRING;

    static {
        SYNTAX_ERROR_STRING = "Packet was not processed, because of a syntax error.";
    }

    private final PacketCheck packetValidator;

    public PacketSyntaxCheckLink(PacketProcessor nextStep, PacketCheck packetValidator) {
        super(nextStep);
        this.packetValidator = packetValidator;
    }

    @Override
    public void processPacket(Packet input)
            throws PacketValidationException, PacketProcessingException {
        var syntaxCheckString = this.packetValidator.checkPacket(input);

        if (syntaxCheckString.isPresent()) {
            throw new PacketValidationException(SYNTAX_ERROR_STRING + syntaxCheckString.get());
        } else {
            super.nextLink.processPacket(input);
        }
    }
}
