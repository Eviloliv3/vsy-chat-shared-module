package de.vsy.shared_module.packet_processing;

import java.util.function.Predicate;

public class HandlerProcessingCondition<T> implements ProcessingCondition {

  private final Predicate<T> conditionCheck;
  private final T condition;
  private final String errorMessage;

  public HandlerProcessingCondition(final Predicate<T> conditionCheck, final T condition,
      final String errorMessage) {
    this.conditionCheck = conditionCheck;
    this.condition = condition;
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean checkCondition() {
    return conditionCheck.test(condition);
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }
}
