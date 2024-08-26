package br.com.sek.request;

import br.com.sek.models.request.User;
import br.com.sek.models.request.organization.Organization;
import br.com.sek.models.request.product.Product;
import br.com.sek.utils.mass.Products;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PlatformSek extends RequestBase {

    public PlatformSek() {
        this.url = System.getenv("PSEK_URL_BASE");
        this.request = given().baseUri(url);
        request.log().all().toString();
    }

    public void setToken(String token){
        this.request.header("Authorization","Bearer ".concat(token));
    }

    public Response executeGetAdminActions(String credential) {
        return this.executeGet(credential.concat("/admin/actions"));
    }

    public Response executeSignUp(User user) {
        return this.executePost("/admin/signup", user);
    }

    public Response executeProduct(Product product) {
        return this.executePost("/products", product);
    }

    public Response executeOrganization(Organization organization){
        return this.executePost("/organizations", organization);
    }

    public Response getProduct(String productId) {
        return this.executeGet("/products/" + productId);
    }

    public Response searchProductsList(){
        return this.executeGet("/products");
    }

    public Response searchProductsListWithParams(String params){
        return this.executeGet("/products" + params);
    }

    public Response getProductWithId(Integer storeId){
        return this.executeGet("/products/" + storeId);
    }

    public Response putProduct(Integer storeId, Object body){
        String url = "/products/" + storeId;
        return this.executePut(url, body);
    }

    public Response deleteProduct(Integer storeId){
        String url = "/products/" + storeId;
        return this.executeDelete(url);
    }

}
