Feature: received requests

  @Method
  Scenario: call get with valid id
    * url BaseUrl
    Given path '/long'
    And form fields Input.Fields
    And method post
    Then match responseStatus == Expected.Status
    Then match response == Expected.Response