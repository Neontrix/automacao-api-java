package br.com.sek.services;

import br.com.sek.models.request.User;
import br.com.sek.models.response.SignUpResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.qameta.allure.Allure.step;

public class Validation {

    public void validateNotNull(Object object) {
        Assertions.assertNotNull(object);
    }

    public void validateIsTrue(boolean verification){
        Assertions.assertTrue(verification);
    }

    public void validateMessageErrorAndStatusCode(Response response,String path, String error, String statusCode){
        this.validateResponseStatusCode(response,statusCode);
        this.validateMessageError(response,path,error);
    }

    public void validateMessageError(Response response,String path, String error){
        Assertions.assertEquals(error, response.getBody().path(path));
        step("Error message \"".concat(error).concat("\" successfully validated"));
    }

    public void validateContainsMessageError(Response response,String path, String error){
        Assertions.assertTrue(response.getBody().path(path).toString().contains(error));
        step("Error message \"".concat(error).concat("\" successfully validated"));
    }

    public void validateResponseStatusCode(Response response, String statusCode){
        Assertions.assertEquals(Integer.parseInt(statusCode), response.statusCode());
        step("Status code validated: ".concat(statusCode));
    }

    public void validateSignUp(Response response, User user){
        Assertions.assertEquals(201, response.statusCode(), "Status Code");

        SignUpResponse objResponse = response.as(SignUpResponse.class);

        Assertions.assertEquals(user.getName(), objResponse.getName(), "Name");
        Assertions.assertEquals(user.getEmail(), objResponse.getEmail(), "E-mail");
        Assertions.assertEquals(user.getCompany(), objResponse.getCompany(), "Company");
        Assertions.assertEquals(user.getPhone().toString(), objResponse.getPhone(), "Phone");
        step("All fields were successfully validated");
    }
    
}
