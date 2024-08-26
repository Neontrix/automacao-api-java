#@allure.label.layer:rest
#@allure.label.owner:thiago.marques
#@allure.label.url:/repos/{owner}/{repo}/labels
@getProfiles
Feature: SEK Platform Get Profiles

  @tc01
  Scenario: List actions for a specific profile
    Given I have access to the platform
    When I query the data
    Then I see the scope and types for each profile

  @tc02
  Scenario Outline: Verify error message for requests with incorrect/invalid user
    Given I have access to the platform
    When I query the data with user "<User>"
    Then I see the error message "<Message>" and status code "<StatusCode>"

    Examples:
      | User    | Message                                                     | StatusCode  |
      | 123456  | There is no document associated with such projectId/nodeId. | 404         |
      | 124x    | Request responded with status code 400                      | 500         |