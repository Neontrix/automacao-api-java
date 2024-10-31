package br.com.sek.services;

import br.com.sek.helpers.Assertions;
import br.com.sek.models.exceptions.ExceptionsMessages;
import br.com.sek.models.exceptions.Messages;
import br.com.sek.models.request.product.Product;
import br.com.sek.models.response.ProductResponse;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.Products;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.*;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.equalTo;

public class ProductService extends Assertions {
    private final PlatformSek platformSek;
    private final Products products;
    private final Assertions assertions;
    @Getter
    private Response response;
    private Product product;
    private String  productCode;
    private String productName;
    private String validationStatus;
    private List<Map<String, Object>> content;
    private static final Set<String> VALID_FIELDS = new HashSet<>(Set.of(
            "name", "code", "description", "addons"
    ));

    public ProductService(PlatformSek platformSek, Products products, Assertions assertions) {
        this.platformSek = platformSek;
        this.products = products;
        this.assertions = assertions;
    }

    private static final String invalidCode = "999SASX";
    private static final String inexistentCode = "999SBR";

    // region Products Methods
    public void sendCreateProductRequest() {
        if (this.product == null) {
            System.out.println(Messages.errorMessages.productIsNull);
            return;
        }
        this.response = this.platformSek.postProduct(this.product);

        assertResponseStatusCode(this.response,"201");
        extractCreatedProductCode();
    }

    public void sendCreateProductInvalidRequest() {
        if (this.product == null) {
            System.out.println(Messages.errorMessages.productIsNull);
            return;
        }
        this.response = this.platformSek.postProduct(this.product);

        //validateResponseStatusCode(this.response,"400");
        extractCreatedProductCode();
    }

    public void validateProductCreated() {
        if (this.productCode != null) {
            System.out.println("GET product with ID after register: ");
            this.response = this.platformSek.getProductResponse(this.productCode);

            validateProductValues(this.response);
        } else {
            throw new IllegalStateException(Messages.errorMessages.productCodeNotSet);
        }
    }

    public void generateFakerProduct(String addonType){
        this.product = this.products.getProducts(addonType);
        step("Body: " + this.product);
    }

    public void generateFakerProductWithoutField(String field) {
        this.product = this.products.getProductsWithoutField(field);
        step("Body: " + this.product);
    }

    public void generateFakerProductWithEmptyField(String field) {
        this.product = this.products.getProductsWithEmptyField(field);
        step("Body: " + this.product);
    }

    public void verifyErrorFieldsDisplayed(String field, int expectedStatus) {
        assertions.setResponse(response);
        this.assertions.verifyErrorFieldsDisplayed(field, expectedStatus);
        step("Body: " + response.getBody().asString());
    }

    public void searchProductsList(){
        this.response = this.platformSek.getProductsList();
        assertResponseStatusCode(this.response,"200");
    }

    public void resultProductsList(){
        List<Map<String, Object>> content = response.path("content");
        if(content != null && !content.isEmpty()){
            System.out.println("List of registered products: ");
            searchProductsList();
        }else{
            System.out.println(Messages.errorMessages.noRegisteredProducts);
        }
    }

/*    public void searchProducts(String validationStatus) {
        this.validationStatus = validationStatus;

        System.out.println("Generating product: ");
        generateFakerProduct("without");
        sendCreateProductRequest();
        this.productCode = extractCreatedProductCode();

        switch (validationStatus) {
            case "valid":
                if (response != null && response.statusCode() == 200) {
                    System.out.println("Product Code: " + this.productCode);
                    this.response = this.platformSek.getProductWithCode(this.productCode);
                    assertResponseStatusCode(this.response, "200");
                } else {
                    System.out.println("No products found with this Code: " + this.productCode);
                }
                break;

            case "invalid":
                System.out.println("Invalid Code: " + invalidCode);
                this.response = this.platformSek.getProductWithCode(invalidCode);
                assertResponseStatusCode(this.response, "404");
                break;

            default:
                System.out.println("No products found");
                break;
        }
    }*/

