package br.com.sek.steps;

import br.com.sek.services.PortfolioService;
import br.com.sek.services.ProductService;
import br.com.sek.services.OrganizationService;
import io.cucumber.java.en.Given;

public class PortfolioSteps {
    private final ProductService productService;
    private final OrganizationService organizationService;
    private final PortfolioService portfolioService;

    public PortfolioSteps(PortfolioService portfolioService, ProductService productService, OrganizationService organizationService) {
        this.productService = productService;
        this.organizationService = organizationService;
        this.portfolioService = portfolioService;
    }

    @Given("I send request to associate portfolio")
    public void iSendRequestToAssociatePortfolio() {
        // Recupera produto e organização criados
        String productCode = this.productService.extractCreatedProductCode();
//        String organizationCode = this.organizationService.sendCreateAndGetOrganizationCode();
        //String organizationId = this.organizationService.sendCreateAndGetOrganizationId();

        // Associar portfolio ao produto e organização
        this.portfolioService.sendCreatePortfolioRequest(productCode);
    }

/*    @Then("xxxxxxxxxxxxxxxxxxxxx")
    public void iWantToGenerateAProductAddons(String addonType) {
        this.portfolioService.sendCreatePortfolioRequest(); //Opção para chamar um único método que gere produto e organização para então gerar um portfólio
    }*/

    @Given("the portfolio is created successfully")
    public void thePortfolioIsCreatedSuccessfully() {
    }

}