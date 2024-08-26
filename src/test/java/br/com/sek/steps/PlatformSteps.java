package br.com.sek.steps;

import br.com.sek.models.response.AdminActions;
import br.com.sek.services.Validation;
import br.com.sek.testContext.TestContext;
import com.google.gson.Gson;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;


public class PlatformSteps {
    private final TestContext testContext;
    private final Validation validation;

    public PlatformSteps(TestContext testContext, Validation validation) {
        this.testContext = testContext;
        this.validation = validation;
    }

    @Given("I have access to the platform")
    public void iHaveAccessToThePlatform() {
        this.testContext.GenerateToken();
    }

    @When("I query the data")
    public void iQueryTheData() {
        testContext.ListActions();
    }

    @Then("I see the scope and types for each profile")
    public void iSeeTheScopeAndTypesForEachProfile() {
        Response resp = testContext.getResponse();

        AdminActions[] test = new Gson().fromJson(resp.body().print(), AdminActions[].class);

        this.validation.validateIsTrue(test.length > 0);
        step("Profiles successfully validated");
    }

    @When("I query the data with user {string}")
    public void iQueryTheDataWithUser(String credential) {
        testContext.ListActionsByCredential(credential);
    }

    @Then("I see the error message {string} and status code {string}")
    public void iSeeTheErrorMessageAndStatusCode(String error, String statusCode) {
        this.validation.validateMessageErrorAndStatusCode(testContext.getResponse(), "detail", error, statusCode);
    }

    @Given("I have a user to sign up")
    public void iHaveAUserToSignUp() {
        testContext.GenerateSignUp();
    }

    @When("I send the Signup request")
    public void iSendTheSignupRequest() {
        testContext.sendSingUp();
    }

    @Then("I see the successful signup registration")
    public void iSeeTheSuccessfulSignupRegistration() {
        this.validation.validateSignUp(this.testContext.getResponse(), this.testContext.getUser());
    }

    @Given("I remove the field {string}")
    public void iRemoveTheField(String field) {
        this.testContext.removeFieldByUser(field);
    }

    @Then("I see the error message of field {string}")
    public void iSeeTheErrorMessageOfField(String field) {
        Response response = this.testContext.getResponse();
        this.validation.validateResponseStatusCode(response, "400");
        this.validation.validateContainsMessageError(response, "message","Invalid request. Please check your input parameters.");
        this.validation.validateContainsMessageError(response, "fields[0]",field);
    }

    @Given("I set the field {string} to blank")
    public void iSetTheFieldToBlank(String field) {
        this.testContext.setFieldBlank(field);
    }

}
