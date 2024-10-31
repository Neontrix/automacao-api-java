package br.com.sek.request;

import br.com.sek.helpers.RequestBase;
import br.com.sek.models.request.User;
import br.com.sek.models.request.organization.Organization;
import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PlatformSek extends RequestBase {


    private static final String URL_BASE = "PSEK_URL_BASE";
    private static final String AdminEndpointBase = "/admin";
    private static final String OrganizationEndpointBase = "/organizations";
    private static final String ProductEndpointBase = "/products";
    private static final String Bindings = "/bindings/";
    private static final String PortfolioEndpointBase = "/portfolio";

    public PlatformSek() {
        this.url = System.getenv(URL_BASE);
        this.request = given().baseUri(url);
        request.log().all().toString();
    }

    public void setHeaders(String token, String organizationCode){
        this.request.header("Authorization","Bearer ".concat(token));
        this.request.header("X-ORGANIZATION-CODE", organizationCode);
    }

    public void setToken(String token){
        this.request.header("Authorization","Bearer ".concat(token));
        this.request.header("X-ORGANIZATION-CODE", "SEK-BR");
    }

    public Response executeGetAdminActions(String credential) {
        return this.executeGet(credential.concat(AdminEndpointBase + "/actions"));
    }

    public Response executeSignUp(User user) {
        return this.executePost(AdminEndpointBase + "/signup", user);
    }

    // region api methods Products

    public Response postProduct(Product product) {
        return this.executePost(ProductEndpointBase, product);
    }

    public Response getProductResponse(String code) {
        return this.executeGet(ProductEndpointBase + "/" + code);
    }

    public Response getProductsList(){
        return this.executeGet(ProductEndpointBase);
    }

    public Response getProductsListWithParams(String params){
        return this.executeGet(ProductEndpointBase + params);
    }

    public Response getProductWithCode(String code){
        return this.executeGet(ProductEndpointBase + "/" + code);
    }

    public Response putProduct(String code, Object body){
        String url = ProductEndpointBase + "/" + code;
        return this.executePut(url, body);
    }

    public Response deleteProduct(String code){
        String url = ProductEndpointBase + "/" + code;
        return this.executeDelete(url);
    }
    // endregion

    //region Organization
    public Response postOrganization(Organization organization){
        return this.executePost(OrganizationEndpointBase, organization);
    }

    public Response postAssociateOrganizations(String firstOrganizationCode, String secondOrganizationCode){
        String url = OrganizationEndpointBase + "/" + firstOrganizationCode + Bindings + secondOrganizationCode;
        return this.executePost(url, null);
    }

    public Response getOrganization(String organizationCode) {
        return this.executeGet(OrganizationEndpointBase + "/" + organizationCode);
    }

    public Response getOrganizationsList(){
        return this.executeGet(OrganizationEndpointBase);
    }

    public Response getOrganizationsListWithParams(String params){
        return this.executeGet(OrganizationEndpointBase + params);
    }

    public Response patchOrganization(String code, Object body){
        String url = OrganizationEndpointBase + "/" + code;
        return this.executePatch(url, body);
    }

    public Response deactivateOrganization(String organizationCode){
        String url = OrganizationEndpointBase + "/" + organizationCode;
        return this.executeDelete(url);
    }

    // Endpoint de delete desativado
/*    public Response removeOrganizations(){
        String url = OrganizationEndpointBase + "/remove";
        return this.executeDelete(url);
    }*/

    public Response deleteDisassociateOrganizations(String firstOrganizationCode, String secondOrganizationCode){
        String url = OrganizationEndpointBase + "/" + firstOrganizationCode + Bindings + secondOrganizationCode;
        return this.executeDelete(url);
    }
    //endregion

    //region Portfolio
    public Response postPortfolio(String productCode, List<Addon> addons) {
        return this.executePost(PortfolioEndpointBase + "/products/" + productCode, addons);
    }
    //endregion Portfolio

  /*
    public Response executeProductRequest(HttpMethod method, Product productCode) {
        switch (method) {
            case GET:
                return executeGet(ProductEndpointBase);
            case POST:
                return executePost(ProductEndpointBase, productCode);
            case PUT:
                return executePut(ProductEndpointBase, productCode);
            case DELETE:
                return executeDelete(ProductEndpointBase);
            default:
                System.out.println("Invalid HttpMethod endpoint");
                return null;
        }
    }*/
}
