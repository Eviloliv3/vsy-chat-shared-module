/*
 *
 */
package de.vsy.shared_module.shared_module.data_element_validation;

import java.util.Optional;

import static de.vsy.shared_utility.standard_value.StandardIdProvider.*;

/** Simple tool: IdCheck. */
public
class IdCheck {

    private
    IdCheck () {}

    /**
     * Check dataManagement.
     *
     * @param toCheck the to check
     *
     * @return the string
     */
    public static
    Optional<String> checkData (final Integer toCheck) {

        if (toCheck != null && (toCheck > 15000 || toCheck == STANDARD_SERVER_ID ||
                                toCheck == STANDARD_CLIENT_ID || toCheck == STANDARD_CLIENT_BROADCAST_ID)) {
            return Optional.empty();
        } else {
            return Optional.of("Fehlerhafte Entitäts-Id: " + toCheck);
        }
    }
}
