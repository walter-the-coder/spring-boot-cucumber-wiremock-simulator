#lang: en
Feature: Black-box test with Wiremock Simulator

  Scenario: Input to controller should succeed
    Given a set of information
      | details | whatever |
    When we post the information to the applications API
    Then we should receive a response with the text "information was stored!"
