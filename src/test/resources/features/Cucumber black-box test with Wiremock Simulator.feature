#lang: en
Feature: Black-box test of our application where external components are simulated with WireMock

  Scenario: Processing of information through application and external services - Information processed with status OK
    Given some information
      | details | this information should be processed externally and end up with status OK |
    When we post the information to the application
    Then we should receive a response informing us that the information processed with status "OK"

  Scenario: Processing of information through application and external services - Information processed with status FAULTY
    Given some information
      | details | this information should be processed externally and end up with status FAULTY |
    When we post the information to the application
    Then we should receive a response informing us that the information processed with status "FAULTY"
