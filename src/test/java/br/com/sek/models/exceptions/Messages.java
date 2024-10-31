package br.com.sek.models.exceptions;

import java.util.ArrayList;
import java.util.List;

import static br.com.sek.models.exceptions.Messages.errorMessages.*;

public class Messages {

    public static class errorMessages {
        public static final String expectedBadRequest = "Expected category 'BAD_REQUEST' but got '";
        public static final String responseNotSet = "Response has not been set.";
        public static final String unknownField = "Unknown field: ";
        public static final String invalidProductStatus = "Invalid validation status or no products registered. ";
        public static final String invalidOrganizationStatus = "Invalid validation status or no organizations registered. ";
        public static final String productIsNull = "Product is null. Cannot send request.";
        public static final String organizationIsNull = "Organization is null. Cannot send request.";
        public static final String productCodeNotSet = "Product Code is not set. Please call sendRequest() first.";
        public static final String organizationCodeNotSet = "Organization Code is not set. Please call sendRequest() first.";
        public static final String organizationNotAssociated = "Child Organization is not associate.";
        public static final String organizationIsStillsAssociated = "Child Organization is still associate.";
        public static final String noRegisteredOrganization = "There are no registered organization.";
        public static final String noRegisteredProducts = "There are no registered products.";
        public static final String invalidParameter ="Invalid request. Please check your input parameters.";
    }

    public static final List<String> ALL_ERROR_MESSAGES;

    static {
        ALL_ERROR_MESSAGES = new ArrayList<>();
        ALL_ERROR_MESSAGES.add(expectedBadRequest);
        ALL_ERROR_MESSAGES.add(responseNotSet);
        ALL_ERROR_MESSAGES.add(unknownField);
        ALL_ERROR_MESSAGES.add(invalidProductStatus);
        ALL_ERROR_MESSAGES.add(invalidOrganizationStatus);
        ALL_ERROR_MESSAGES.add(productIsNull);
        ALL_ERROR_MESSAGES.add(organizationIsNull);
        ALL_ERROR_MESSAGES.add(productCodeNotSet);
        ALL_ERROR_MESSAGES.add(organizationCodeNotSet);
        ALL_ERROR_MESSAGES.add(organizationNotAssociated);
        ALL_ERROR_MESSAGES.add(organizationIsStillsAssociated);
        ALL_ERROR_MESSAGES.add(noRegisteredOrganization);
        ALL_ERROR_MESSAGES.add(noRegisteredProducts);
        ALL_ERROR_MESSAGES.add(invalidParameter);
    }

}
