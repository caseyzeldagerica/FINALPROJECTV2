@api
Feature: Final Project API

  Scenario: Ambil data user dari DummyAPI
    Given Saya setup API Dummy
    When Saya request GET users
    Then Statusnya 200 dan datanya ada