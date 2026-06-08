package edu.bo.ucb.jesusvelasco;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:4200";
    protected static final String EMAIL = "admin@zconnect.com";
    protected static final String PASSWORD = "admin123";

    protected static String createdEspecieNombre;
    protected static String createdHabitatNombre;

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
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep();
    }

    protected void sleep() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
