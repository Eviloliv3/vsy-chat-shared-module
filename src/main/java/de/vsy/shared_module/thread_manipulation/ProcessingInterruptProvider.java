package de.vsy.shared_module.thread_manipulation;

/**
 * The Interface ProcessingInterruptionProvider.
 */
public interface ProcessingInterruptProvider {

  /**
   * Checks whether a condition is not met.
   *
   * @return true, if condition is not met, false otherwise
   */
  boolean conditionNotMet();
}