    public void searchProducts(String validationStatus) {
        this.validationStatus = validationStatus;
        System.out.println("Search product with " + validationStatus + " status...");
        if ("valid".equals(validationStatus)) {
            System.out.println("Generating product...");
            generateFakerProduct("without");
            sendCreateProductRequest();
            extractCreatedProductCode();

            if (response != null && response.statusCode() == 201) {
                System.out.println("Search with product code: " + this.productCode);
                this.response = this.platformSek.getProductWithCode(this.productCode);
            } else {
                System.out.println("No products found with this product code: " + this.productCode);
                throw new IllegalStateException(Messages.errorMessages.noRegisteredProducts);
            }
        } else if ("invalid".equals(validationStatus)) {
            System.out.println("Search with invalid product code: " + invalidCode);
            this.response = this.platformSek.getProductWithCode(invalidCode);
        } else {
            System.out.println("No products found");
        }
    }

/*    public void validateSearchProduct() {
        switch (validationStatus) {
            case "valid":
                if (this.productCode != null) {
                    System.out.println("Search with Code: " + this.productCode);
                    this.productCode = extractCreatedProductCode();
                    this.response = this.platformSek.getProductWithCode(this.productCode);
                    assertResponseStatusCode(this.response, "200");
                } else {
                    System.out.println(Messages.errorMessages.invalidProductStatus);
                }
                break;

            case "invalid":
                System.out.println("Search with invalid Code: " + invalidCode);
                this.response = this.platformSek.getProductWithCode(invalidCode);
                assertResponseStatusCode(this.response, "404");
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
                break;
        }
    }*/

    public void validateSearchProduct() {
        if ("valid".equals(validationStatus)) {
            response.then()
                    .body("code", equalTo(this.productCode));
            assertResponseStatusCode(this.response, "200");
        }else{
            response.then()
                    .body("code", equalTo("NOT_FOUND"));
            assertResponseStatusCode(this.response, "404");
        }
    }

    public void searchInvalidField(String field, String value){
        String url = field + value;
        System.out.println("Searching products: ");
        this.response = this.platformSek.getProductsListWithParams(url);
        assertResponseStatusCode(this.response,"200");
        this.content = response.path("content");
    }

    public void resultProductsWithInvalidField(){
        if (content == null || content.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("Product found.");
        }
        step("Body: " + response.getBody().asString());
    }

    public void updateOriginProduct(String validationStatus) {
        String status = validationStatus.toLowerCase();
        System.out.println("Updating product based on validation status: " + status);
        switch (status) {
            case "valid":
                System.out.println("Generating product... ");
                generateFakerProduct("without");
                sendCreateProductRequest();

                //traz o codigo do produto antes de atualizar
                this.productCode = extractCreatedProductCode();

                System.out.println("Update with valid code: " + this.productCode);
                this.response = this.platformSek.putProduct(this.productCode, this.products.generateUpdateRequestWithFaker());
                //traz o codigo do produto depois de atualizar
                this.productCode = getNewProductCode(this.response);
                this.productName = getNewProductName(this.response);

                break;

            case "nonexistent":
                System.out.println("Update with " + status + " code: " + invalidCode);
                this.response = this.platformSek.putProduct(invalidCode, this.products.generateUpdateRequestWithFaker());
//                this.productCode = getNewProductCode(this.response);
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
        }
    }

    public void searchUpdatedStatusProduct(String statusUpdate) {
        String status = statusUpdate.toLowerCase();
        switch (status) {
            case "updated":
                System.out.println("Search " + status + " product with the product code: " + this.productCode);
                this.response = this.platformSek.getProductWithCode(this.productCode);
                assertResponseStatusCode(this.response, "200");
                break;

            case "notupdated":
                assertResponseStatusCode(this.response, "400");

                break;

            case "invalidproductupdated":
                this.productCode = getCreatedProductCode();
                System.out.println("Search " + status + " product with the product code: " + this.invalidCode);
                this.response = this.platformSek.getProductWithCode(this.invalidCode);

                assertResponseStatusCode(this.response, "404");

                break;

            default:
                System.out.println("Invalid value of statusUpdate value in case of: " + statusUpdate);
        }
    }

    public void updateProductWithoutField(String field) {
        if (VALID_FIELDS.contains(field.toLowerCase())) {

            System.out.println("Generating product: ");
            generateFakerProduct("without");
            sendCreateProductRequest();

            this.productCode = extractCreatedProductCode();

            System.out.println("Update with valid code: " + this.productCode);
            System.out.println("Updating with field " + field + " hidden.");
            Product product = products.generateUpdateFieldToOmit(field);

            this.response = this.platformSek.putProduct(this.productCode, product);

        } else {
            throw new IllegalArgumentException("Invalid field: " + field);
        }
    }

