/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.vsy.chat.shared_module.packet_transmission.cache;

import de.vsy.chat.shared_transmission.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Cache works based on CachedPackets and is intended to store Packet while no
 * reception is sent by another thread/process or application. Frederic Heath
 */
public
class UnconfirmedPacketTransmissionCache {

    private static final int STANDARD_MIN_CYCLES = 5;
    private static final Logger LOGGER = LogManager.getLogger();
    private final ReadWriteLock accessLock;
    private final Queue<CachedPacket> cachedPackets;
    private final int cycleTimeMillis;

    /**
     * Instantiates a new Packettransmission cache.
     *
     * @param cycleTimeMillis the cycle time millis
     */
    public
    UnconfirmedPacketTransmissionCache (final int cycleTimeMillis) {
        this.accessLock = new ReentrantReadWriteLock();
        this.cachedPackets = new LinkedList<>();

        if (cycleTimeMillis > 0) {
            this.cycleTimeMillis = cycleTimeMillis;
        } else {
            throw new IllegalArgumentException(
                    "Ungültige Wartezeit für Zyklen (<=0)");
        }
    }

    /** Adds the cycle. */
    public
    void addCycle () {

        try {
            this.accessLock.writeLock().lock();

            if (!this.cachedPackets.isEmpty()) {

                for (final var cachedPacket : this.cachedPackets) {
                    cachedPacket.increasePassedCycles();
                }
            }
        } finally {
            this.accessLock.writeLock().unlock();
        }
    }

    /**
     * Gets the next due transmission Packet
     *
     * @param minCycles the min cycles
     *
     * @return the next due transmission packet
     */
    public
    Packet getNextDueTransmissionPacket (final int minCycles) {
        Packet toTransmit = null;
        int dueCycles;

        if (minCycles > 0) {
            dueCycles = minCycles;
        } else {
            dueCycles = STANDARD_MIN_CYCLES;
        }

        try {
            this.accessLock.writeLock().lock();

            if (!this.cachedPackets.isEmpty()) {
                final var nextDuePacket = this.cachedPackets.peek();

                if (nextDuePacket.getPassedCycles() >= dueCycles) {
                    this.cachedPackets.remove();
                    toTransmit = nextDuePacket.getCachedPacket();
                    appendPacket(toTransmit);
                    LOGGER.info("Wird erneut gesandt {}", toTransmit);
                }
            }
        } finally {
            this.accessLock.writeLock().unlock();
        }

        return toTransmit;
    }

    /**
     * Adds the Packet
     *
     * @param toCache the to cache
     */
    public
    void appendPacket (final Packet toCache) {

        if (toCache != null) {
            final var cachedPacket = new CachedPacket(toCache, this.cycleTimeMillis);

            try {
                this.accessLock.writeLock().lock();

                if (!this.cachedPackets.contains(cachedPacket)) {
                    this.cachedPackets.add(cachedPacket);
                }
            } finally {
                this.accessLock.writeLock().unlock();
            }
        }
    }

    /**
     * Removes the Packet
     *
     * @param packetHash the Packethash
     */
    public
    void removePacket (final String packetHash) {

        try {
            this.accessLock.writeLock().lock();

            this.cachedPackets.removeIf(cachedPacket -> Objects.equals(
                    cachedPacket.getCachedPacketHash(), packetHash));
        } finally {
            this.accessLock.writeLock().unlock();
        }
    }

    public
    Queue<Packet> removeRemainingPackets () {
        Queue<Packet> remainingPacketSet = new LinkedList<>();

        while (!this.cachedPackets.isEmpty()) {
            remainingPacketSet.add(cachedPackets.poll().getCachedPacket());
        }
        return remainingPacketSet;
    }
}
