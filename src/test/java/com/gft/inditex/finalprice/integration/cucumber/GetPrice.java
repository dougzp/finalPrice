package com.gft.inditex.finalprice.integration.cucumber;


import com.gft.inditex.domain.Brands;
import com.gft.inditex.finalprice.infra.adapters.in.PriceDTO;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPrice extends FinalPriceIntegrationTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private PriceDTO price;
    @Before
    public void setup() {
        log.info("Before any test execution");
    }

    @When("I request a price for {string} and product {string} and brand {string}")
    public void should_get_price_for_given_time_and_product(String applicationDate, String productId, String brand) throws Exception {
        log.info("Running: request a price for application date: " + applicationDate + " and product " + productId + "and Brand: "+        Brands.valueOf(brand).toString()  +" at " + new Date());
        price = getPrices(applicationDate, productId, brand).block();

    }

    @Then("I validate if the price is correct for {string} on product {string} and brand {string} with values {double} for priceValue,{int} for priceList, {string} for startDate, and {string} for endDate")
    public void should_validate_price_for_given_time_and_product(String applicationDate,
                                                                 String productId,
                                                                 String brand,
                                                                 Double expectedPriceValue,
                                                                 Integer expectedPriceList,
                                                                 String expectedStartDate,
                                                                 String expectedEndDate) throws Exception {

        log.info("Running: validate application date: "+ applicationDate +"product: "+productId+ " brand: "+ brand +" correct price at " + new Date());

        assertEquals(expectedPriceValue, price.getPriceValue());
        assertEquals(expectedPriceList, price.getPriceList());
        assertEquals(Integer.parseInt(productId), price.getProductId());
        assertEquals(LocalDateTime.parse(expectedStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")), price.getStartDate());
        assertEquals(LocalDateTime.parse(expectedEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")), price.getEndDate());

    }

    @After
    public void tearDown() {
        log.info("After all test execution");
    }


}
