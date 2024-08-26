#@allure.label.layer:rest
#@allure.label.owner:thiago.marques
#@allure.label.url:/products
@createProducts
Feature: SEK Platform Create Product

  Background:
    Given I have access to the platform

  @product01
  Scenario: Create one product without addons
    Given I want to generate a product "without" addons
    When I send product request
    Then the product is created successfully

  @product02
  Scenario: Create product with addons
    Given I want to generate a product "with" addons
    When I send product request
    Then the product is created successfully

  @product03
  Scenario Outline: Create product and do not inform the "Field"
    Given I want to generate a product without the "<Field>"
    When I send product request
    Then an error message is displayed indicating that the "<Field>" is required

    Examples:
      | Field           |
      | name            |
      | code            |
      | description     |
      | addons          |

  @product04
  Scenario Outline: Create product and enter empty "Field"
    Given I want to generate a product with the empty "<Field>"
    When I send product request
    Then an error message is displayed indicating that the "<Field>" cannot be empty

    Examples:
      | Field           |
      | name            |
      | code            |
