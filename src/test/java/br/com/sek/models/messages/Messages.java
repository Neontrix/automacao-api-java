package br.com.sek.models.messages;

public class Messages {

    public static class errorMessages {
        public static final String expected400 = "Expected status code 400 but got ";
        public static final String expected404 = "Expected status code 404 but got ";
        public static final String expectedBadRequest = "Expected category 'BAD_REQUEST' but got '";
        public static final String responseNotSet = "Response has not been set.";
        public static final String uknownField = "Unknown field: ";
        public static final String invalidProductStatus = "Invalid validation status or no products registered. ";
    }

    public static class successMessages {

    }

}
