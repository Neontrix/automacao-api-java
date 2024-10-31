#@allure.label.layer:rest
#@allure.label.owner:thiago.marques
#@allure.label.url:/repos/{owner}/{repo}/labels
@signup
Feature: SEK Platform - Signup
  Background:
    Given I have access to the platform through organization code "SEK-BR"

  @signup
  Scenario: Send a signup
    Given I have a user to sign up
    When I send the Signup request
    Then I see the successful signup registration

  @signup
  Scenario Outline: Send a wrong signup missing required fields
    Given I have a user to sign up
    And I remove the field "<Field>"
    When I send the Signup request
    Then I see the error message of field "<Field>"
    Examples:
    | Field             |
    | name              |
    | email             |
    | phone             |
#    | phoneFilled       |
#    | phoneCountryCode  |
    | company           |
    | roles             |
#    | roles.name        |
    | title             |
    | settings          |
    #    | products    |

  @tc05
  Scenario Outline: Send a wrong signup blank required fields
    Given I have a user to sign up
    And I set the field "<Field>" to blank
    When I send the Signup request
    Then I see the error message of field "<Field>"
    Examples:
      | Field     |
      | name      |
#      | email     |
#      | phone     |
#      | company   |
##      | roles     |
#      | title     |
##      | settings  |