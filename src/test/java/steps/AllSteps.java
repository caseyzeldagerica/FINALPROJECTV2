package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DemoblazeHomePage;
import java.time.Duration;

public class AllSteps {
    // --- WEB ---
    WebDriver driver;
    DemoblazeHomePage homePage;

    @Given("Saya membuka web Demoblaze")
    public void openWeb() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        homePage = new DemoblazeHomePage(driver);
    }
    @When("Saya login dengan user {string} dan pass {string}")
    public void loginWeb(String user, String pass) {
        homePage.clickLoginMenu();
        homePage.login(user, pass);
        homePage.clickLoginButton();
    }
    @Then("Saya melihat tulisan {string}")
    public void verifyWeb(String expected) {
        Assert.assertEquals(expected, homePage.getWelcomeText());
        driver.quit();
    }

    // --- API ---
    RequestSpecification request;
    Response response;

    @Given("Saya setup API Dummy")
    public void setupApi() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
        request = RestAssured.given().header("app-id", "63a804408eb0cb069b57e43a");
    }
    @When("Saya request GET users")
    public void getApi() { response = request.get("user"); }
    @Then("Statusnya {int} dan datanya ada")
    public void verifyApi(int status) {
        Assert.assertEquals(status, response.getStatusCode());
        Assert.assertTrue(response.getBody().asString().contains("data"));
    }
}