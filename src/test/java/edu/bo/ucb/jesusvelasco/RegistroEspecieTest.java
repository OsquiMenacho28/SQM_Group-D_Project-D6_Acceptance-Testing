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
// Historia de Usuario: Como administrador del zoologico quiero registrar una nueva especie
// con su clasificacion taxonomica completa para catalogar los animales del zoologico
//
// Prueba de Aceptacion: Verificar que una especie con datos taxonomicos completos
// (nombre cientifico, nombre comun, filo, clase, orden, familia) se registre exitosamente
//
// Paso 1. Ingresar al formulario de creacion de especie en /admin/especies/crear
// Paso 2. Llenar todos los campos taxonomicos requeridos
// Paso 3. Enviar el formulario y esperar la confirmacion
//
// Resultado Esperado: La especie se crea exitosamente y se muestra un mensaje de confirmacion
/****************************************/

public class RegistroEspecieTest {

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
    public void registroEspecieConDatosTaxonomicos() {
        long startTime = System.currentTimeMillis();

        // Paso 1. Ingresar al formulario de creacion de especie
        driver.get(BASE_URL + "/admin/especies/crear");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paso 2. Llenar todos los campos taxonomicos requeridos
        driver.findElement(By.name("nombreCientifico")).sendKeys("Panthera leo");
        driver.findElement(By.name("nombreComun")).sendKeys("Leon");
        driver.findElement(By.name("filo")).sendKeys("Chordata");
        driver.findElement(By.name("clase")).sendKeys("Mammalia");
        driver.findElement(By.name("orden")).sendKeys("Carnivora");
        driver.findElement(By.name("familia")).sendKeys("Felidae");
        driver.findElement(By.name("descripcion")).sendKeys("Gran felino africano cazador");

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

        Assert.assertTrue(exito, "La especie debe crearse exitosamente");
    }
}
