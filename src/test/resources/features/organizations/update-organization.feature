#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/organizations
@updateOrganizations
@all
Feature: SEK Platform Update Organization

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @cleanupOrganization
  @update_organization1
  Scenario: Update organization created with valid code
    When I send request to update "valid" organization
    Then the organization is updated successfully

  @cleanupOrganization
  @dont_update_organization
  Scenario: Do not Update organization with invalid code
    When I send request to update "nonexistent" organization
    Then the organization is not updated

  @update_organization3
  Scenario Outline: Do not Update an existent organization without mandatory "Field"
    When I send request to update the organization without inform the "<Field>"
    Then the organization is not updated without mandatory field

    Examples:
      | Field            |
#      | name             |
#      | industry         |
      | acceptDomains    |