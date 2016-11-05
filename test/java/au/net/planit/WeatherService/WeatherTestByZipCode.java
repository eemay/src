package au.net.planit.WeatherService;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class WeatherTestByZipCode{

	private Hashtable<String,String> zipAndCities;
	private int failureCount=0,totalCount=0;
	private StringBuffer zipCodes=new StringBuffer();
	public WebDriver driver;
	
	@Given("^I have a list of zipcode$")
	public void i_have_a_list_of_zipcode(){
	    // Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", "/Users/Eemay/Desktop/SoftwareProjects/chromedriver");
		driver= new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	    driver.get("http://www.phaster.com/zip_code.html");
		
	    List<WebElement> rows = driver.findElements(By.cssSelector("blockquote tr"));
        zipAndCities = new Hashtable<>();
        for (int i = 1; i < 5; i++) {
            List<WebElement> columns = rows.get(i).findElements(By.cssSelector("td"));
            String city = columns.get(1).getText().split("\n")[0].split("\\(")[0];
            String zipCode = columns.get(2).getText().split(" ")[0].split("-")[0].split("\n")[0];
            zipAndCities.put(zipCode, city);
        }
	}

	@When("^I use the weather service to get the weather information$")
	public void i_use_the_weather_service_to_get_the_weather_information() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		        Set<Entry<String, String>> set = zipAndCities.entrySet();
		        Iterator<Entry<String, String>> iterator = set.iterator();
		        while (iterator.hasNext()) {
		        	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
	                Entry<String, String> entry = iterator.next();
		            String zipCode = entry.getKey();
		            String city = entry.getValue();
		            String[] prop ={"usePropertyFileFlag=true","zipCode="+zipCode, "city="+city};
		            try {
		            	SoapUITestCaseRunner SoapUITestCaseRunner= new SoapUITestCaseRunner();
		                SoapUITestCaseRunner.setProjectFile("src/test/resources/May-SOAP-WMSAutomation-soapui-project.xml");
		                SoapUITestCaseRunner.setProjectProperties(prop);
		                SoapUITestCaseRunner.setTestSuite("TestSuite 1");
 		                SoapUITestCaseRunner.setTestCase("TestCase 1");
		                SoapUITestCaseRunner.run();
		            } catch (Exception e) {
		                System.err.println("checking " + zipCode + " failed!");
		                failureCount++;
		                zipCodes.append(zipCode + " [" + city +"] ");
		                e.printStackTrace();
		            }finally{
		                totalCount++;
		                //driver.quit();
		            }
		        }
	}

	@Then("^the information should be found successfully$")
	public void the_information_should_be_found_successfully() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
        assertArrayEquals("There are " + failureCount +" in " + totalCount+" cities are not found, " + zipCodes.toString(), new
        int[]{0}, new
		int[]{failureCount});
        driver.quit();

	}

}
