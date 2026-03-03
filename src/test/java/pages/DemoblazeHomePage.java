package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DemoblazeHomePage {
    WebDriver driver;
    WebDriverWait wait;

    By loginMenu = By.id("login2");
    By usernameField = By.id("loginusername");
    By passwordField = By.id("loginpassword");
    By loginBtn = By.xpath("//button[text()='Log in']");
    By nameOfUser = By.id("nameofuser");

    public DemoblazeHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void clickLoginMenu() { driver.findElement(loginMenu).click(); }
    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
    }
    public void clickLoginButton() { driver.findElement(loginBtn).click(); }
    public String getWelcomeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameOfUser)).getText();
    }
}