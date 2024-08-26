#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{id}/
@searchProducts
Feature: SEK Platform Search Product

  Background:
    Given I have access to the platform

  @product05
  Scenario: Search for product created with valid ID
    When I want to search a product with "valid" ID
    Then the product is displayed successfully

  @product06
  Scenario: Search product with invalid ID
    When I want to search a product with "invalid" ID
    Then the product is not displayed

  @product07
  Scenario: Search list of products
    When I want to search list of products
    Then the list of products is displayed successfully

  @product08
  Scenario Outline: Search product with invalid "Field" type
    When I want to search product with invalid "<Field>" and value "<Value>"
    Then the list of products is not displayed

    Examples:
      | Field            | Value  |
      | ?page            | =999    |
      | ?size            | =999    |