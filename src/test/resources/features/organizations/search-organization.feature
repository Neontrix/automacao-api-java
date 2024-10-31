#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/organizations
@searchOrganizations
@all
Feature: SEK Platform Create Organization

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @cleanupOrganization
  @search_organization1
  Scenario: Search for organization created with valid code
    When I send request to search a organization with "valid" code
    Then the organization is displayed successfully

  @cleanupOrganization
  @search_organization2
  Scenario: Search for organization created with invalid code
    When I send request to search a organization with "invalid" code
    Then the organization is not displayed

  @search_organization3
  Scenario: Search list of organizations
    When I send request to search a list of organizations
    Then the list of organizations is displayed successfully

  @search_organization4
  Scenario Outline: Search list of organizations with invalid "Field" type
    When I send request to search list of organizations with invalid "<Field>" and value "<Value>"
    Then the list of organizations is not displayed

    Examples:
      | Field            | Value  |
      | ?page            | =999    |
      | ?size            | =999    |
