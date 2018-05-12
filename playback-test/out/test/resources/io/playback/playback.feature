Feature: Playback

  Background:
    Given an in-memory recording repository
    And a mock http client
    And I create a new Playback instance

  Scenario: Record request
    Given the http client is configured to return 200 OK
    When I receive a GET / request
    Then the response is 200 OK
    And the http client received 1 request

  Scenario: Replay same request
    Given the http client is configured to return 200 OK
    And I receive a GET / request
    When I receive a GET / request
    Then the response is 200 OK
    And the http client received 1 request

  Scenario: Record different request
    Given the http client is configured to return 200 OK
    And I receive a GET / request
    When I receive a POST / request
    Then the response is 200 OK
    And the http client received 2 requests
