package edu.bo.ucb.jesusvelasco;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero registrar un nuevo habitat
// con sus condiciones climaticas para asignar animales a entornos adecuados
//
// Prueba de Aceptacion: Verificar que un habitat con datos completos
// (nombre, tipo, descripcion, condiciones climaticas) se registre exitosamente
//
// Paso 1. Ingresar al formulario de creacion de habitat en /admin/habitats/crear
// Paso 2. Llenar todos los campos requeridos del habitat
// Paso 3. Enviar el formulario y esperar la confirmacion
//
// Resultado Esperado: El habitat se crea exitosamente y se muestra un mensaje de exito
/****************************************/

public class RegistroHabitatTest {

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
    public void registroHabitatConDatosCompletos() {
        long startTime = System.currentTimeMillis();

        // Paso 1. Ingresar al formulario de creacion de habitat
        driver.get(BASE_URL + "/admin/habitats/crear");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paso 2. Llenar todos los campos requeridos
        driver.findElement(By.name("nombre")).sendKeys("Sabana Africana");
        driver.findElement(By.name("tipo")).sendKeys("Tropical");
        driver.findElement(By.name("descripcion")).sendKeys("Sabana africana con clima calido y vegetacion dispersa");
        driver.findElement(By.name("condicionesClimaticas")).sendKeys("Calido y seco");

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

        Assert.assertTrue(exito, "El habitat debe crearse exitosamente");
    }
}
