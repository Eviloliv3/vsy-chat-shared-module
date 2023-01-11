/*
 *
 */
package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * A PacketBuffer acts as a Cache for processed Packet that need to be passed towards another
 * thread.
 */
public class PacketBuffer implements InputBuffer, OutputBuffer {

    protected static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger();
    }

    private BlockingDeque<Packet> buffer;

    /**
     * Instantiates a new PacketBuffer.
     */
    public PacketBuffer() {
        this.buffer = new LinkedBlockingDeque<>();
    }

    @Override
    public void appendPacket(final Packet input) {
        this.buffer.addLast(input);
    }

    @Override
    public void prependPacket(final Packet input) {
        this.buffer.addFirst(input);
    }

    @Override
    public Packet getPacket() throws InterruptedException {
        return this.buffer.poll(250, TimeUnit.MILLISECONDS);
    }

    /**
     * Disclaimer: should not be used if multiple threads read from this buffer, because Packets may
     * be read multiple times. Replaces current BlockingDeque with new one and returns remaining
     * Packets from old queue in List.
     *
     * @return List containing remaining Packets
     */
    public List<Packet> freezeBuffer() {
        BlockingDeque<Packet> currentBuffer = this.buffer;
        this.buffer = new LinkedBlockingDeque<>();
        List<Packet> currentBufferContent = new ArrayList<>(currentBuffer.size());
        currentBufferContent.addAll(currentBuffer);
        return currentBufferContent;
    }

    /**
     * Checks whether the PacketBuffer is empty.
     *
     * @return true, if empty; false otherwise
     */
    public boolean containsPackets() {
        return !this.buffer.isEmpty();
    }
}