    // region Deleted Methods
    public void deleteProduct(String validationStatus) {
        switch (validationStatus.toLowerCase()) {
            case "valid":
                System.out.println("Deleting product... ");

                this.productCode = getCreatedProductCode();

                System.out.println("Delete with valid code: " + this.productCode);
                this.response = this.platformSek.deleteProduct(this.productCode);
                assertResponseStatusCode(this.response, "204");
                break;

            case "nonexistent":
                System.out.println("Delete with no existent code: " + invalidCode);
                this.response = this.platformSek.deleteProduct(invalidCode);
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
                break;
        }
    }

    public void deleteNewProduct(String validationStatus) {
        switch (validationStatus.toLowerCase()) {
            case "valid":
                System.out.println("Deleting product... ");

                this.productCode = getNewProductCode(this.response);

                System.out.println("Delete with valid code: " + this.productCode);
                this.response = this.platformSek.deleteProduct(this.productCode);
                assertResponseStatusCode(this.response, "204");
                break;

            case "nonexistent":
                System.out.println("Delete with no existent code: " + invalidCode);
                this.response = this.platformSek.deleteProduct(invalidCode);
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
                break;
        }
    }

    public void deleteCreatedProduct(String validationStatus) {
        switch (validationStatus) {
            case "valid":
                System.out.println("Generating product: ");
                generateFakerProduct("without");
                sendCreateProductRequest();

                this.productCode = extractCreatedProductCode();
                System.out.println("Delete product with valid Code: " + this.productCode);
                this.response = this.platformSek.deleteProduct(this.productCode);
                assertResponseStatusCode(this.response, "204");
                break;

            case "nonexistent":
                System.out.println("Delete with no existent Code: " + inexistentCode);
                this.response = this.platformSek.deleteProduct(inexistentCode);
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
                break;
        }
    }

    public void searchDeletedProduct(String statusDelete) {
        switch (statusDelete) {
            case "deleted":
                System.out.println("Search delete product with the Code: " + productCode);
                this.response = this.platformSek.getProductWithCode(this.productCode);
                assertResponseStatusCode(this.response, "404");
                break;

            case "notDeleted":
                System.out.println("Search delete product with the invalid Code: " + invalidCode);
                this.response = this.platformSek.getProductWithCode(invalidCode);
                assertResponseStatusCode(this.response, "404");
                break;

            default:
                System.out.println(Messages.errorMessages.invalidProductStatus);
                return;
        }

        if (this.response.statusCode() != 404) {
            throw new AssertionError(String.format(ExceptionsMessages.fromCode(404).getMessage(), response.statusCode()));
        } else {
            System.out.println("This status code " + response.statusCode() + " is correct.");
        }
    }
    // endregion Deleted Methods

    // region Return Products Key
    private void extractProductCode() {
        this.productCode = this.response.path("code");
    }

    public String extractCreatedProductCode() {
        this.productCode = this.response.path("code");
        return this.productCode;
    }

    // validar esse em portfolio
    public String getCreatedProductCode() {
        this.product = this.products.getProducts("without");
        this.productCode = this.product.getCode();
        step("code: " + this.productCode);
        return this.productCode;
    }

    public String getCreatedProductName() {
        this.product = this.products.getProducts("without");
        this.productName = this.product.getName();
        step("name: " + this.productName);
        return this.productName;
    }

    public String getNewProductCode(Response response) {
        this.productCode = returnJsonAttributeValue(response, "code".toLowerCase());
        return this.productCode;
    }

    public String getNewProductName(Response response) {
        this.productName = returnJsonAttributeValue(response, "name".toLowerCase());
        return this.productName;
    }
    // endregion Return Product Key

    // region Asserts
    public void validateProductValues(Response response) {

        ProductResponse objResponse = response.as(ProductResponse.class);
        String originalCodeProduct = extractCreatedProductCode();
        String originalNameProduct = getCreatedProductName();
        assertValues(originalCodeProduct, objResponse.getCode());
        assertValues(originalNameProduct, objResponse.getName());

        step("All fields were successfully validated");
    }
    // endregion Asserts

// endregion Products Methods
}
