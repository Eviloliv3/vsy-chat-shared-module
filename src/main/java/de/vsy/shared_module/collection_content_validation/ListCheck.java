
package de.vsy.shared_module.collection_content_validation;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Simple tool: List check.
 */
public class ListCheck {

    private ListCheck() {
    }

    /**
     * Checks set of TextMessageDTO for invalid TextMessageDTO instances and creates an notification String,
     * indicating which elements contain invalid data.
     *
     * @param listData the list to check
     * @return Optional containing notification String if invalid elements were found; empty Optional
     * otherwise
     */
    public static Optional<String> checkMessageDataList(final List<TextMessageDTO> listData) {
        var deadInfo = new StringBuilder();

        if (listData != null) {

            for (var msgPos = listData.size() - 1; msgPos >= 0; msgPos--) {
                final var checkString = BeanChecker.checkBean(listData.get(msgPos));

                if (checkString.isPresent()) {
                    deadInfo.append("Erroneous message list position: ").append(msgPos)
                            .append(checkString.get());
                }
            }
        } else {
            deadInfo.append("No message list specified.");
        }
        return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
    }

    /**
     * Checks set of CommunicatorDTO for invalid CommunicatorDTO instances and creates an notification
     * String, indicating which elements contain invalid data.
     *
     * @param listData the set to check
     * @return Optional containing notification String if invalid elements were found; empty Optional
     * otherwise
     */
    public static Optional<String> checkMessageDataSet(final List<TextMessageDTO> listData) {
        var deadInfo = new StringBuilder();

        if (listData != null) {

            for (var currentMessage : listData) {
                final var checkString = BeanChecker.checkBean(currentMessage);

                checkString.ifPresent(s -> deadInfo.append("\nErroneous message: ").append(s).append(". "));
            }
        } else {
            deadInfo.append("No messages specified.");
        }
        return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
    }
}
