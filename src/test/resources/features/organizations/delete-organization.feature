#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/organizations/{organizationCode}/
@deleteOrganizations
@all
Feature: SEK Platform Delete Organization

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @delete_organization1
  Scenario: Delete organization created with valid code
    When I send request to delete organization with "valid" code
    Then the organization is deactivated

  @delete_organization2
  Scenario: Delete organization with invalid code
    When I send request to delete organization with "nonexistent" code
    Then the organization is not deleted