@Regression
Feature: Cart feature

  Scenario: Create cart and add product
    Given User created cart via api
    When User added product "2876350" with quantity 1 via api with "product_published" payload
    And I open the 'Initial home page'
    And Authenticate to web application by adding "kvn-cart"
    And Navigate to cart page and verify that it contains "2876350" product
