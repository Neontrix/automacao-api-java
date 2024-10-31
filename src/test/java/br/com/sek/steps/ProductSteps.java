package br.com.sek.steps;

import br.com.sek.helpers.Assertions;
import br.com.sek.services.ProductService;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductSteps {
    private final ProductService productService;

    public ProductSteps(ProductService productService, Assertions assertions) {
        this.productService = productService;
    }

    // region @Given

    @Given("I want to generate a product {string} addons")
    public void iWantToGenerateAProductAddons(String addonType) {
        this.productService.generateFakerProduct(addonType);
    }

    @Given("I want to generate a product without the {string}")
    public void iWantToGenerateAProductWithoutThe(String field) {
        this.productService.generateFakerProductWithoutField(field);
    }

    @Given("I want to generate a product with the empty {string}")
    public void iWantToGenerateAProductWithTheEmpty(String field) {
        this.productService.generateFakerProductWithEmptyField(field);
    }

    @Given("I have an active product")
    public void iHaveAnActiveProduct() {
        this.productService.generateFakerProduct("with");
        this.productService.sendCreateProductRequest();
    }
    // endregion

    // region @When
    @When("I send request to create product")
    public void iSendRequestToCreateProduct() {
        this.productService.sendCreateProductRequest();
    }

    @When("I send a invalid request to create product")
    public void iSendAInvalidRequestToCreateProduct() {
        this.productService.sendCreateProductInvalidRequest();
    }


    @When("I send request to delete product with {string} code")
    public void iSendRequestToDeleteProductWithCode(String validationStatus) {
        this.productService.deleteCreatedProduct(validationStatus);
    }

    @When("I send request to search a product with {string} code")
    public void iSendRequestToSearchAProductWithCode(String validationStatus) {
        this.productService.searchProducts(validationStatus);
    }

    @When("I send request to search a list of products")
    public void iSendRequestToSearchAListOfProducts() {
        this.productService.searchProductsList();
    }

    @When("I send request to search product with invalid {string} and value {string}")
    public void iSendRequestToSearchProductWithInvalidAndValue(String field, String value) {
        this.productService.searchInvalidField(field, value);
    }

    @When("I send request to update {string} product")
    public void iSendRequestToUpdateProduct(String validationStatus) {
        this.productService.updateOriginProduct(validationStatus);
    }

    @When("I send request to update the product without inform the {string}")
    public void iSendRequestToUpdateTheProductWithoutInformThe(String field) {
        this.productService.updateProductWithoutField(field);
    }
    // endregion

    // region @Then
    @Then("the product is created successfully")
    public void theProductIsCreatedSuccessfully() {
        this.productService.validateProductCreated();
        this.productService.deleteProduct("valid");
    }

    @Then("an error message is displayed indicating that the {string} product is required")
    public void anErrorMessageIsDisplayedIndicatingThatTheIsRequired(String field) {
        this.productService.verifyErrorFieldsDisplayed(field, 400);
    }

    @Then("an error message is displayed indicating that the {string} product cannot be empty")
    public void anErrorMessageIsDisplayedIndicatingThatTheProductCannotBeEmpty(String field) {
        this.productService.verifyErrorFieldsDisplayed(field, 400);
    }

    @Then("the product is deleted")
    public void theProductIsDeleted() {
        this.productService.searchDeletedProduct("deleted");
    }
    @Then("the product is not deleted")
    public void theProductIsNotDeleted() {
        this.productService.searchDeletedProduct("notDeleted");
    }

    @Then("the product is displayed successfully")
    public void theProductIsDisplayedSuccessfully() {
        this.productService.validateSearchProduct();
        this.productService.deleteProduct("valid");
    }

    @Then("the product is not displayed")
    public void theProductIsNotDisplayed() {
        this.productService.validateSearchProduct();
    }

    @Then("the list of products is not displayed")
    public void theListOfProductsIsNotDisplayed() {
        this.productService.resultProductsWithInvalidField();
    }

    @Then("the list of products is displayed successfully")
    public void theListOfProductsIsDisplayedSuccessfully() {
        this.productService.resultProductsList();
    }

    @Then("the product is updated successfully")
    public void theProductIsUpdatedSuccessfully() {
        this.productService.searchUpdatedStatusProduct("updated");
        this.productService.deleteNewProduct("valid");
    }

    @Then("the product is not updated")
    public void theProductIsNotUpdated() {
        this.productService.searchUpdatedStatusProduct("invalidProductUpdated");
    }

    @Then("the product is not updated without mandatory field")
    public void theProductIsNotUpdatedWithoutMandatoryField() {
        this.productService.searchUpdatedStatusProduct("notUpdated");
        this.productService.deleteProduct("valid");
    }
    // endregion
}
