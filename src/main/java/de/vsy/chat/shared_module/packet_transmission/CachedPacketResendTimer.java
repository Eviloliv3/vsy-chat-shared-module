package de.vsy.chat.shared_module.packet_transmission;

import de.vsy.chat.shared_module.packet_management.OutputBuffer;
import de.vsy.chat.shared_module.packet_transmission.cache.UnconfirmedPacketTransmissionCache;
import de.vsy.chat.shared_module.thread_manipulation.ThreadStatusManipulator;
import de.vsy.chat.shared_utility.logging.ThreadContextTimerTask;

/**
 * The Class CachePacketResendTimer.
 *
 * @author Frederic Heath
 */
public
class CachedPacketResendTimer extends ThreadContextTimerTask {

    private final UnconfirmedPacketTransmissionCache packetCache;
    private final ThreadStatusManipulator threadState;
    private final OutputBuffer resendBuffer;

    /**
     * Instantiates a new cache Packetresend timer.
     *
     * @param transmissionCache the transmission cache
     * @param threadManipulator the thread manipulator
     * @param outputBuffer the output buffer
     */
    public
    CachedPacketResendTimer (final UnconfirmedPacketTransmissionCache transmissionCache,
                             final ThreadStatusManipulator threadManipulator,
                             final OutputBuffer outputBuffer) {
        super();
        this.packetCache = transmissionCache;
        this.threadState = threadManipulator;
        this.resendBuffer = outputBuffer;
    }

    @Override
    public
    void runWithContext () {
        this.packetCache.addCycle();
        checkForDuePackets();
    }

    /** Check for due Packet. */
    private
    void checkForDuePackets () {

        do {
            var duePacket = this.packetCache.getNextDueTransmissionPacket(5);

            if (duePacket != null) {
                this.resendBuffer.appendPacket(duePacket);
            } else {
                break;
            }
        } while (this.threadState.areThreadsToTerminate());
    }
}
