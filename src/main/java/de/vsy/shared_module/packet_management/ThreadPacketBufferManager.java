/*
 *
 */
package de.vsy.shared_module.packet_management;

import java.util.EnumMap;
import java.util.Map;

/**
 * Manages PacketBuffers using  ThreadPacketBufferLabel as Key.
 */
public class ThreadPacketBufferManager {

  private final Map<ThreadPacketBufferLabel, PacketBuffer> bufferMap;

  public ThreadPacketBufferManager() {
    this.bufferMap = new EnumMap<>(ThreadPacketBufferLabel.class);
  }

  /**
   * Removes and returns the PacketBuffer identified by the specified bufferLabel.
   *
   * @param bufferLabel ThreadPacketBufferLabel
   * @return PacketBuffer
   */
  public PacketBuffer deregisterPacketBuffer(final ThreadPacketBufferLabel bufferLabel) {
    return this.bufferMap.remove(bufferLabel);
  }

  /**
   * Returns the PacketBuffer.
   *
   * @param bufferLabel ThreadPacketBufferLabel
   * @return PacketBuffer
   */
  public PacketBuffer getPacketBuffer(final ThreadPacketBufferLabel bufferLabel) {
    return this.bufferMap.get(bufferLabel);
  }

  /**
   * Registers a new PacketBuffer for the specified bufferLabel, if no PacketBuffer exists.
   *
   * @param bufferLabel ThreadPacketBufferLabel
   */
  public void registerPacketBuffer(final ThreadPacketBufferLabel bufferLabel) {
    this.bufferMap.putIfAbsent(bufferLabel, new PacketBuffer());
  }

  /**
   * Sets the PacketBuffer for the specified bufferLabel.
   *
   * @param bufferLabel ThreadPacketBufferLabel
   * @param buffer      PacketBuffer
   * @return PacketBuffer if old PacketBuffer existed; null otherwise
   */
  public PacketBuffer setPacketBuffer(final ThreadPacketBufferLabel bufferLabel,
      final PacketBuffer buffer) {
    return this.bufferMap.put(bufferLabel, buffer);
  }

  @Override
  public String toString() {
    final var sb = new StringBuilder();

    for (final var bufferEntrySet : this.bufferMap.entrySet()) {
      sb.append("------Thread-Buffer-Manager-----").append("\nLabel: ")
          .append(bufferEntrySet.getKey())
          .append("\nBuffer: ").append(bufferEntrySet.getValue())
          .append("\n********************************");
    }
    return sb.toString();
  }
}
