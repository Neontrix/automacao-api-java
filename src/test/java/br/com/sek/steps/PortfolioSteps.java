package br.com.sek.steps;

import io.cucumber.java.en.Given;

public class PortfolioSteps {
    private final ProductSteps productSteps;

    public PortfolioSteps(ProductSteps productSteps) {
        this.productSteps = productSteps;
    }

//    @Given("xxxxxxxxxxxxxxxx")
//    public void iWantToGenerateAProductAddons(String addonType) {
//        this.productSteps.Ihaveanactiveproduct(); //Opção para chamar métodos isolados de geração de produto e organização e então gerar um portfólio
//        this.organizationSteps.iWantToGenerateAOrganization();
//
//    }
//
//    @Then("xxxxxxxxxxxxxxxxxxxxx")
//    public void iWantToGenerateAProductAddons(String addonType) {
//        this.service.generatePortfolio(); //Opção para chamar um único método que gere produto e organização para então gerar um portfólio
//    }
}
