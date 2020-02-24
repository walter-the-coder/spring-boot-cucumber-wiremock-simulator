#lang: en
Feature: Black-box test with Wiremock Simulator

  Scenario: The information controller should answer our calls
    Given some information
      | details | whatever |
    When we test the applications API by posting the information
    Then we should receive a response with the text "information was received!"
