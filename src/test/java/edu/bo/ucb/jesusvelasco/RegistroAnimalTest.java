package edu.bo.ucb.jesusvelasco;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero registrar un nuevo animal
// en el sistema con todos sus datos para que sea visible en el catalogo publico
//
// Prueba de Aceptacion: Verificar que un animal con datos completos se registre
// exitosamente y se muestre la confirmacion correspondiente
//
// Paso 1. Ingresar al formulario de creacion de animal en /admin/animales/crear
// Paso 2. Llenar todos los campos requeridos (nombre, especie, habitat, descripcion)
// Paso 3. Enviar el formulario y esperar la confirmacion
//
// Resultado Esperado: El animal se crea exitosamente y se muestra un mensaje de exito
// o redireccion a la pagina de detalle
/****************************************/

public class RegistroAnimalTest {

    private WebDriver driver;
    private static final String BASE_URL = "http://localhost:4200";

    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void registroAnimalConDatosCompletos() {
        long startTime = System.currentTimeMillis();

        // Paso 1. Ingresar al formulario de creacion de animal
        driver.get(BASE_URL + "/admin/animales/crear");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paso 2. Llenar todos los campos requeridos
        driver.findElement(By.name("nombre")).sendKeys("Simba");
        driver.findElement(By.name("procedencia")).sendKeys("Sabana");

        try {
            Select especieSelect = new Select(driver.findElement(By.name("especie_id")));
            especieSelect.selectByIndex(1);
        } catch (Exception e) {
            driver.findElement(By.name("especie_id")).sendKeys("1");
        }

        try {
            Select habitatSelect = new Select(driver.findElement(By.name("habitat_id")));
            habitatSelect.selectByIndex(1);
        } catch (Exception e) {
            driver.findElement(By.name("habitat_id")).sendKeys("1");
        }

        driver.findElement(By.name("descripcion")).sendKeys("Un leon majestuoso de la sabana africana");

        if (driver.findElements(By.name("genero")).size() > 0) {
            driver.findElement(By.name("genero")).sendKeys("Macho");
        }

        // Paso 3. Enviar el formulario
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        submitBtn.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar resultado: redireccion o toast de exito
        String currentUrl = driver.getCurrentUrl();
        boolean exito = !currentUrl.contains("crear")
                || driver.findElements(By.cssSelector(".toast-success, .alert-success")).size() > 0;

        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("URL actual: " + currentUrl);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(exito, "El animal debe crearse exitosamente");
    }
}
