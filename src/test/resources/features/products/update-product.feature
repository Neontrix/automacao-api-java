#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{id}/
@updateProducts
@all
Feature: SEK Platform Update Product

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @update_product1
  Scenario: Update product created with valid code
    When I send request to update "valid" product
    Then the product is updated successfully

  @dont_update_product
  Scenario: Do not Update product with invalid code
    When I send request to update "nonexistent" product
    Then the product is not updated

  @update_product3
  Scenario Outline: Do not Update an existent product without mandatory "Field"
    When I send request to update the product without inform the "<Field>"
    Then the product is not updated without mandatory field

    Examples:
      | Field           |
      | name            |
      | code            |
      | description     |
      | addons          |