#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/products/{productCode}/
@searchProducts
@all
Feature: SEK Platform Search Product

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @search_product1
  Scenario: Search for product created with valid code
    When I send request to search a product with "valid" code
    Then the product is displayed successfully

  @search_product2
  Scenario: Search product with invalid code
    When I send request to search a product with "invalid" code
    Then the product is not displayed

  @search_product3
  Scenario: Search list of products
    When I send request to search a list of products
    Then the list of products is displayed successfully

  @search_product4
  Scenario Outline: Search product with invalid "Field" type
    When I send request to search product with invalid "<Field>" and value "<Value>"
    Then the list of products is not displayed

    Examples:
      | Field            | Value  |
      | ?page            | =999    |
      | ?size            | =999    |