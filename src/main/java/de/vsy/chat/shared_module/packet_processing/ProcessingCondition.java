package de.vsy.chat.shared_module.packet_processing;

public
interface ProcessingCondition {

    boolean checkCondition ();

    String getErrorMessage ();
}
