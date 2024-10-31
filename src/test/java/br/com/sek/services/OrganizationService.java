package br.com.sek.services;

import br.com.sek.helpers.Assertions;
import br.com.sek.models.exceptions.Messages;
import br.com.sek.models.request.organization.Organization;
import br.com.sek.models.response.OrganizationResponse;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.Organizations;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.*;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.*;

public class OrganizationService extends Assertions {
    private final PlatformSek platformSek;
    private final Organizations organizations;
    private final Assertions assertions;
    @Getter
    private Organization organization;
    private String organizationName;
    private String organizationNewName;
    public String organizationCode;
    private String firstOrganizationCode;
    private String secondOrganizationCode;
    private String thirdOrganizationCode;
    private String organizationId;
    private String validationStatus;
    private String field;
    private Response response;
    private List<Map<String, Object>> content;
    private static final Set<String> VALID_FIELDS = new HashSet<>(Set.of(
            "name", "industry", "acceptDomains"
    ));

    public OrganizationService(PlatformSek platformSek, Organizations organizations, Assertions assertions) {
        this.platformSek = platformSek;
        this.organizations = organizations;
        this.assertions = assertions;
    }
    private static final String invalidCode = "999SASX";

    // region Organization Methods

    // region Generate
    public void generateFakerOrganization() {
        this.organization = this.organizations.getOrganization();
        step("Body: " + this.organization);
    }

    public void generateFakerOrganizationWithoutParameter(String parameter){
        this.organization = this.organizations.generateOrganizationWithoutParameter(parameter);
        step("Body: " + this.organization);
    }

    public void generateFakerOrganizationWithEmptyField(String field) {
        this.organization = this.organizations.getOrganizationsWithEmptyField(field);
        step("Body: " + this.organization);
    }
    // endregion Generate

    // region Create Methods
    public void sendCreateOrganizationRequest() {
        if (this.organization == null) {
            System.out.println(Messages.errorMessages.organizationIsNull);
            return;
        }
        this.response = this.platformSek.postOrganization(this.organization);
        assertResponseStatusCode(this.response,"201");
        extractOrganizationCode();
        extractOrganizationId();
    }

    public void sendInvalidCreateOrganizationRequest(String expectedStatusCode){
        if (this.organization == null) {
            System.out.println(Messages.errorMessages.organizationIsNull);
            return;
        }
        this.response = this.platformSek.postOrganization(this.organization);
        assertResponseStatusCode(this.response,expectedStatusCode);
    }

    public void createTwoOrganizationsRequest() {
        if (this.organization == null) {
            System.out.println(Messages.errorMessages.organizationIsNull);
            return;
        }
        sendCreateOrganizationRequest();
        this.firstOrganizationCode = this.organizationCode;

        this.organization = this.organizations.generateSecondOrganization();
        sendCreateOrganizationRequest();
        this.secondOrganizationCode = this.organizationCode;
    }

    public void createThreeOrganizationsRequest() {
        if (this.organization == null) {
            System.out.println(Messages.errorMessages.organizationIsNull);
            return;
        }
        sendCreateOrganizationRequest();
        this.firstOrganizationCode = this.organizationCode;

        this.organization = this.organizations.generateSecondOrganization();
        sendCreateOrganizationRequest();
        this.secondOrganizationCode = this.organizationCode;

        this.organization = this.organizations.generateThirdOrganization();
        sendCreateOrganizationRequest();
        this.thirdOrganizationCode = this.organizationCode;
    }

