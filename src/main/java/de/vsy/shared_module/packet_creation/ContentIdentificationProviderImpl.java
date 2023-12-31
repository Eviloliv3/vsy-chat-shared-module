package de.vsy.shared_module.packet_creation;

import de.vsy.shared_module.packet_creation.identification_provider.AuthenticationIdentificationProvider;
import de.vsy.shared_module.packet_creation.identification_provider.ChatIdentificationProvider;
import de.vsy.shared_module.packet_creation.identification_provider.ErrorIdentificationProvider;
import de.vsy.shared_module.packet_creation.identification_provider.RelationIdentificationProvider;
import de.vsy.shared_module.packet_creation.identification_provider.StatusIdentificationProvider;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ContentIdentificationProviderImpl implements ContentIdentificationProvider {

  protected final Map<Class<? extends PacketContent>, Supplier<ContentIdentifier>> registeredIdentifiers;

  {
    registeredIdentifiers = new HashMap<>();

    registeredIdentifiers.putAll(new ChatIdentificationProvider().getIdentifiers());
    registeredIdentifiers.putAll(new StatusIdentificationProvider().getIdentifiers());
    registeredIdentifiers.putAll(new RelationIdentificationProvider().getIdentifiers());
    registeredIdentifiers.putAll(new AuthenticationIdentificationProvider().getIdentifiers());
    registeredIdentifiers.putAll(new ErrorIdentificationProvider().getIdentifiers());
  }

  public void addContentIdentifier(
      Map<Class<? extends PacketContent>, Supplier<ContentIdentifier>> identifiersToAdd) {
    if (identifiersToAdd != null) {

      for (var newIdentifier : identifiersToAdd.entrySet()) {
        var newContent = newIdentifier.getKey();
        var newSupplier = newIdentifier.getValue();

        if (newContent != null && newSupplier != null) {
          registeredIdentifiers.put(newContent, newSupplier);
        }

        registeredIdentifiers.put(newIdentifier.getKey(), newIdentifier.getValue());
      }
    }
  }

  @Override
  public ContentIdentifier getContentIdentifier(PacketContent data) {
    var identificationSupplier = registeredIdentifiers.get(data.getClass());

    return (identificationSupplier != null) ? identificationSupplier.get() : null;
  }
}
