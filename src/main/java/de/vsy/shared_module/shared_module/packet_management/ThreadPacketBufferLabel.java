/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

/**
 * Labels used to create constistently manageable PacketBuffers.
 */
public enum ThreadPacketBufferLabel {
  /**
   * The input.
   */
  HANDLER_BOUND,
  /**
   * The output.
   */
  OUTSIDE_BOUND,
  /**
   * The assignment.
   */
  SERVER_BOUND
}
