package de.vsy.shared_module.packet_transmission;

public interface ConnectionThreadSynchronizer {

  /**
   * Returns boolean indicating the Socket connection's healthiness.
   *
   * @return true if connection is live; false otherwise
   */
  boolean connectionIsLive();
}
