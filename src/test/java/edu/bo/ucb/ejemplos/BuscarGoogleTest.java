package edu.bo.ucb.ejemplos;

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
// Historia de Usuario: Como usuario quiero verificar que el boton "Buscar con Google" se despliega
//
// Prueba de Aceptacion: Verificar que el boton de busqueda tenga el texto "Buscar con Google"
//
// Paso 1. Ingresar a la pagina de Google: https://www.google.com
// Paso 2. Buscar el boton "Buscar con Google"
//
// Resultado Esperado: El boton "Buscar con Google" debe estar presente y ser visible
/****************************************/

public class BuscarGoogleTest {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void paginaPrincipalGoogle() {
        // Paso 1. Ingresar a la pagina de Google
        driver.get("https://www.google.com");

        // Paso 2. Buscar el boton "Buscar con Google"
        WebElement boton = driver.findElement(By.name("btnK"));

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String txtBoton = boton.getAttribute("value");
        System.out.println("Texto del boton:: " + txtBoton);

        Assert.assertEquals(txtBoton, "Buscar con Google");
    }
}
