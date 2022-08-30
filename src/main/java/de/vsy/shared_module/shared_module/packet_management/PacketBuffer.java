/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * A PacketBuffer acts as a Cache for processed Packet that need to be passed towards
 * another thread.
 */
public
class PacketBuffer implements InputBuffer, OutputBuffer {

    private final BlockingDeque<Packet> buffer;

    /** Instantiates a new PacketBuffer. */
    public
    PacketBuffer () {
        this.buffer = new LinkedBlockingDeque<>();
    }

    @Override
    public
    boolean appendPacket (final Packet input) {
        final var packetAdded = true;

        if (input != null && !this.buffer.contains(input)) {
            this.buffer.addLast(input);
            return packetAdded;
        }
        return false;
    }

    @Override
    public
    boolean prependPacket (final Packet input) {
        final var packetAdded = true;

        if (input != null && !this.buffer.contains(input)) {
            this.buffer.addFirst(input);
            return packetAdded;
        }
        return false;
    }

    @Override
    public
    Packet getPacket ()
    throws InterruptedException {
        return this.buffer.poll(500, TimeUnit.MILLISECONDS);
    }

    /**
     * Gibt an, ob die Queue leer ist.
     *
     * @return true, if is empty
     */
    public
    boolean containsPackets () {
        return !this.buffer.isEmpty();
    }
}
