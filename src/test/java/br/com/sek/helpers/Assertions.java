package br.com.sek.helpers;

import br.com.sek.models.exceptions.ExceptionsMessages;
import br.com.sek.models.exceptions.Messages;
import io.restassured.response.Response;
import lombok.Setter;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static br.com.sek.models.exceptions.Messages.ALL_ERROR_MESSAGES;

public class Assertions {
    @Setter
    private Response response;

    List<String> errorMessages = ALL_ERROR_MESSAGES;

    public void assertNotNull(Object object) {
        org.junit.jupiter.api.Assertions.assertNotNull(object);
    }

    public void assertIsTrue(boolean verification){
        assertTrue(verification);
    }

    public void assertMessageErrorAndStatusCode(Response response, String path, String error, String statusCode){
        this.assertResponseStatusCode(response,statusCode);
        this.assertMessageError(response,path,error);
    }

    public void assertMessageError(Response response,String path, String error){
        org.junit.jupiter.api.Assertions.assertEquals(error, response.getBody().path(path));
        step("Error message \"".concat(error).concat("\" successfully validated"));
    }

    public void assertContainsMessageError(Response response, String path, String error){
        assertTrue(response.getBody().path(path).toString().contains(error));
        step("Error message \"".concat(error).concat("\" successfully validated"));
    }

    public void assertResponseStatusCode(Response response, String statusCode){
        org.junit.jupiter.api.Assertions.assertEquals(Integer.parseInt(statusCode), response.statusCode());
        step("Status code validated: ".concat(statusCode));
    }
    public void assertValues(String actual, String expected){
        org.junit.jupiter.api.Assertions.assertEquals(actual, expected);
        step("Values validated: ".concat(expected));
    }

    public String returnJsonAttributeValue(Response response, String attributeName){
        Map<String, Object> content = response.jsonPath().getMap("$");
        return (String) content.get(attributeName);
    }

    public void verifyErrorFieldsDisplayed(String field, int expectedStatus){
        if (response == null) {
            throw new IllegalStateException(Messages.errorMessages.responseNotSet);
        }

        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != expectedStatus) {
            throw new AssertionError(String.format(ExceptionsMessages.fromCode(expectedStatus).getMessage(), actualStatusCode));
        }

        if (actualStatusCode == 409){
            String actualCategory = response.jsonPath().getString("category");
            if (!"CONFLICT".equals(actualCategory)) {
                throw new AssertionError(String.format(ExceptionsMessages.fromCode(expectedStatus).getMessage(), actualStatusCode));
            }
        }else {
            // Verifica se o campo esperado está presente na lista de campos de erro
            response.then().body("fields", hasItem(field));
        }
    }

    public void assertErrorMessage404(String message, String field) {
        String errorMessage = "Invalid request. Please check your input parameters.";
        String fieldsMessage = String.format(
                "\"fields\": [\n        \"%s\"\n    ]",
                field
        );

        // Verifica se a mensagem de erro contém a parte fixa
        assertThat(errorMessage, containsString("Invalid request. Please check your input parameters."));

        // Verifica se a mensagem dos campos contém o nome variável
        assertThat(fieldsMessage, containsString(field));
    }
}
