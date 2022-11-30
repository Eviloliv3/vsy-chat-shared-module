package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.error.ErrorDTO;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationRequestDTO;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationResponseDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NotificationTranslator {

  protected static final Map<Class<? extends Translatable>, Function<Translatable, String>> translators;

  static {
    translators = new HashMap<>();
    translators.put(ContactRelationRequestDTO.class, ContactRelationRequestTranslator::translate);
    translators.put(ContactRelationResponseDTO.class, ContactRelationResponseTranslator::translate);
    translators.put(ErrorDTO.class, ErrorTranslator::translate);
  }

  protected NotificationTranslator() {
  }

  public static String translate(Translatable request) {
    final var translator = translators.get(request.getClass());
    if (translator != null) {
      return translator.apply(request);
    }
    return "No translator found for: " + request;
  }
}
