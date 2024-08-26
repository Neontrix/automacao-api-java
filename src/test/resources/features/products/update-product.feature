#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{id}/
@updateProducts
Feature: SEK Platform Update Product

  Background:
    Given I have access to the platform

  @product11
  Scenario: Update product created with valid ID
    When I want to update "valid" product
    Then the product is updated successfully

  @product12
  Scenario: Update product with invalid ID
    When I want to update "nonexistent" product
    Then the product is not updated

  @product13
  Scenario Outline: Update product and do not inform the "Field" type
    Given I want to update the product without inform the "<Field>"
    Then the product is not updated

    Examples:
      | Field           |
      | name            |
      | code            |
      | description     |
      | addons          |