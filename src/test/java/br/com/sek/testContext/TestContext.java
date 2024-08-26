package br.com.sek.testContext;

import br.com.sek.models.request.*;
import br.com.sek.models.response.Token;
import br.com.sek.request.Auth0;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.SignUp;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.function.Consumer;

import static io.qameta.allure.Allure.step;

public class TestContext {
    private final PlatformSek platformSek;
    private final SignUp signUp;
    private final Auth0 auth0;
    private static Token token;
    @Getter
    private Response response;
    @Getter
    private User user;

    public TestContext(PlatformSek platformSek, Auth0 auth0, SignUp signUp) {
        this.platformSek = platformSek;
        this.auth0 = auth0;
        this.signUp = signUp;
    }

    public void GenerateToken() {
        if(token == null)
            token = this.auth0.OAuthToken().as(Token.class);

        this.platformSek.setToken(token.getId_token());
    }

    public void ListActions() {
        this.response = this.platformSek.executeGetAdminActions("");
    }

    public void ListActionsByCredential(String credential) {
        this.response = this.platformSek.executeGetAdminActions(credential);
    }

    public void GenerateSignUp() {
        this.user = this.signUp.generateSignUp();
        step("Body: " + this.user);
    }

    public void sendSingUp() {
        this.response = this.platformSek.executeSignUp(this.user);
    }

    public void removeFieldByUser(String field) {
        Map<String, Consumer<User>> fieldRemovers = Map.ofEntries(
                Map.entry("name", user -> user.setName(null)),
                Map.entry("email", user -> user.setEmail(null)),
                Map.entry("phone", user -> user.setPhone(null)),
                Map.entry("phoneFilled", user -> user.setPhone(Phone.builder()
                        .countryCode(null)
                        .areaCode(null)
                        .phoneNumber(null)
                        .build())),
                Map.entry("phoneCountryCode", user -> user.getPhone().setCountryCode(null)),
                Map.entry("products", user -> user.setProducts(null)),
                Map.entry("products2", user -> user.getProducts().get(0).setId(null)),
                Map.entry("products3", user -> user.getProducts().get(0).setFeatures(null)),
                Map.entry("company", user -> user.setCompany(null)),
                Map.entry("roles", user -> user.setRoles(null)),
                Map.entry("roles.name", user -> user.getRoles().get(0).setName(null)),
                Map.entry("title", user -> user.setTitle(null)),
                Map.entry("settings", user -> user.setSettings(null))
        );

        Consumer<User> remover = fieldRemovers.get(field);
        if (remover != null) {
            remover.accept(this.user);
        } else {
            throw new NotImplementedException();
        }
        step("After removed field: ".concat(this.user.toString()));
    }

    public void setFieldBlank(String field) {
        Map<String, Consumer<User>> fieldRemovers = Map.ofEntries(
                Map.entry("name", user -> user.setName("")),
                Map.entry("email", user -> user.setEmail("")),
                Map.entry("phone", user -> user.setPhone(Phone.builder()
                        .countryCode("")
                        .areaCode("")
                        .phoneNumber("")
                        .build())),
                Map.entry("company", user -> user.setCompany("")),
                Map.entry("title", user -> user.setTitle(""))
        );

        Consumer<User> remover = fieldRemovers.get(field);
        if (remover != null) {
            remover.accept(this.user);
        } else {
            throw new NotImplementedException();
        }
        step("After removed field: ".concat(this.user.toString()));
    }
}
