package au.net.planit.WeatherService;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", tags={"@getWeatherByZipcode"},format = {"pretty", "html:build/Cucumber -report", "json:build/Cucumber -json-report.json"})

public class RunCukesTest{

}
