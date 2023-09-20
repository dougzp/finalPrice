Feature: Validate get correct final price
  Scenario: Request price for 10:00 on day 14 and product 35455
    When I request a price for "2020-06-14-10.00.00" and product "35455" and brand "ZARA"
    Then I validate if the price is correct for "2020-06-14-10.00.00" on product "35455" and brand "ZARA" with values 35.50 for priceValue,1 for priceList, "2020-06-14-00.00.00" for startDate, and "2020-12-31-23.59.59" for endDate
  Scenario: Request price for 10:00 on day 14 and product 35455
    Whence for "2020-06-14-16.00.00" and product "35455" and brand "ZARA"
    Then I validate if I request a pri the price is correct for "2020-06-14-16.00.00" on product "35455" and brand "ZARA" with values 25.45 for priceValue,2 for priceList, "2020-06-14-15.00.00" for startDate, and "2020-06-14-18.30.00" for endDate

#
#
#  //INSERT INTO prices (id, brand_id, start_date, end_date, price_list_id, product_id, priority, price, currency)
#  //        (UUID(), 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
#  //            (UUID(), 1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
#  //            (UUID(), 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
#  //            (UUID(), 1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
#
#  //    Test 1: peFción a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
#  //            - Test 2: peFción a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
#  //            - Test 3: peFción a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
#  //            - Test 4: peFción a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
#  //            - Test 5: peFción a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)