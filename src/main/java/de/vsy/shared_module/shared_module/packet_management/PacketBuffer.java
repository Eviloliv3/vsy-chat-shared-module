/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * A PacketBuffer acts as a Cache for processed Packet that need to be passed towards
 * another thread.
 */
public
class PacketBuffer implements InputBuffer, OutputBuffer {

    protected static final Logger LOGGER;
    private final BlockingDeque<Packet> buffer;

    static {
        LOGGER = LogManager.getLogger();
    }

    /** Instantiates a new PacketBuffer. */
    public
    PacketBuffer () {
        this.buffer = new LinkedBlockingDeque<>();
    }

    @Override
    public
    void appendPacket (final Packet input) {
        if (!this.buffer.contains(input)) {
            this.buffer.addLast(input);
        }else{
            LOGGER.error("Paket leer oder bereits im Puffer: {}", input);
        }
    }

    @Override
    public
    void prependPacket (final Packet input) {
        if (!this.buffer.contains(input)) {
            this.buffer.addFirst(input);
        }else {
            LOGGER.error("Paket leer oder bereits im Puffer: {}", input);
        }
    }

    @Override
    public
    Packet getPacket ()
    throws InterruptedException {
        return this.buffer.poll(250, TimeUnit.MILLISECONDS);
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
