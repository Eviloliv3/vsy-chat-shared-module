package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.dto.CommunicatorDTO;

public interface ClientDataProvider {

    /**
     * Returns the client's id.
     *
     * @return the client's id as int.
     */
    int getClientId();

    /**
     * Returns  the client's full CommunicatorDTO.
     *
     * @return the client's CommunicatorDTO
     */
    CommunicatorDTO getCommunicatorData();
}
