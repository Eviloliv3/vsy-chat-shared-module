/*
 *
 */
package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;

import static de.vsy.shared_transmission.packet.property.communicator.EligibleCommunicationEntity.CLIENT;


public abstract class BasicClientPacketDispatcher implements MultiplePacketDispatcher {

    private static final Logger LOGGER = LogManager.getLogger();
    private final PacketBuffer serverBoundBuffer;
    private final PacketBuffer clientBoundBuffer;

    /**
     * Instantiates a new ClientPacketDispatcher.
     *
     * @param clientBoundBuffer the client bound buffer
     * @param serverBoundBuffer the server bound buffer
     */
    public BasicClientPacketDispatcher(final PacketBuffer clientBoundBuffer,
                                       final PacketBuffer serverBoundBuffer) {
        this.clientBoundBuffer = clientBoundBuffer;
        this.serverBoundBuffer = serverBoundBuffer;
    }

    @Override
    public void dispatchPacket(final Deque<Packet> output) {

        while (!output.isEmpty()) {
            final Packet toDispatch = output.pop();
            dispatchPacket(toDispatch);
        }
    }

    @Override
    public void dispatchPacket(final Packet toAppend) {
        if (toAppend == null) {
            throw new IllegalArgumentException("Empty Packets cannot be buffered.");
        }
        final var properties = toAppend.getPacketProperties();
        final var recipient = properties.getRecipient();
        final var sender = properties.getSender();

        if (clientIsRecipient(sender, recipient)) {
            this.clientBoundBuffer.appendPacket(toAppend);
        } else {
            this.serverBoundBuffer.appendPacket(toAppend);
        }
    }

    /**
     * Packet is client bound if: local client not authenticated; recipient is STANDARD_CLIENT_ID
     * recipientId equals clientId
     *
     * @param sender the sender entity
     * @param recipient the recipient entity
     * @return true if client is recipient, else false
     */
    protected abstract boolean clientIsRecipient(final CommunicationEndpoint sender, final CommunicationEndpoint recipient);
}
