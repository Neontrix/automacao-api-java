#@allure.label.layer:rest
#@allure.label.owner:victor.augusto
#@allure.label.url:/organizations
@createProducts
Feature: SEK Platform Create Organization

  Background:
    Given I have access to the platform

  @organization01
  Scenario: Create one organization with members
    Given I want to create a organization "with" members
    When I send organization request
    Then the organization is created successfully

  @organization02
  Scenario: Create one organization with members
    Given I want to create a organization "without" members
    When I send organization request
    Then the organization is created successfully