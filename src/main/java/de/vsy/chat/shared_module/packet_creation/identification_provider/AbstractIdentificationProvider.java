package de.vsy.chat.shared_module.packet_creation.identification_provider;

import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Map.copyOf;

public
class AbstractIdentificationProvider {

    protected final Map<Class<? extends PacketContent>, Supplier<ContentIdentifier>> identifiers;

    protected
    AbstractIdentificationProvider () {
        identifiers = new HashMap<>();
    }

    public
    Map<Class<? extends PacketContent>, Supplier<ContentIdentifier>> getIdentifiers () {
        return copyOf(identifiers);
    }
}
