#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/organizations
@createOrganizations
  @all
Feature: SEK Platform Create Organization

  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @smoke
  @cleanupOrganization
  @create_organization
  Scenario: Create one organization
    Given I want to generate a organization
    When I send request to create organization
    Then the organization is created successfully

  @organization_parameter
  Scenario Outline: Create one organization and not inform "Parameter"
    Given I want to create a organization without the "<Parameter>"
    When I send a invalid request to create organization with status "400"
    Then an error message is displayed indicating that the "<Parameter>" organization is required

    Examples:
      | Parameter     |
      | name          |
      | industry      |
#      | code          |
      | countryCode   |
      | acceptDomains |

  @organization_field
  Scenario Outline: Create one organization and enter empty "Field"
    Given I want to create a organization with the empty "<Field>"
    When I send a invalid request to create organization with status "400"
    Then an error message is displayed indicating that the "<Field>" organization cannot be empty

    Examples:
      | Field         |
      | name          |
#      | code          |
      | countryCode   |

#  @create_organization_existent_code
#  Scenario: Create one organization with an existent code
#    Given I want to create a organization with an existent code
#    When I send a invalid request to create organization with status "409"
#    Then an error message is displayed indicating that the organization "code" exists

  @cleanupOrganization
  @associate_organization
  Scenario: Associate one organization
    Given I want to associate a organization
    When I send request to associate organization
    Then the organization is associate successfully

  @associate_organization_another_holding
  Scenario: Associate one organization in another holding
    Given I want to associate a organization
    When I send request to associate organization in another holding
    Then the organization is not associate

  @cleanupOrganization
  @disassociate_organization
  Scenario: Disassociate one organization
    Given I want to disassociate a organization
    When I send request to disassociate organization
    Then the organization is disassociate
