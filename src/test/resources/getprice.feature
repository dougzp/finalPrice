Feature: Validate get correct final price
  Scenario: Request price for 10:00 on day 14 and product 35455 and brand ZARA
    When I request a price for "2020-06-14-10.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-14-10.00.00" on product "35455" and brand "ZARA" with values 35.50 for priceValue,1 for priceList, "2020-06-14-00.00.00" for startDate, and "2020-12-31-23.59.59" for endDate
  Scenario: Request price for 16:00 on day 14 and product 35455 and brand ZARA
    When I request a price for "2020-06-14-16.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-14-16.00.00" on product "35455" and brand "ZARA" with values 25.45 for priceValue,2 for priceList, "2020-06-14-15.00.00" for startDate, and "2020-06-14-18.30.00" for endDate
  Scenario: Request price for 21:00 on day 14 and product 35455 and brand ZARA
    When I request a price for "2020-06-14-21.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-14-10.00.00" on product "35455" and brand "ZARA" with values 35.50 for priceValue,1 for priceList, "2020-06-14-00.00.00" for startDate, and "2020-12-31-23.59.59" for endDate
  Scenario: Request price for 10:00 on day 15 and product 35455 and brand ZARA
    When I request a price for "2020-06-15-10.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-15-10.00.00" on product "35455" and brand "ZARA" with values 30.50 for priceValue,3 for priceList, "2020-06-15-00.00.00" for startDate, and "2020-06-15-11.00.00" for endDate
  Scenario: Request price for 16:00 on day 16 and product 35455 and brand ZARA
    When I request a price for "2020-06-16-21.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-16-21.00.00" on product "35455" and brand "ZARA" with values 38.95 for priceValue,4 for priceList, "2020-06-15-16.00.00" for startDate, and "2020-12-31-23.59.59" for endDate

