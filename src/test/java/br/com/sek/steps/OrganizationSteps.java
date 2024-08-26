package br.com.sek.steps;

import br.com.sek.testContext.OrganizationTestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class OrganizationSteps {
    private final OrganizationTestContext organizationTestContext;

    public OrganizationSteps(OrganizationTestContext organizationTestContext){
        this.organizationTestContext = organizationTestContext;
    }

    @Given("I want to create a organization {string} members")
    public void iWantToCreateAOrganizationMembers(String string) {
        this.organizationTestContext.generateOrganization(string);
    }
    @When("I send organization request")
    public void iSendOrganizationRequest() {
        this.organizationTestContext.sendRequest();
    }
    @Then("the organization is created successfully")
    public void theOrganizationIsCreatedSuccessfully() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
