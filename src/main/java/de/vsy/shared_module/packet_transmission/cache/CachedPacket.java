package de.vsy.shared_module.packet_transmission.cache;

import de.vsy.shared_transmission.packet.Packet;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Wrapper for Packet, that provides a creation time, a cycle time and a cycle counter indicating
 * the time that has passed since the Wrapper creation. Frederic Heath
 */
public class CachedPacket {

  private final Packet cache;
  private final long cycleMillis;
  private final Instant cachingTime;
  private int cyclesPassed;

  /**
   * Instantiates a new cached Packet
   *
   * @param toCache            the to cache
   * @param cachingCycleMillis the caching cycle millis
   */
  public CachedPacket(final Packet toCache, final int cachingCycleMillis) {
    if (toCache == null || cachingCycleMillis <= 0) {
      var errorMessage = "";
      errorMessage +=
          "Ungültiger Parameter! Packet: " + toCache + "/Caching Cycle:" + cachingCycleMillis;
      throw new IllegalArgumentException(errorMessage);
    }
    this.cache = toCache;
    this.cycleMillis = cachingCycleMillis;
    this.cyclesPassed = 0;
    this.cachingTime = Instant.now();
  }

  /**
   * Gets the cached Packethash.
   *
   * @return the cached Packethash
   */
  public String getCachedPacketHash() {
    return this.cache.getPacketHash();
  }

  @Override
  public int hashCode() {
    return 53 * Objects.hashCode(this.cache);
  }

  @Override
  public boolean equals(final Object otherPacket) {
    if (this == otherPacket) {
      return true;
    }

    if (!(otherPacket instanceof CachedPacket that)) {
      return false;
    }

    return this.cache.equals(that.getCachedPacket());
  }

  /**
   * Gets the cached Packet
   *
   * @return the cached packet
   */
  public Packet getCachedPacket() {
    return this.cache;
  }

  /**
   * Gets the caching time millis.
   *
   * @return the caching time millis
   */
  public Instant getCachingTime() {
    return this.cachingTime;
  }

  /**
   * Gets the passed cycles.
   *
   * @return the passed cycles
   */
  public int getPassedCycles() {
    return this.cyclesPassed;
  }

  /**
   * Increase passed cycles.
   */
  public void increasePassedCycles() {
    final Instant currentTime = Instant.now();
    this.cyclesPassed = (int) (Duration.between(this.cachingTime, currentTime).toMillis()
        / this.cycleMillis);
  }
}
