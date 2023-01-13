package de.vsy.shared_module.packet_processing;

public interface ProcessingCondition {

    /**
     * Returns whether a preset condition is fulfilled.
     *
     * @return true, if condition fulfilled, false otherwise.
     */
    boolean checkCondition();

    /**
     * Provides information on which condition was not fulfilled and why the preset
     * condition might not have been fulfilled.
     *
     * @return String of information concerning a condition check failure.
     */
    String getErrorMessage();
}
