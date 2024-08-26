package br.com.sek.utils.mass;

import br.com.sek.models.request.organization.*;
import br.com.sek.models.request.organization.Products;
import br.com.sek.models.request.product.Product;
import io.restassured.response.Response;
import lombok.Setter;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Organizations {
    @Setter
    private Response response;
    private Faker faker;
    private Organization organization;

    public Organizations(){
        this.faker = new Faker(new Locale("pt-BR"));
        this.organization = generateOrganizationWithMember();
    }

    private Organization generateOrganizationWithMember(){
        String name = this.faker.name().fullName();
        String code = this.faker.code().isbn10();
        Boolean mfaRequired = this.faker.bool().bool();;
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            // Construindo o objeto MemberDetails com o nome gerado pela Faker
            MemberDetails memberDetails = MemberDetails.builder()
                    .name(faker.name().fullName())
                    .build();

            Members member = Members.builder()
                    .createdTime(faker.date().past(10, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .updatedTime(faker.date().past(5, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .activatedTime(faker.date().past(3, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .deactivatedTime(faker.date().past(1, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                    .memberDetails(memberDetails)
                    .build();
            members.add(member);
        }
        List<Products> products = new ArrayList<>();
        List<Bindings> bindings = new ArrayList<>();
        List<AcceptDomains> acceptDomains = new ArrayList<>();

        return Organization.builder()
                .name(name)
                .code(code)
                .mfaRequired(mfaRequired)
                .members(members)
                .products(products)
                .bindings(bindings)
                .acceptDomains(acceptDomains)
                .build();
    }

    public Organization getOrganization(){
        return Organization.builder()
                .name(organization.getName())
                .code(organization.getCode())
                .mfaRequired(organization.getMfaRequired())
                .members(organization.getMembers())
                .products(organization.getProducts())
                .bindings(organization.getBindings())
                .acceptDomains(organization.getAcceptDomains())
                .build();
    }

}
