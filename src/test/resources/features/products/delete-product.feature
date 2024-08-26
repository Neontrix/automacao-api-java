#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{id}/
@deleteProducts
Feature: SEK Platform Delete Product

  Background:
    Given I have access to the platform

  @product09
  Scenario: Delete product created with valid ID
    When I want to delete product with "valid" ID
    Then the product is deleted

  @product10
  Scenario: Delete product with invalid ID
    When I want to delete product with "nonexistent" ID
    Then the product is not deleted