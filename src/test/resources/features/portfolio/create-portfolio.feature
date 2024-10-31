#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/portfolio
@createPortfolio
@all
Feature: SEK Platform Create Portfolio

  Background:
    Given I have access to the platform through organization code "SEK-BR"
    And I have an active product
#    And I have an active organization

  @smoke
  @create_portfolio
  Scenario: Associate Product on organization
    When I send request to associate portfolio
   Then the portfolio is created successfully



#  @portfolio_incorrect_product_code
#  Scenario: Dont Associate Product - Incorrect product code
#    When I send request with product code "incorrect"
#    Then an error message is displayed indicating that the product code is incorrect
#
#  @portfolio_invalid_product_code
#  Scenario: Dont Associate Portfolio - Invalid product code
#    When I send request with product code "invalid"
#    Then an error message is displayed indicating that the product code is invalid
#
#  @portfolio_dont_inform_request_body
#  Scenario: Dont Associate Portfolio - Dont inform request body
#    When I send request with empty request body
#    Then an error message is displayed indicating that the request body addons is required
#
#  @portfolio_addons_organization_associated
#  Scenario: Insert product addons in organization associated
#    When I send request to insert product addons in organization
#    Then the product addons insert with success
