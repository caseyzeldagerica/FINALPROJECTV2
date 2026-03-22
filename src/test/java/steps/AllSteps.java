package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DemoblazeHomePage;
import java.time.Duration;

public class AllSteps {
    WebDriver driver;
    DemoblazeHomePage homePage;
    RequestSpecification request;
    Response response;

    // --- WEB STEPS ---
    @Given("Saya membuka web Demoblaze")
    public void openWeb() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://www.demoblaze.com/");
        homePage = new DemoblazeHomePage(driver);
    }

    @When("Saya login dengan user {string} dan pass {string}")
    public void login(String user, String pass) {
        homePage.clickLoginMenu();
        homePage.login(user, pass);
        homePage.clickLoginButton();
    }

    @Then("Saya melihat tulisan {string}")
    public void verify(String expected) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            if(expected.equals("alert")) {
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
            } else {
                WebElement welcomeMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
                Assert.assertEquals(expected, welcomeMsg.getText());
            }
        } finally {
            driver.quit(); // Wajib quit biar memory Linux nggak penuh
        }
    }

    @And("Saya menambah barang {string} ke cart")
    public void addToCart(String item) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Klik Nama Barang (Pakai retry kalau gagal)
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(item))).click();

        // Klik Tombol Add to Cart
        WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Add to cart']")));
        addToCartBtn.click();

        // Tunggu Alert muncul dan klik OK
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Klik Menu Cart untuk lanjut checkout
        driver.findElement(By.id("cartur")).click();
    }

    @And("Saya melakukan checkout dengan nama {string}")
    public void checkout(String nama) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(nama);
        driver.findElement(By.id("country")).sendKeys("Indonesia");
        driver.findElement(By.id("city")).sendKeys("Jakarta");
        driver.findElement(By.id("card")).sendKeys("123456");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2026");
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
    }

    @Then("Saya melihat pesan sukses {string}")
    public void verifySuccess(String msg) {
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'Thank you')]")).isDisplayed());
        driver.quit();
    }

    // --- API STEPS ---
    @Given("Saya setup API Dummy")
    public void setupApi() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
        request = RestAssured.given().header("app-id", "63a804408eb0cb069b57e43a");
    }

    @Given("Saya setup API Dummy tanpa App ID")
    public void setupApiNoId() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
        request = RestAssured.given();
    }

    @When("Saya request GET {string}")
    public void getReq(String endpoint) { response = request.get(endpoint); }

    @Then("Statusnya {int} dan datanya ada")
    public void verifyApi(int status) {
        Assert.assertEquals(status, response.getStatusCode());
        Assert.assertTrue(response.getBody().asString().contains("data") || response.getBody().asString().contains("id"));
    }

    @Then("Statusnya {int} dan pesan error muncul")
    public void verifyError(int status) {
        Assert.assertEquals(status, response.getStatusCode());
        Assert.assertTrue(response.getBody().asString().contains("APP_ID_MISSING"));
    }
}