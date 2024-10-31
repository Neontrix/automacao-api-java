#@allure.label.layer:rest
#@allure.label.owner:thiago.marques
#@allure.label.url:/products
@createProducts
@all
Feature: SEK Platform Create Product

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @create_product_without_addon
  Scenario: Create one product without addons
    Given I want to generate a product "without" addons
    When I send request to create product
    Then the product is created successfully

  @create_product_with_addons
  Scenario: Create product with addons
    Given I want to generate a product "with" addons
    When I send request to create product
    Then the product is created successfully

  @product_not_inform_field
  Scenario Outline: Create product and do not inform the "Field"
    Given I want to generate a product without the "<Field>"
    When I send a invalid request to create product
    Then an error message is displayed indicating that the "<Field>" product is required

    Examples:
      | Field           |
      | name            |
      | code            |
      | description     |
      | addons          |

  @product_empty_field
  Scenario Outline: Create product and enter empty "Field"
    Given I want to generate a product with the empty "<Field>"
    When I send a invalid request to create product
    Then an error message is displayed indicating that the "<Field>" product cannot be empty

    Examples:
      | Field           |
      | name            |
      | code            |
