#encoding:utf-8


Feature: add to card

  @firefox
  Scenario Outline: check image


    Given i visit trendyol
    And i click fancybox
    And i see <tab> in 5 seconds
    And i click <tab>
    And save the value of "<tab>" as activeTab
    And i see activeTab in 10 seconds
    And save random number less than 15 as seller
    And save random number less than 5 as anyproduct
    And check images
    And save sellerCategoryName element text as thisCategoryname
    And i click seller
    And i see productCategory in 5 seconds
    And save "butikProduct" "title" value as "productName"
    And i see butikProduct in 5 seconds
    And i click butikProduct
    And i see signin in 10 seconds
    And i click signin
    And i see email in 10 seconds
    And i click email
    And type "spothesap52@gmail.com"
    And press tab
    And i click password
    And type "test1234"
    When i click login
    And see "{productName}" in 10 seconds
    And hover sepeteekle
    And i click sepeteekle
    When i click sepetim
    Then  see "{productName}" in 10 seconds
    And i see deleteMyOrder in 5 seconds
    When i click deleteMyOrder
    And i see delete in 5 seconds
    And i click delete
    Then do not see deleteMyOrder in 10 seconds


    Examples:
      | tab           |
      | KADIN         |
      | ERKEK         |
      | COCUK         |
      | EVYASAM       |
      | SUPERMARKET   |
      | KOZMETIK      |
      | AYAKKABICANTA |
      | SAATAKSESUAR  |
      | ELEKTRONIK    |












