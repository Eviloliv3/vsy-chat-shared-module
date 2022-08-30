/*
 *
 */
package de.vsy.shared_module.shared_module.data_element_validation;

import static de.vsy.shared_utility.standard_value.StandardIdProvider.STANDARD_CLIENT_ID;
import static de.vsy.shared_utility.standard_value.StandardIdProvider.STANDARD_SERVER_ID;

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
    String checkData (final Integer toCheck) {
        final String errorMessage;

        if (toCheck != null && (toCheck > 15000 || toCheck == STANDARD_SERVER_ID ||
                                toCheck == STANDARD_CLIENT_ID)) {
            errorMessage = null;
        } else {
            errorMessage = "Fehlerhafte Entitäts-Id: " + toCheck;
        }
        return errorMessage;
    }
}
