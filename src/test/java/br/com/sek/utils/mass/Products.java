package br.com.sek.utils.mass;

import br.com.sek.models.exceptions.Messages;
import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import io.restassured.response.Response;
import lombok.Setter;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
        //String name = faker.name().fullName().replaceAll("\\.","");
        String name = faker.name().fullName().replaceAll("[^\\p{ASCII}]", "").replaceAll("\\.", "");
        String code = faker.code().isbn10().concat("X");
        String description = faker.lorem().paragraph(1);
        List<Addon> addons = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Addon addon = Addon.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence(1))
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
        String name = faker.name().fullName().replaceAll("[^\\p{ASCII}]", "").replaceAll("\\.", "");
        String code = faker.code().isbn10().concat("X");
        String description = faker.lorem().paragraph(1);
        List<Addon> addons = new ArrayList<>();

        return Product.builder()
                .name("PUT " + name)
                .code(code)
                .description("PUT " + description)
                .addons(addons)
                .build();
    }

/*    public Product getProducts(String addonType){
        if ("with".equals(addonType)) {
            return Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .addons(product.getAddons())
                    .build();
        }else if ("without".equals(addonType)) {
            return Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .addons(new ArrayList<Addon>())
                    .build();
        }else {
            throw new IllegalArgumentException("Invalid addon type: " + addonType);
        }
    }*/

    public Product getProducts(String addonType){
        return "with".equals(addonType) ? product :
            Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .addons(Collections.emptyList())
                    .build();
    }

/*    public Product getAddons(String addonType){
        return "with".equals(addonType) ? product :
                Product.builder()
                        .addons(Collections.emptyList())
                        .build();
    }*/

    public List<Addon> getAddons(String addonType) {
        // Se o tipo for 'with', retorna a lista de addons existentes
        if ("with".equals(addonType)) {
            return product.getAddons().stream()
                    .map(addon -> Addon.builder()
                            .name(addon.getName())
                            .description(addon.getDescription())
                            .build())
                    .collect(Collectors.toList());
        }

        // Caso contrário, retorna uma lista vazia
        return Collections.emptyList();
    }

    public Product getProductsWithoutField(String field) {
        Product.ProductBuilder builder = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .description(product.getDescription())
                .addons(product.getAddons());

        switch (field) {
            case "name":
                builder.name(null);
                break;
            case "code":
                builder.code(null);
                break;
            case "description":
                builder.description(null);
                break;
            case "addons":
                builder.addons(null);
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }

        return builder.build();
    }


    public Product getProductsWithEmptyField(String field) {
        Product.ProductBuilder builder = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .description(product.getDescription())
                .addons(product.getAddons());

        switch (field) {
            case "name":
                builder.name("");
                break;
            case "code":
                builder.code("");
                break;
            default:
                throw new IllegalArgumentException(Messages.errorMessages.unknownField + field);
        }

        return builder.build();
    }


    public Product generateUpdateFieldToOmit(String fieldToOmit) {
        String name = faker.name().fullName().replaceAll("[^\\p{ASCII}]", "").replaceAll("\\.", "");
        String code = faker.code().isbn10().concat("X");
        String description = faker.lorem().paragraph(1);
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
