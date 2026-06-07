package edu.bo.ucb.manueljimenez;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:4200";
    protected static final String EMAIL = "admin@zconnect.com";
    protected static final String PASSWORD = "admin123";
    protected WebDriverWait wait;
    protected static String createdTaskTitulo;

    @BeforeClass
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        login();
    }

    protected void login() {
        driver.get(BASE_URL + "/login");
        sleep();

        driver.findElement(By.id("Usuario")).sendKeys(EMAIL);
        driver.findElement(By.cssSelector(".p-password-input")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep();

        waitForVisible(By.cssSelector("zoo-profile-button button"));
    }

    protected void navigateToGestionTareas() {
        waitForVisible(By.cssSelector("zoo-profile-button button"));
        driver.findElement(By.cssSelector("zoo-profile-button button")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Panel de Administraci\u00f3n']/ancestor::a")).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gesti\u00f3n Tareas']/ancestor::li")
        ).click();
        sleep();

        driver.findElement(By.cssSelector(".p-drawer-close-button button")).click();
        sleep();
    }

    protected void sleep() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected WebElement waitForVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForInvisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
