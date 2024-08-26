package br.com.sek.utils.mass;

import br.com.sek.models.messages.Messages;
import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import io.restassured.response.Response;
import lombok.Setter;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasItem;

public class Products {
    @Setter
    private Response response;
    private Faker faker;
    private Product product;

    public Products() {
        this.faker = new Faker(new Locale("pt-BR"));
        this.product = generateFullProduct();
    }

    private Product generateFullProduct() {
        String name = faker.name().fullName();
        String code = faker.code().isbn10();
        String description = faker.lorem().paragraph(3);
        List<Addon> addons = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Addon addon = Addon.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence(5))
                    .build();
            addons.add(addon);
        }
        return Product.builder()
                .name(name)
                .code(code)
                .description(description)
                .addons(addons)
                .build();
    }

    public Product generateUpdateRequestWithFaker() {
        String name = faker.name().fullName();
        String code = faker.code().isbn10();
        String description = faker.lorem().paragraph(3);
        List<Addon> addons = new ArrayList<>();

        return Product.builder()
                .name("PUT " + name)
                .code(code)
                .description("PUT " + description)
                .addons(addons)
                .build();
    }

    public Product getProducts(String addonType){
        if ("with".equals(addonType)) {
            return Product.builder()
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .addons(product.getAddons())
                    .build();
        }else if ("without".equals(addonType)) {
            return Product.builder()
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .addons(new ArrayList<Addon>())
                    .build();
        }else {
            throw new IllegalArgumentException("Invalid addon type: " + addonType);
        }
    }

    public Product getProductsWithoutField(String field){
        switch (field) {
            case "name":
                return Product.builder()
                        .code(product.getCode())
                        .description(product.getDescription())
                        .addons(product.getAddons())
                        .build();
            case "code":
                return Product.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .addons(product.getAddons())
                        .build();
            case "description":
                return Product.builder()
                        .name(product.getName())
                        .code(product.getCode())
                        .addons(product.getAddons())
                        .build();
            case "addons":
                return Product.builder()
                        .name(product.getName())
                        .code(product.getCode())
                        .description(product.getDescription())
                        .build();
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    public Product getProductsWithEmptyField(String field){
        switch (field) {
            case "name":
                return Product.builder()
                        .name("")
                        .code(product.getCode())
                        .description(product.getDescription())
                        .addons(product.getAddons())
                        .build();
            case "code":
                return Product.builder()
                        .name(product.getName())
                        .code("")
                        .description(product.getDescription())
                        .addons(product.getAddons())
                        .build();
            default:
                throw new IllegalArgumentException(Messages.errorMessages.uknownField + field);
        }
    }

    public void verifyErrorFieldsDisplayed(String field){
        if (response == null) {
            throw new IllegalStateException(Messages.errorMessages.responseNotSet);
        }

        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != 400) {
            throw new AssertionError(Messages.errorMessages.expected400 + actualStatusCode);
        }

        String actualCategory = response.jsonPath().getString("category");
        if (!"BAD_REQUEST".equals(actualCategory)) {
            throw new AssertionError(Messages.errorMessages.expectedBadRequest + actualCategory + "'");
        }

        // Verifica se o campo esperado está presente na lista de campos de erro
        response.then().body("fields", hasItem(field));
    }

    public Product generateUpdateFieldToOmit(String fieldToOmit) {
        String name = faker.name().fullName();
        String code = faker.code().isbn10();
        String description = faker.lorem().paragraph(3);
        List<Addon> addons = new ArrayList<>();

        Product.ProductBuilder builder = Product.builder();

        if (!"name".equalsIgnoreCase(fieldToOmit)) {
            builder.name("PUT " + name);
        }
        if (!"code".equalsIgnoreCase(fieldToOmit)) {
            builder.code(code);
        }
        if (!"description".equalsIgnoreCase(fieldToOmit)) {
            builder.description("PUT " + description);
        }
        if (!"addons".equalsIgnoreCase(fieldToOmit)) {
            builder.addons(addons);
        }

        return builder.build();
    }

}
