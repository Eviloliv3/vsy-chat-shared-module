package de.vsy.chat.shared_module.thread_manipulation;

/** The Interface ProcessingInterruptionProvider. */
public
interface ProcessingInterruptProvider {

    /**
     * Interruption condition met.
     *
     * @return true, if successful
     */
    boolean conditionNotMet ();
}
