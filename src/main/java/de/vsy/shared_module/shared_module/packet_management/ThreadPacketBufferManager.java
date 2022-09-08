/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import java.util.EnumMap;
import java.util.Map;

/**
 * PacketBuffer Manager using ThreadPacketBufferLabel as Key. /** Verwaltet bis zu
 * drei threadeigene PacketBuffer.
 */
public
class ThreadPacketBufferManager {

    /** The buffer map. */
    private final Map<ThreadPacketBufferLabel, PacketBuffer> bufferMap;

    /** Instantiates a new thread PacketBuffer manager. */
    public
    ThreadPacketBufferManager () {
        this.bufferMap = new EnumMap<>(ThreadPacketBufferLabel.class);
    }

    /**
     * Entfernt den, unter dem Label hinterlegten, PacketBuffer.
     *
     * @param bufferLabel the buffer label
     *
     * @return the PacketBuffer
     */
    public
    PacketBuffer deregisterPackerBuffer (final ThreadPacketBufferLabel bufferLabel) {
        return this.bufferMap.remove(bufferLabel);
    }

    /**
     * Gets the PacketBuffer.
     *
     * @param bufferLabel the buffer label
     *
     * @return the PacketBuffer
     */
    public
    PacketBuffer getPacketBuffer (final ThreadPacketBufferLabel bufferLabel) {
        return this.bufferMap.get(bufferLabel);
    }

    /**
     * Erstellt einen neuen PacketBuffer und fügt diesen der, nicht leeren, Map
     * hinzu; sofern kein PacketBuffer hinterlegt ist.
     *
     * @param bufferLabel the buffer label
     */
    public
    void registerPackerBuffer (final ThreadPacketBufferLabel bufferLabel) {
        this.bufferMap.putIfAbsent(bufferLabel, new PacketBuffer());
    }

    /**
     * Ersetzt einen bestehenden PacketBuffer durch den übergebenen neuen.
     *
     * @param bufferLabel the buffer label
     * @param buffer the buffer
     *
     * @return the old PacketBuffer
     */
    public
    PacketBuffer setPacketBuffer (final ThreadPacketBufferLabel bufferLabel,
                                  final PacketBuffer buffer) {
        return this.bufferMap.put(bufferLabel, buffer);
    }

    @Override
    public
    String toString () {
        final var sb = new StringBuilder();

        for (final var bufferEntrySet : this.bufferMap.entrySet()) {
            sb.append("------Thread-Buffer-Manager-----")
              .append("\nLabel: ")
              .append(bufferEntrySet.getKey())
              .append("\nBuffer: ")
              .append(bufferEntrySet.getValue())
              .append("\n********************************");
        }
        return sb.toString();
    }
}
