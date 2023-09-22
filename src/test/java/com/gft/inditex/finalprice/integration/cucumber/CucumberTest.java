package com.gft.inditex.finalprice.integration.cucumber;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue="com.gft.inditex.finalprice.integration.cucumber")
public class CucumberTest {


}