package br.com.sek.utils.mass;

import br.com.sek.models.exceptions.Messages;
import br.com.sek.models.request.organization.*;
import br.com.sek.models.request.organization.Products;
import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;

import java.util.*;

public class Organizations {
    @Setter
    private Faker faker;
    // Retorna a instância existente retirement
    @Getter
    private Organization organization;

    public Organizations(){
        this.faker = new Faker(new Locale("pt-BR"));
        this.organization = generateOrganization();
    }

    private Organization generateOrganization() {
        String name = faker.name().fullName().replaceAll("\\.","").replaceAll("[^a-zA-Z]", "");
        Integer industry = generateIndustryNumber();
        String countryCode = getRandomCountryCode();
//        String code = generateOrganizationCode();
        List<Products> products = new ArrayList<>();
        List<Bindings> bindings = new ArrayList<>();
        List<AcceptDomains> acceptDomains = new ArrayList<>();

        return Organization.builder()
                .name(name)
                .industry(industry)
//                .code(code)
                .countryCode(countryCode)
                .products(products)
                .bindings(bindings)
                .acceptDomains(acceptDomains)
                .build();
    }

    private Organization createOrganizationWithSuffix(String suffix) {
        String countryCode = getRandomCountryCode();
//        String code = generateOrganizationCode();
        Integer industry = generateIndustryNumber();
        String baseName = this.organization.getName(); // Obtém o nome original da primeira organização

        return Organization.builder()
                .name(baseName + " - " + suffix)
                .industry(industry)
//                .code(code)
                .countryCode(countryCode)
                .products(this.organization.getProducts())
                .bindings(this.organization.getBindings())
                .acceptDomains(this.organization.getAcceptDomains())
                .build();
    }

    public Organization generateOrganizationWithoutParameter(String parameter) {
        Organization.OrganizationBuilder builder = Organization.builder()
                .name(organization.getName())
                .industry(organization.getIndustry())
//                .code(organization.getCode())
                .countryCode(organization.getCountryCode())
                .acceptDomains(organization.getAcceptDomains());

        switch (parameter) {
            case "name":
                builder.name(null);
                break;
            case "industry":
                builder.industry(null);
                break;
/*            case "code":
                builder.code(null);
                break;*/
            case "countryCode":
                builder.countryCode(null);
                break;
            case "acceptDomains":
                builder.acceptDomains(null);
                break;
            default:
                throw new IllegalArgumentException("Unknown parameter: " + parameter);
        }

        return builder.build();
    }

/*    public Organization generateOrganizationWithExistentCode(){
        String name = faker.name().fullName().replaceAll("\\.","").replaceAll("[^a-zA-Z]", "");
        Integer industry = generateIndustryNumber();
        List<Products> products = new ArrayList<>();
        List<Bindings> bindings = new ArrayList<>();
        List<AcceptDomains> acceptDomains = new ArrayList<>();

        return Organization.builder()
                .name(name)
                .industry(industry)
                .code(organization.getCode())
                .countryCode(organization.getCountryCode())
                .products(products)
                .bindings(bindings)
                .acceptDomains(acceptDomains)
                .build();
    }*/

    public Organization generateSecondOrganization() {
        return createOrganizationWithSuffix("Second Organization");
    }

    public Organization generateThirdOrganization() {
        return createOrganizationWithSuffix("Third Organization");
    }

    private String getRandomCountryCode() {
        Set<String> countryCodesSet  = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);
        String[] countryCodes = countryCodesSet.toArray(new String[0]);
        Random random = new Random();
        return countryCodes[random.nextInt(countryCodes.length)];
    }
/*
    private String generateOrganizationCode() {
        return faker.code().getFaker().letterify("????").toUpperCase();
    }*/

    private String generateName(){
        return faker.name().fullName().replaceAll("[^\\p{ASCII}]", "").replaceAll("\\.", "");
    }

    private Integer generateIndustryNumber() {
        return faker.number().numberBetween(1, 19);
    }

    public Organization generateUpdateRequestWithFaker() {
        String name = generateName();
        Integer industry = generateIndustryNumber();

        return Organization.builder()
                .name("PATCH " + name)
                .industry(industry)
                .countryCode(organization.getCountryCode())
                .acceptDomains(organization.getAcceptDomains())
                .build();
    }

    public Organization getOrganizationsWithEmptyField(String field) {
        Organization.OrganizationBuilder builder = Organization.builder()
                .name(organization.getName())
                .industry(organization.getIndustry())
//                .code(organization.getCode())
                .countryCode(organization.getCountryCode())
                .acceptDomains(organization.getAcceptDomains());


        switch (field) {
            case "name":
                builder.name("");
                break;
/*            case "code":
                builder.code("");
                break;*/
            case "countryCode":
                builder.countryCode("");
                break;
            case "acceptDomains":
                builder.acceptDomains(Collections.emptyList());
                break;
            default:
                throw new IllegalArgumentException(Messages.errorMessages.unknownField + field);
        }

        return builder.build();
    }

    public Organization generateUpdateFieldToOmit(String fieldToOmit) {
        String name = generateName();
        Integer industry = generateIndustryNumber();

        Organization.OrganizationBuilder builder = Organization.builder();
        if (!"name".equalsIgnoreCase(fieldToOmit)) {
            builder.name("PATCH " + name);
        }
        if (!"industry".equalsIgnoreCase(fieldToOmit)) {
            builder.industry(industry);
        }
        if (!"acceptDomains".equalsIgnoreCase(fieldToOmit)) {
            builder.acceptDomains(this.organization.getAcceptDomains());
        }

        return builder.build();
    }

}
