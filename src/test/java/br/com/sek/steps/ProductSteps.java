package br.com.sek.steps;

import br.com.sek.services.Validation;
import br.com.sek.testContext.ProductTestContext;
import br.com.sek.testContext.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductSteps {
    private final ProductTestContext productTestContext;
    private final Validation validation;

    public ProductSteps(ProductTestContext productTestContext, Validation validation) {
        this.productTestContext = productTestContext;
        this.validation = validation;
    }

    @Given("I want to generate a product {string} addons")
    public void iWantToGenerateAProductAddons(String addonType) {
        this.productTestContext.generateProduct(addonType);
    }
    @When("I send product request")
    public void iSendProductRequest() {
        this.productTestContext.sendRequest();
    }
    @Then("the product is created successfully")
    public void theProductIsCreatedSuccessfully() {
        this.productTestContext.getProduct();
    }
    @Given("I want to generate a product without the {string}")
    public void iWantToGenerateAProductWithoutThe(String field) {
        this.productTestContext.generateProductWithoutField(field);
    }
    @Then("an error message is displayed indicating that the {string} is required")
    public void anErrorMessageIsDisplayedIndicatingThatTheIsRequired(String field) {
        this.productTestContext.verifyErrorFieldsDisplayed(field);
    }
    @Given("I want to generate a product with the empty {string}")
    public void iWantToGenerateAProductWithTheEmpty(String field) {
        this.productTestContext.generateProductWithEmptyField(field);
    }
    @Then("an error message is displayed indicating that the {string} cannot be empty")
    public void anErrorMessageIsDisplayedIndicatingThatTheCannotBeEmpty(String field) {
        this.productTestContext.verifyErrorFieldsDisplayed(field);
    }
    @When("I want to delete product with {string} ID")
    public void iWantToDeleteProductWithID(String validationStatus) {
        this.productTestContext.deleteProduct(validationStatus);
    }
    @Then("the product is deleted")
    public void theProductIsDeleted() {
        this.productTestContext.searchDeleteProduct("deleted");
    }
    @Then("the product is not deleted")
    public void theProductIsNotDeleted() {
        this.productTestContext.searchDeleteProduct("notDeleted");
    }
    @When("I want to search a product with {string} ID")
    public void iWantToSearchAProductWithID(String validationStatus) {
        this.productTestContext.searchProducts(validationStatus);
    }
    @Then("the product is displayed successfully")
    public void theProductIsDisplayedSuccessfully() {
        this.productTestContext.searchProductById();
    }
    @Then("the product is not displayed")
    public void theProductIsNotDisplayed() {
        this.productTestContext.searchProductById();
    }
    @Then("the list of products is not displayed")
    public void theListOfProductsIsNotDisplayed() {
        this.productTestContext.resultProductsWithInvalidField();
    }
    @When("I want to search list of products")
    public void iWantToSearchListOfProducts() {
        this.productTestContext.searchProductsList();
    }
    @Then("the list of products is displayed successfully")
    public void theListOfProductsIsDisplayedSuccessfully() {
        this.productTestContext.resultProductsList();
    }

    @When("I want to search product with invalid {string} and value {string}")
    public void iWantToSearchProductWithInvalidAndValue(String field, String value) {
        this.productTestContext.searchInvalidField(field, value);
    }
    @When("I want to update {string} product")
    public void iWantToUpdateProduct(String validationStatus) {
        this.productTestContext.updateProduct(validationStatus);
    }
    @Then("the product is updated successfully")
    public void theProductIsUpdatedSuccessfully() {
        this.productTestContext.searchUpdateProduct();
    }
    @Given("I want to update the product without inform the {string}")
    public void iWantToUpdateTheProductWithoutInformThe(String field) {
        this.productTestContext.updateProductWithoutField(field);
    }
    @Then("the product is not updated")
    public void theProductIsNotUpdated() {

    }
}
