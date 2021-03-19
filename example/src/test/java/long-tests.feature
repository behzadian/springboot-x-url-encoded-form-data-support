Feature: received requests

  Background:

  Scenario: verifies that form post works
    * def long = { Expected: {Status: 200, Response: "1" } }
    * set long.Input = { Fields: {id: 1} }
    * set long.Result = call read("classpath:long-method.feature") long