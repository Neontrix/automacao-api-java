package br.com.sek.steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class OrganizationSteps {
    private final br.com.sek.services.OrganizationService organizationService;

    public OrganizationSteps(br.com.sek.services.OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    // region Reusable methods
    @After
    public void cleanupAfterScenario(Scenario scenario) {
        // Verifica se o cenário tem a tag @cleanup
        if (scenario.getSourceTagNames().contains("@cleanup")) {
            System.out.println("CLEANUP - DESATIVANDO E VALIDANDO ORGANIZAÇÃO CRIADA");
            organizationService.deactivateOrganization("valid");
            this.organizationService.validateDeactivatedOrganization();
            // Endpoint de delete desativado
//            organizationService.removeOrganizations();
        }
    }
    private void generateAndCreateOrganization() {
        this.organizationService.generateFakerOrganization();
        this.organizationService.sendCreateOrganizationRequest();
    }
    //endregion Reusable methods

    // region @Given
    @Given("I want to generate a organization")
    public void iWantToGenerateAOrganization() {
        this.organizationService.generateFakerOrganization();
    }
    @Given("I want to create a organization without the {string}")
    public void iWantToCreateAOrganizationWithoutThe(String parameter) {
        this.organizationService.generateFakerOrganizationWithoutParameter(parameter);
    }
    @Given("I want to create a organization with the empty {string}")
    public void iWantToCreateAOrganizationWithTheEmpty(String field) {
        this.organizationService.generateFakerOrganizationWithEmptyField(field);
    }
    @Given("I have an active organization")
    public void iHaveAnActiveOrganization() {
        this.organizationService.sendCreateAndGetOrganizationId();
    }
    @Given("I want to associate a organization")
    public void iWantToAssociateAOrganization() {
        this.organizationService.generateFakerOrganization();
        this.organizationService.createThreeOrganizationsRequest();
    }
    @Given("I want to disassociate a organization")
    public void iWantToDisassociateAOrganization() {
        this.organizationService.generateFakerOrganization();
        this.organizationService.createTwoOrganizationsRequest();
    }
    // endregion @Given

    // region @When
    @When("I send request to create organization")
    public void iSendRequestToCreateOrganization() {
        this.organizationService.sendCreateOrganizationRequest();
    }
    @When("I send a invalid request to create organization with status {string}")
    public void iSendAInvalidRequestToCreateOrganizationWithStatus(String status) {
        this.organizationService.sendInvalidCreateOrganizationRequest(status);
    }
    @When("I send request to search a organization with {string} code")
    public void iSendRequestToSearchAOrganizationWithCode(String validationStatus) {
        generateAndCreateOrganization();
        this.organizationService.searchOrganization(validationStatus);
    }
    @When("I send request to search a list of organizations")
    public void iSendRequestToSearchAListOfOrganizations() {
        generateAndCreateOrganization();
        this.organizationService.searchListOrganizations();
    }
    @When("I send request to search list of organizations with invalid {string} and value {string}")
    public void iSendRequestToSearchListOfOrganizationsWithInvalidAndValue(String field, String value) {
        this.organizationService.searchListOrganizationsInvalidFields(field, value);
    }
    @When("I send request to associate organization")
    public void iSendRequestToAssociateOrganization() {
        this.organizationService.associateOrganizations();
    }
    @When("I send request to associate organization in another holding")
    public void iSendRequestToAssociateOrganizationInAnotherHolding() {
        this.organizationService.associateOrganizationsInAnotherHolding();
    }
    @When("I send request to disassociate organization")
    public void iSendRequestToDisassociateOrganization() {
        this.organizationService.associateOrganizations();
        this.organizationService.disassociateOrganizations();
    }
    @When("I send request to update {string} organization")
    public void iSendRequestToUpdateOrganization(String status) {
        generateAndCreateOrganization();
        this.organizationService.updateOrganization(status);
    }
    @When("I send request to update the organization without inform the {string}")
    public void iSendRequestToUpdateTheOrganizationWithoutInformThe(String field) {
        generateAndCreateOrganization();
        this.organizationService.updateOrganizationWithoutField(field);
    }
    @When("I send request to delete organization with {string} code")
    public void iSendRequestToDeleteOrganizationWithCode(String status) {
        generateAndCreateOrganization();
        organizationService.deactivateOrganization(status);
    }
    // endregion @When

    // region @Then
    @Then("the organization is displayed successfully")
    public void theOrganizationIsDisplayedSuccessfully() {
        this.organizationService.validateSearchOrganization();
    }
    @Then("the organization is not displayed")
    public void theOrganizationIsNotDisplayed() {
        this.organizationService.validateSearchOrganization();
    }
    @Then("the list of organizations is displayed successfully")
    public void theListOfOrganizationsIsDisplayedSuccessfully() {
        this.organizationService.validateSearchListOrganizations();
    }
    @Then("the list of organizations is not displayed")
    public void theListOfOrganizationsIsNotDisplayed() {
        this.organizationService.resultOrganizationsWithInvalidField();
    }
    @Then("the organization is created successfully")
    public void theOrganizationIsCreatedSuccessfully() {
        this.organizationService.validateOrganization("create");
    }
    @Then("an error message is displayed indicating that the {string} organization is required")
    public void anErrorMessageIsDisplayedIndicatingThatTheOrganizationIsRequired(String parameter) {
        this.organizationService.verifyErrorFieldsDisplayed(parameter, 400);
    }
    @Then("an error message is displayed indicating that the {string} organization cannot be empty")
    public void anErrorMessageIsDisplayedIndicatingThatTheOrganizationCannotBeEmpty(String field) {
        this.organizationService.verifyErrorFieldsDisplayed(field, 400);
    }
    @Then("the organization is associate successfully")
    public void theOrganizationIsAssociateSuccessfully() {
        this.organizationService.validateOrganizationAssocied();
    }
    @Then("the organization is not associate")
    public void theOrganizationIsNotAssociate() {
        this.organizationService.validateFirstAndNotThirdAssociation();
        this.organizationService.deactivateOrganizations();
    }
    @Then("the organization is disassociate")
    public void theOrganizationIsDisassociate() {
        this.organizationService.validateOrganizationDisassociated();
    }
    @Then("the organization is updated successfully")
    public void theOrganizationIsUpdatedSuccessfully() {
        this.organizationService.validateOrganization("update");
    }
    @Then("the organization is not updated")
    public void theOrganizationIsNotUpdated() {
        this.organizationService.validateSearchOrganization();
    }
    @Then("the organization is not updated without mandatory field")
    public void theOrganizationIsNotUpdatedWithoutMandatoryField() {
        this.organizationService.validateOmittedFieldIsRequired();
    }
    @Then("the organization is deactivated")
    public void theOrganizationIsDeactivated() {
        this.organizationService.validateDeactivatedOrganization();
        // Endpoint de delete desativado
//        organizationService.removeOrganizations();
    }
    @Then("the organization is not deleted")
    public void theOrganizationIsNotDeleted() {
        this.organizationService.validateDeactivatedOrganization();
        // Agora está desativando, validando e removendo a massa criada
        organizationService.deactivateOrganization("valid");
        this.organizationService.validateDeactivatedOrganization();
//        organizationService.removeOrganizations();
    }
    // endregion @Then
}
