Feature: Best match

  Background:
    Given an in-memory recording repository
    And the following recordings exist:
      | method | uri | headers           | status code | status text |
      | GET    | /   | Accept: text/html | 200         | OK          |
    And I create a new Playback instance

  Scenario: Best match
    When I receive a GET / request
    Then the response is 200 "OK"
