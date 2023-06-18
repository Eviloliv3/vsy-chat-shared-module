package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.Translatable;

public class ClientRelatedTranslatable<T extends PacketContent> implements Translatable {

  private final CommunicatorDTO clientData;
  private final T contentToTranslate;

  public ClientRelatedTranslatable(final CommunicatorDTO clientData, final T contentToTranslate) {
    this.clientData = clientData;
    this.contentToTranslate = contentToTranslate;
  }

  public CommunicatorDTO getClientData() {
    return this.clientData;
  }

  public T getContent() {
    return this.contentToTranslate;
  }
}
