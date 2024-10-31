package br.com.sek.request;

import br.com.sek.helpers.RequestBase;
import br.com.sek.models.request.ClientDetails;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Auth0 extends RequestBase {

    public Auth0() {
        this.url = System.getenv("AUTH0_URL_BASE");
        this.request = given().baseUri(url);
    }

    public Response OAuthToken(){
        return this.executePost("oauth/token", ClientDetails.builder()
                .client_id(System.getenv("AUTH_CLIENT_ID"))
                .client_secret(System.getenv("AUTH_CLIENT_SECRET"))
                .username(System.getenv("AUTH_USERNAME"))
                .password(System.getenv("AUTH_PASSWORD"))
                .grant_type(System.getenv("AUTH_GRAND_TYPE"))
                .realm(System.getenv("REALM"))
                .build());
    }

}
