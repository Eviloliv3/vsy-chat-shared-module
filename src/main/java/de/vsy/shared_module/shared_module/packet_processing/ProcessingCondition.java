package de.vsy.shared_module.shared_module.packet_processing;

public
interface ProcessingCondition {

    boolean checkCondition ();

    String getErrorMessage ();
}
