package br.com.sek.testContext;

import br.com.sek.models.messages.Messages;
import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.Products;
import br.com.sek.utils.mass.SignUp;
import io.restassured.response.Response;
import lombok.Getter;
import net.datafaker.Faker;

import java.util.*;

import static io.qameta.allure.Allure.step;

public class ProductTestContext {
    private final PlatformSek platformSek;
    private final Products products;
    @Getter
    private Response response;
    private Product product;
    private String productId;
    private String validationStatus;
    private Integer storedId;
    private Integer invalidId;
    private List<Map<String, Object>> content;
    private static final Set<String> VALID_FIELDS = new HashSet<>(Set.of(
            "name", "code", "description", "addons"
    ));

    public ProductTestContext(PlatformSek platformSek, Products products) {
        this.platformSek = platformSek;
        this.products = products;
    }

    public void sendRequest() {
        if (this.product == null) {
            System.out.println("Product is null. Cannot send request.");
            return;
        }
        this.response = this.platformSek.executeProduct(this.product);
        extractProductId(); // Extrair e armazenar o ID após enviar a solicitação
    }

    private void extractProductId() {
        Integer id = this.response.path("id");
        this.productId = id != null ? id.toString() : null;
    }

    public void getProduct() {
        if (this.productId != null) {
            System.out.println("GET product with ID after register: ");
            this.response = this.platformSek.getProduct(this.productId);
        } else {
            throw new IllegalStateException("Product ID is not set. Please call sendRequest() first.");
        }
    }

    public void generateProduct(String addonType){
        this.product = this.products.getProducts(addonType);
        step("Body: " + this.product);
    }

    public void generateProductWithoutField(String field) {
        this.product = this.products.getProductsWithoutField(field);
        step("Body: " + this.product);
    }

    public void generateProductWithEmptyField(String field) {
        this.product = this.products.getProductsWithEmptyField(field);
        step("Body: " + this.product);
    }

    public void verifyErrorFieldsDisplayed(String field) {
        products.setResponse(response);
        this.products.verifyErrorFieldsDisplayed(field);
        step("Body: " + response.getBody().asString());
    }

    public void searchProductsList(){
        this.response = this.platformSek.searchProductsList();
    }

    public void resultProductsList(){
        List<Map<String, Object>> content = response.path("content");
        if(content != null && !content.isEmpty()){
            System.out.println("List of registered products: ");
            searchProductsList();
        }else{
            System.out.println("There are no registered products.");
        }
    }

    public void searchProducts(String validationStatus){
        this.invalidId = 999;
        this.validationStatus = validationStatus;

        this.response = this.platformSek.searchProductsList();
        List<Map<String, Object>> content = response.path("content");

        if ("valid".equals(validationStatus) && content != null && !content.isEmpty()) {
            this.storedId = (Integer) content.get(0).get("id");
            System.out.println("Stored ID: " + this.storedId);
        } else if("invalid".equals(validationStatus)){
            System.out.println("Invalid ID: " + this.invalidId);
        } else {
            System.out.println("No products found");
        }
    }

    public void searchProductById(){
        if ("valid".equals(validationStatus) && this.storedId != null) {
            System.out.println("Search with stored ID: " + this.storedId);
            this.response = this.platformSek.getProductWithId(this.storedId);
        } else if ("invalid".equals(validationStatus)) {
            System.out.println("Search with invalid ID: " + this.invalidId);
            this.response = this.platformSek.getProductWithId(this.invalidId);
        } else {
            System.out.println(Messages.errorMessages.invalidProductStatus);
            return;
        }
    }

    public void searchInvalidField(String field, String value){
        String url = field + value;
        System.out.println("Searching products: ");
        this.response = this.platformSek.searchProductsListWithParams(url);
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

    public void updateProduct(String validationStatus){
        if ("valid".equals(validationStatus)) {
            System.out.println("Generating product: ");
            generateProduct("without");
            sendRequest();

            Map<String, Object> content = response.jsonPath().getMap("$");
            this.storedId = (Integer) content.get("id");

            System.out.println("Update with valid ID: " + this.storedId);
            this.response = this.platformSek.putProduct(this.storedId, this.products.generateUpdateRequestWithFaker());
        } else if ("nonexistent".equals(validationStatus)) {
            this.invalidId = 999;
            System.out.println("Update with no existent ID: " + this.invalidId);
            this.response = this.platformSek.putProduct(this.invalidId, this.products.generateUpdateRequestWithFaker());
        } else {
            System.out.println(Messages.errorMessages.invalidProductStatus);
            return;
        }
    }

    public void searchUpdateProduct(){
        System.out.println("Product Updated: ");
        this.response = this.platformSek.getProductWithId(this.storedId);
    }

    public void updateProductWithoutField(String field) {
        if (VALID_FIELDS.contains(field.toLowerCase())) {

            System.out.println("Generating product: ");
            generateProduct("without");
            sendRequest();

            Map<String, Object> content = response.jsonPath().getMap("$");
            this.storedId = (Integer) content.get("id");

            System.out.println("Update with valid ID: " + this.storedId);
            System.out.println("Updating with Field " + field + " hidden.");
            Product product = products.generateUpdateFieldToOmit(field);

            this.response = this.platformSek.putProduct(this.storedId, product);
        } else {
            throw new IllegalArgumentException("Invalid field: " + field);
        }
    }

    public void deleteProduct(String validationStatus){
        if ("valid".equals(validationStatus)) {
            System.out.println("Generating product: ");
            generateProduct("without");
            sendRequest();

            Map<String, Object> content = response.jsonPath().getMap("$");
            this.storedId = (Integer) content.get("id");

            System.out.println("Delete with valid ID: " + this.storedId);
            this.response = this.platformSek.deleteProduct(this.storedId);
        } else if ("nonexistent".equals(validationStatus)) {
            this.invalidId = 999;
            System.out.println("Delete with no existent ID: " + this.invalidId);
            this.response = this.platformSek.deleteProduct(this.invalidId);
        } else {
            System.out.println(Messages.errorMessages.invalidProductStatus);
        }
    }

    public void searchDeleteProduct(String statusDelete){
        if ("deleted".equals(statusDelete)){
            System.out.println("Search delete product with the ID: " + storedId);
            this.response = this.platformSek.getProductWithId(this.storedId);
        }else if ("notDeleted".equals(statusDelete)){
            System.out.println("Search delete product with the invalid ID: " + invalidId);
            this.response = this.platformSek.getProductWithId(this.invalidId);
        }

        if (this.response.statusCode() != 404){
            System.out.println(Messages.errorMessages.expected404 + response.statusCode());
        }else{
            System.out.println("This status code " + response.statusCode() + " is correct.");
        }

    }

}