    public void associateOrganizations() {
        if (this.firstOrganizationCode != null && this.secondOrganizationCode != null) {
            System.out.println("Associating child organization CODE: " + secondOrganizationCode +
                    " with first organization CODE: " + firstOrganizationCode);

            this.response = this.platformSek.postAssociateOrganizations(firstOrganizationCode, secondOrganizationCode);
            assertResponseStatusCode(this.response, "200");
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void associateOrganizationsInAnotherHolding() {
        if (this.firstOrganizationCode != null && this.secondOrganizationCode != null && this.thirdOrganizationCode != null) {
            // Chamando o método para associar as duas primeiras organizações
            associateOrganizations();

            // Agora os métodos para associar a terceira organização
            System.out.println("Trying to associate second organization CODE: " + secondOrganizationCode +
                    " with third organization CODE: " + thirdOrganizationCode);

            this.response = this.platformSek.postAssociateOrganizations(thirdOrganizationCode, secondOrganizationCode);
            assertResponseStatusCode(this.response, "409");
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void disassociateOrganizations(){
        if (this.firstOrganizationCode != null && this.secondOrganizationCode != null) {
            System.out.println("Disassociating child organization CODE: " + secondOrganizationCode +
                    " with parent organization CODE: " + firstOrganizationCode);

            this.response = this.platformSek.deleteDisassociateOrganizations(firstOrganizationCode, secondOrganizationCode);
            assertResponseStatusCode(this.response, "204");
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void searchOrganization(String validationStatus){
        this.validationStatus = validationStatus;

        if ("valid".equals(validationStatus)) {
            if (response != null && response.statusCode() == 201) {
                System.out.println("GET Organization Code: " + this.organizationCode);
                this.response = this.platformSek.getOrganization(this.organizationCode);
            } else {
                System.out.println("No organization found with this Code: " + this.organizationCode);
                throw new IllegalStateException(Messages.errorMessages.noRegisteredOrganization);
            }
        } else if ("invalid".equals(validationStatus)) {
            System.out.println("GET Invalid Code: " + invalidCode);
            this.response = this.platformSek.getOrganization(invalidCode);
        } else {
            System.out.println("No organization found");
        }
    }

    public void searchListOrganizations(){
        System.out.println("GET list of Organizations");
        this.response = this.platformSek.getOrganizationsList();
    }

    public void searchListOrganizationsInvalidFields(String field, String value){
        String url = field + value;
        System.out.println("GET list of Organizations with invalid Field: " + field);
        this.response = this.platformSek.getOrganizationsListWithParams(url);
        assertResponseStatusCode(this.response,"200");
        this.content = response.path("content");
    }
    // endregion Create Methods

    // region Return Organization Key
    public String sendCreateAndGetOrganizationCode() {
        this.organization = this.organizations.getOrganization();
        this.organizationCode = this.organization.getCode();
        step("code: " + this.organizationCode);
        return this.organizationCode;
    }

    public void sendCreateAndGetOrganizationId() {
        this.organization = this.organizations.getOrganization();
        this.organizationId = String.valueOf(this.organization.getId());
        step("id: " + this.organizationId);
    }

    public void extractOrganizationCode() {
        this.organizationCode = response.path("code");
    }

    public void extractOrganizationId() {
        this.organizationId = response.path("id");
    }

    public String getCreatedOrganizationId() {
        step("id: " + this.organizationId);
        return this.organizationId;
    }

    public String getCreatedOrganizationName() {
        this.organization = this.organizations.getOrganization();
        this.organizationName = this.organization.getName();
        step("name: " + this.organizationName);
        return this.organizationName;
    }

    public String getCreatedOrganizationCode() {
        this.organization = this.organizations.getOrganization();
        step("code: " + organizationCode);
        return organizationCode;
    }

    public void updateOrganization(String validationStatus) {
        String status = validationStatus.toLowerCase();
        System.out.println("Updating organization based on validation status");

        Organization updateRequest = this.organizations.generateUpdateRequestWithFaker();
        this.organizationNewName = updateRequest.getName(); // Armazenando o nome gerado
        switch (status) {
            case "valid":
                System.out.println("Update organization with valid code: " + this.organizationCode);
                this.response = this.platformSek.patchOrganization(this.organizationCode, updateRequest);

                break;
            case "nonexistent":
                System.out.println("Update with nonexistent Code: " + invalidCode);
                this.response = this.platformSek.patchOrganization(invalidCode, updateRequest);

                break;
            default:
                System.out.println(Messages.errorMessages.invalidOrganizationStatus);
        }
        // Validar resposta
        this.validationStatus = status;
        validateSearchOrganization();
    }

    public void updateOrganizationWithoutField(String field) {
        this.field = field;
        if (VALID_FIELDS.contains(field)) {
            System.out.println("Update organization with valid Code: " + this.organizationCode);
            System.out.println("Updating with Field " + field + " hidden.");

            this.response = this.platformSek.patchOrganization(this.organizationCode, this.organizations.generateUpdateFieldToOmit(field));

        } else {
            throw new IllegalArgumentException("Invalid field: " + field);
        }
    }
    // endregion Return Organization Key

    // region Delete Methods
    public void deactivateOrganization(String validationStatus) {
        this.validationStatus = validationStatus;
        if (validationStatus.equals("valid")) {
            if (this.organizationCode != null) {
                System.out.println("Deactivate organization with CODE: " + organizationCode);
                this.response = this.platformSek.deactivateOrganization(organizationCode);
                assertResponseStatusCode(this.response, "204");
            } else {
                throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
            }
        } else if (validationStatus.equals("nonexistent")) {
            System.out.println("Attempting to deactivate organization with nonexistent ID");
            this.response = this.platformSek.deactivateOrganization(invalidCode);
            assertResponseStatusCode(this.response, "404");
        }
    }

    public void deactivateOrganizations() {
        List<String> organizationCodes = Arrays.asList(firstOrganizationCode, secondOrganizationCode, thirdOrganizationCode);

        if (organizationCodes.stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }

        organizationCodes.stream()
                .filter(Objects::nonNull)
                .forEach(code -> {
                    System.out.println("Deactivating organization with CODE: " + code);
                    this.response = this.platformSek.deactivateOrganization(code);
                });
    }

    // Endpoint de delete desativado
/*    public void removeOrganizations(){
            System.out.println("Remove organizations");
            this.response = this.platformSek.removeOrganizations();
            assertResponseStatusCode(this.response,"204");
    }*/
    // endregion Delete Methods

    // region Asserts
    public void verifyErrorFieldsDisplayed(String field, int expectedStatus) {
        assertions.setResponse(response);
        this.assertions.verifyErrorFieldsDisplayed(field, expectedStatus);
        step("Body: " + response.getBody().asString());
    }

    public void validateValuesOfCreatedOrganization(Response response) {
        String originalIdOrganization = getCreatedOrganizationId();
        String originalNameOrganization = getCreatedOrganizationName();
        String originalCodeOrganization = getCreatedOrganizationCode();

        OrganizationResponse objResponse = response.as(OrganizationResponse.class);

        assertValues(originalIdOrganization, objResponse.getId());
        assertValues(originalNameOrganization, objResponse.getName());
        assertValues(originalCodeOrganization, objResponse.getCode());
    }

    public void validateValuesOfUpdatedOrganization(Response response) {
        String newNameOrganization = this.organizationNewName;
        OrganizationResponse objResponse = response.as(OrganizationResponse.class);

        assertValues(newNameOrganization, objResponse.getName());
    }

    public void validateOrganizationAssocied() {
        if (this.firstOrganizationCode != null && this.secondOrganizationCode != null) {
            System.out.println("GET organization with CODE: " + firstOrganizationCode);
            this.response = this.platformSek.getOrganization(firstOrganizationCode);

            assertResponseStatusCode(this.response, "200");
            List<String> bindingsCode = this.response.jsonPath().getList("bindings.code");

            boolean isAssociated = bindingsCode.contains(this.secondOrganizationCode);
            assertIsTrue(isAssociated);
            if (isAssociated) {
                System.out.println("The child organization code: " + this.secondOrganizationCode + " is associated in organization code: " + this.firstOrganizationCode);
            }else{
                throw new IllegalStateException(Messages.errorMessages.organizationNotAssociated);
            }
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void validateOrganizationDisassociated() {
        if (this.firstOrganizationCode != null) {
            System.out.println("GET organization with CODE: " + firstOrganizationCode);
            this.response = this.platformSek.getOrganization(firstOrganizationCode);

            assertResponseStatusCode(this.response, "200");

            if (this.response.jsonPath().get("bindings") != null) {
                List<String> bindingsCode = this.response.jsonPath().getList("bindings.code");
                boolean isAssociated = bindingsCode != null && bindingsCode.contains(this.secondOrganizationCode);
                assertIsTrue(isAssociated);
                if (isAssociated) {
                    throw new IllegalStateException("The organization code: " + this.secondOrganizationCode + " is still associated");
                }
            } else {
                System.out.println("The organization is not contains bindings");
            }
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void validateFirstAndNotThirdAssociation() {
        if (this.firstOrganizationCode != null && this.secondOrganizationCode != null && this.thirdOrganizationCode != null) {
            System.out.println("GET organization with CODE: " + firstOrganizationCode);
            this.response = this.platformSek.getOrganization(firstOrganizationCode);

            assertResponseStatusCode(this.response, "200");

            List<String> bindingsCode = this.response.jsonPath().getList("bindings.code");
            boolean isSecondOrganizationAssociated = bindingsCode != null && bindingsCode.contains(this.secondOrganizationCode);
            assertIsTrue(isSecondOrganizationAssociated);

            if (!isSecondOrganizationAssociated) {
                throw new IllegalStateException("The organization code: " + this.secondOrganizationCode + " is not associated with the first organization");
            } else {
                System.out.println("The organization code: " + this.secondOrganizationCode + " is associated with the first organization CODE: " + this.firstOrganizationCode);
            }
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

    public void validateOrganization(String operation) {
        if (this.organizationCode != null) {
            System.out.println("GET created organization with CODE after register: " + organizationCode);
            this.response = this.platformSek.getOrganization(organizationCode);

            assertResponseStatusCode(this.response,"200");
            if(operation.toLowerCase() == "create"){
                validateValuesOfCreatedOrganization(this.response);
            }else if(operation.toLowerCase() == "update"){
                validateValuesOfUpdatedOrganization(this.response);
            }
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }

   /* public void validateOrganizationCreated() {
        if (this.organizationCode != null) {
            System.out.println("GET created organization with CODE after register: " + organizationCode);
            this.response = this.platformSek.getOrganization(organizationCode);

            assertResponseStatusCode(this.response,"200");
            validateOrganizationValues(this.response, organizationCode, "create");
            this.validationStatus = "valid";
        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }*/

   /* public void validateOrganizationUpdated() {
        if (this.organizationCode != null) {
            this.response = this.platformSek.getOrganization(organizationCode); // colocar metodo de update
            this.organizationName = getNewOrganizationName(this.response);
            System.out.println("GET updated organization with CODE after register: " + this.organizationCode);
            //this.response = this.platformSek.getOrganization(organizationCode);

            assertResponseStatusCode(this.response,"200");
            validateOrganizationValues(this.response, organizationCode, "update");



        } else {
            throw new IllegalStateException(Messages.errorMessages.organizationCodeNotSet);
        }
    }*/

    public void validateSearchOrganization(){
        if ("valid".equals(this.validationStatus)) {
            response.then()
                    .body("code", equalTo(this.organizationCode));
            assertResponseStatusCode(this.response, "200");
        }else{
            response.then()
                    .body("message", equalTo("Not found organization with code " + invalidCode));
            assertResponseStatusCode(this.response, "404");
        }
    }

    public void validateSearchListOrganizations(){
        assertResponseStatusCode(this.response, "200");

        response.then()
                .body("content[0]", hasKey("id"))
                .body("content[0]", hasKey("name"))
                .body("content[0]", hasKey("code"))
                .body("content[0]", hasKey("industry"));
    }

    public void validateDeactivatedOrganization(){
        if (this.validationStatus.equals("valid")){
            System.out.println("GET Organization code: " + this.organizationCode);
            this.response = this.platformSek.getOrganization(this.organizationCode);
            assertMessageErrorAndStatusCode(this.response, "code", "INACTIVATED", "403");
        }else if (this.validationStatus.equals("nonexistent")){
            System.out.println("GET Organization code: " + invalidCode);
            this.response = this.platformSek.getOrganization(invalidCode);
            assertMessageErrorAndStatusCode(this.response, "code", "NOT_FOUND", "404");
        }else{
            throw new IllegalStateException(Messages.errorMessages.invalidOrganizationStatus);
        }
    }

    public void validateOmittedFieldIsRequired() {
        response.then()
                .statusCode(400)
                // O certo é a lógica comentada, porém tem defect. Então para o teste rodar foi comentado.
                .body("fields", containsInAnyOrder(this.field))
                .body("code", equalTo("INVALID_FIELDS"));
    }

    public void resultOrganizationsWithInvalidField(){
        if (content == null || content.isEmpty()) {
            System.out.println("No organizations found.");
        } else {
            System.out.println("Organizations found.");
        }
        step("Body: " + response.getBody().asString());
    }
    // endregion Asserts

    // endregion Organization Methods
}
