package de.vsy.shared_module.shared_module.packet_content_translation;

import de.vsy.shared_transmission.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.shared_transmission.packet.content.error.ErrorDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationRequestDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationResponseDTO;

import java.util.HashMap;
import java.util.Map;

public
class HumanInteractionRequestTranslator {

    private static final Map<Class<? extends Translatable>, PacketContentTranslator> translators;

    static {
        translators = new HashMap<>();
        translators.put(ContactRelationRequestDTO.class,
                        new ContactRelationRequestTranslator());
        translators.put(ContactRelationResponseDTO.class,
                        new ContactRelationResponseTranslator());
        translators.put(ErrorDTO.class, null);
    }

    private
    HumanInteractionRequestTranslator () {}

    public static
    String translate (Translatable request) {
        final var translator = translators.get(request.getClass());
        if (translator != null) {
            return translator.translate(request);
        }
        return "Keinen gültigen Uebersetzer gefunden für: \n" + request;
    }
}
