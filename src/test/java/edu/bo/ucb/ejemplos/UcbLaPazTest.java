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
// Historia de Usuario: Como usuario quiero verificar que el boton "Ingenieria de Sistemas" se despliega
//
// Prueba de Aceptacion: Verificar que el boton de "Ingenieria de Sistemas" este presente en la pagina de la UCB
//
// Paso 1. Ingresar a la pagina de la UCB: https://lpz.ucb.edu.bo/
// Paso 2. Buscar el boton "Ingenieria de Sistemas"
//
// Resultado Esperado: El boton "Buscar con Google" debe estar presente y ser visible
/****************************************/

public class UcbLaPazTest {

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
    public void paginaPrincipalUCB() {
        // Paso 1. Ingresar a la pagina de UCB
        driver.get("https://lpz.ucb.edu.bo/");

        // Paso 2. Buscar el boton "Ingenieria de Sistemas"

        /* Capturar el boton "Ingenieria de Sistemas" */
        WebElement linkSistemas = driver.findElement(By.xpath("//*[@id=\"post-227475\"]/div/div/div/div[6]/div[2]/div[2]/div[5]/div/h4/a"));

        String txtBoton = linkSistemas.getText();
        System.out.println("Texto del boton:: " + txtBoton);

        linkSistemas.click();
        System.out.println("Haciendo click...  ");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(txtBoton, "Ingeniería de Sistemas");
    }
}
