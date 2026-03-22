Feature: Final Project QA Automation

  @api
  Scenario Outline: API Test - Get Users and Validations
    Given Saya setup API Dummy
    When Saya request GET "<endpoint>"
    Then Statusnya <status> dan datanya ada
    Examples:
      | endpoint      | status |
      | user          | 200    |
      | user?limit=10 | 200    |
      | tag           | 200    |
      | post          | 200    |

  @api
  Scenario: API Test - Negative Case (Missing App ID)
    Given Saya setup API Dummy tanpa App ID
    When Saya request GET "user"
    Then Statusnya 403 dan pesan error muncul

  @web
  Scenario Outline: Web Test - Login Scenarios
    Given Saya membuka web Demoblaze
    When Saya login dengan user "<user>" dan pass "<pass>"
    Then Saya melihat tulisan "<pesan>"
    Examples:
      | user   | pass   | pesan         |
      | admin  | admin  | Welcome admin |
      | admin  | salah  | alert         |

  @web
  Scenario: Web Test - Checkout E2E
    Given Saya membuka web Demoblaze
    When Saya login dengan user "admin" dan pass "admin"
    And Saya menambah barang "Samsung galaxy s6" ke cart
    And Saya melakukan checkout dengan nama "Casey"
    Then Saya melihat pesan sukses "Thank you for your purchase!"