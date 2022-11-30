/*
 *
 */
package de.vsy.shared_module.packet_management;

/**
 * Labels used to create consistently manageable PacketBuffers.
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
