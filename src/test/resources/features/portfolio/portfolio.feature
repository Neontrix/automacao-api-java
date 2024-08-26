#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/portfolio
@createProducts
Feature: SEK Platform Create Portfolio

  Background:
    Given I have an active organization #Implementar o Step Given fazendo tudo em Organization
    And I have an active product #Implementar o Step Given fazendo tudo em Product

  @product01
  Scenario: Associate Portfolio - product on organization
    When I send to associate portfolio
    Then the product is created successfully