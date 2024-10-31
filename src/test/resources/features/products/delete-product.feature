#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{id}/
@deleteProducts
@all
Feature: SEK Platform Delete Product

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @deleteProduct1
  Scenario: Delete product created with valid Code
    When I send request to delete product with "valid" code
    Then the product is deleted

  @deleteProduct2
  Scenario: Delete product with invalid Code
    When I send request to delete product with "nonexistent" code
    Then the product is not deleted