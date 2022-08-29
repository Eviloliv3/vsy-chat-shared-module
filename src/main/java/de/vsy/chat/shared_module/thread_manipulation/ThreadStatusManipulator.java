/*
 *
 */
package de.vsy.chat.shared_module.thread_manipulation;

import java.util.concurrent.atomic.AtomicBoolean;

/** Provides a single poison pill for a list of Threads. */
public
class ThreadStatusManipulator {

    private final AtomicBoolean threadAliveFlag;

    /** Instantiates a new service killer. */
    public
    ThreadStatusManipulator () {
        this.threadAliveFlag = new AtomicBoolean(true);
    }

    public
    void terminateThreads () {
        this.threadAliveFlag.set(false);
    }

    public
    boolean areThreadsToTerminate () {
        return this.threadAliveFlag.get();
    }
}
