package edu.bo.ucb.luzticona;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;   // ← Cambiado
import org.testng.annotations.BeforeTest;  // ← Cambiado

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:4200";
    protected static final String ADMIN_EMAIL = "admin@zconnect.com";
    protected static final String ADMIN_PASSWORD = "admin123";

    protected static String createdUsuarioEmail;
    protected static String createdUsuarioUsername;

    @BeforeTest   // ← Ahora es @BeforeTest
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    protected void loginAdmin() {
        driver.get(BASE_URL + "/login");
        sleep();

        driver.findElement(By.id("Usuario")).sendKeys(ADMIN_EMAIL);
        driver.findElement(By.cssSelector(".p-password-input")).sendKeys(ADMIN_PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep();
    }

    protected void navigateToAdminPanel() {
        driver.findElement(By.cssSelector("zoo-profile-button button")).click();
        sleep();
        driver.findElement(By.xpath("//span[text()='Panel de Administración']/ancestor::a")).click();
        sleep();
    }

    protected void navigateToGestionUsuarios() {
        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();
        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gestión de Usuarios']/ancestor::li")
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

    @AfterTest   // ← Ahora es @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}