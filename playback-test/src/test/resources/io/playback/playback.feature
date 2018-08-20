Feature: Playback

  Background:
    Given an in-memory recording repository
    And a mock http client
    And I create a new Playback instance

  Scenario: Record request
    Given the http client is configured to return 200 "OK"
    When I receive a GET / request
    Then the response is 200 "OK"
    And the http client received 1 request

  Scenario: Replay same request
    Given the http client is configured to return 200 "OK"
    And I receive a GET / request
    When I receive a GET / request
    Then the response is 200 "OK"
    And the http client received 1 request

  Scenario: Record multiple requests
    Given the http client is configured to return 200 "OK"
    And I receive a GET / request
    When I receive a POST / request
    Then the response is 200 "OK"
    And the http client received 2 requests

  Scenario: Not found response
    Given the http client is configured to return 404 "Not found"
    When I receive a GET /xyz request
    Then the response is 404 "Not found"
    And the http client received 1 request
