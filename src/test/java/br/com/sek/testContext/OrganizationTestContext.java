package br.com.sek.testContext;

import br.com.sek.models.request.organization.Organization;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.Organizations;
import io.restassured.response.Response;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

public class OrganizationTestContext {
    private final PlatformSek platformSek;
    private final Organizations organizations;
    @Getter
    private Organization organization;
    private Response response;

    public OrganizationTestContext(PlatformSek platformSek, Organizations organizations) {
        this.platformSek = platformSek;
        this.organizations = organizations;
    }

    public void sendRequest() {
        if (this.organization == null) {
            System.out.println("Organization is null. Cannot send request.");
            return;
        }
        this.response = this.platformSek.executeOrganization(this.organization);
    }

    public void generateOrganization(String string){
        this.organization = this.organizations.getOrganization();
        step("Body: " + this.organization);
    }


}
