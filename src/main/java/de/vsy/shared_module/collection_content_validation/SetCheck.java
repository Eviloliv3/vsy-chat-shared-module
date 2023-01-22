package de.vsy.shared_module.collection_content_validation;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.dto.CommunicatorDTO;

import java.util.Optional;
import java.util.Set;

public class SetCheck {

    private SetCheck() {
    }

    /**
     * Checks set of CommunicatorDTO for invalid CommunicatorDTO instances and creates an notification
     * String, indicating which elements contain invalid data.
     *
     * @param setData the set to check
     * @return Optional containing notification String if invalid elements were found; empty Optional
     * otherwise
     */
    public static Optional<String> checkMemberList(final Set<CommunicatorDTO> setData) {
        var deadInfo = new StringBuilder();

        if (setData != null) {

            for (var currentCommunicator : setData) {
                final var checkString = BeanChecker.checkBean(currentCommunicator);

                checkString.ifPresent(
                        s -> deadInfo.append("; element : ").append(currentCommunicator).append("/")
                                .append(s));
            }
        } else {
            deadInfo.append("Contact list not specified.");
        }
        return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
    }
}
