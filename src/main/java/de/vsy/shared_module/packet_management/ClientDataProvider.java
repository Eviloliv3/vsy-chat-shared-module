package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.dto.CommunicatorDTO;

public interface ClientDataProvider {

  int getClientId();

  CommunicatorDTO getCommunicatorData();
}
