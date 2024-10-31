package br.com.sek.utils.mass;

import br.com.sek.models.request.*;
import br.com.sek.models.request.product.Product;
import br.com.sek.models.response.SignUpResponse;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static io.qameta.allure.Allure.step;

public class SignUp {

    public User generateSignUp() {
        Faker faker = new Faker(new Locale("pt-BR"));
        Phone phone = createPhone(faker);
        List<Action> actions = createActions();
        List<Policy> policies = createPolicies(actions);
        List<Role> roles = createRoles(policies);
        List<Product> products = createProducts();
        return createUser(faker, phone, roles, products);
    }

    private Phone createPhone(Faker faker) {
        String phoneNumber = faker.phoneNumber().phoneNumber();
        return Phone.builder()
                .countryCode("55")
                .areaCode(phoneNumber.substring(1, 3))
                .phoneNumber(phoneNumber.substring(5).replace("-",""))
                .build();
    }

    private List<Action> createActions() {
        List<String> types = List.of("Create", "List", "Update", "Delete", "ChangeMfaRequired", "AttachProducts", "AttachBindings");

        Random rand = new Random();
        List<String> randomTypes = types.subList(0, rand.nextInt(types.size()));
        List<Action> actions = new ArrayList<>();

        for (String randomType : randomTypes) {
            actions.add(Action.builder()
                    .scope("organization")
                    .type(randomType)
                    .build());
        }

        return actions;
    }

    private List<Policy> createPolicies(List<Action> actions) {
        return List.of(Policy.builder()
                .sid("1234")
                .actions(actions)
                .resources(List.of("string"))
                .build());
    }

    private List<Role> createRoles(List<Policy> policies) {
        return List.of(Role.builder()
                .name("string")
                .policies(policies)
                .build());
    }

    private List<Product> createProducts() {
        return List.of(Product.builder()
                .id("string")
                .features(List.of("string"))
                .build());
    }

    private User createUser(Faker faker, Phone phone, List<Role> roles, List<Product> products) {
        return User.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .phone(phone)
                .company(faker.company().name())
                .products(products)
                .roles(roles)
                .title(faker.book().title())
                .settings(Settings.builder()
                        .locale("string")
                        .timezone("string")
                        .build())
                .build();
    }

    public void validateSignUp(Response response, User user){
        Assertions.assertEquals(201, response.statusCode(), "Status Code");

        SignUpResponse objResponse = response.as(SignUpResponse.class);

        Assertions.assertEquals(user.getName(), objResponse.getName(), "Name");
        Assertions.assertEquals(user.getEmail(), objResponse.getEmail(), "E-mail");
        Assertions.assertEquals(user.getCompany(), objResponse.getCompany(), "Company");
        Assertions.assertEquals(user.getPhone().toString(), objResponse.getPhone(), "Phone");
        step("All fields were successfully validated");
    }
}
