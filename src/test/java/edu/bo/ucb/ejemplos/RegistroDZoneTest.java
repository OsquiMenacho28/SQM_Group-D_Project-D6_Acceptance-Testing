package edu.bo.ucb.ejemplos;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como usuario nuevo quiero registrar mis datos en Dzone.com
//
// Prueba de Aceptacion: Verificar que se muestren alertas para los campos obligatorios
//
// 1. Ingresar a la pagina de DZone  www.dzone.com
// 2. Hacer en el link Join
// 3. Presionar el boton Join
//
// Resultado Esperado: Se deben mostrar mensajes de alerta para los campos obligatorios que no fueron llenados
/****************************************/

public class RegistroDZoneTest {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void verificarMensajeErrorAlRegistrar() {
        // PASO 1. Ingresar a la pagina de DZone
        driver.get("https://dzone.com");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Esperando a la pagina.....");

        // PASO 2. Hacer en el link Join
        WebElement joinLink = driver.findElement(By.xpath("//*[@id=\"unauthenticated-block\"]/div[2]/a[2]"));
        joinLink.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // PASO 3. Presionar el boton Join
        WebElement joinButton = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div[2]/div[3]/button"));

        joinButton.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }
}
