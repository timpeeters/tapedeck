Feature: Best match

  Background:
    Given an in-memory recording repository
    And the following recordings exist:
      | method | uri | headers                                       | status code | status text |
      | GET    | /   | Accept: text/html, Accept-Charset: utf-8      | 200         | OK          |
      | GET    | /   | Accept: text/html, Accept-Charset: iso-8859-1 | 404         | Not found   |
    And I create a new Playback instance

  Scenario: Exact match
    When I receive a GET / request with the following request headers:
      | Accept         | text/html |
      | Accept-Charset | utf-8     |
    Then the response is 200 "OK"
