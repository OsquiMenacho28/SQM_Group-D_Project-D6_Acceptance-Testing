package edu.bo.ucb.manueldelgadillo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como veterinario quiero registrar nuevos tipos de atención
// para clasificar las consultas clínicas del zoológico.
//
// Prueba de Aceptación / Caso de Prueba CP31: Verificar la creación de una nueva
// categoría de Tipo de Atención.
//
// PASO 1. Iniciar sesión como veterinario en ZooConnect.
// PASO 2. Ingresar al Panel Veterinario y abrir la configuración de Tipos de Atención.
// PASO 3. Crear un nuevo tipo con nombre y descripción, y guardar.
//
// Resultado Esperado: El sistema muestra el mensaje de éxito y el nuevo tipo aparece
// en la tabla de tipos de atención.
/****************************************/

// mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp31TipoAtencionTest

public class Cp31TipoAtencionTest extends BaseTest {

    @Test
    public void crearTipoAtencionTest() {
        String nombreTipo = "Tratamiento Ortopédico ATDD " + System.currentTimeMillis();

        /********** Preparación de la prueba **********/
        loginComoVeterinario();
        irAlPanelVeterinario();
        abrirHistorialesClinicos();

        WebDriverWait webDriverWait = wait(20);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Tipos de Atención')]"))).click();
        esperar(2);

        /*********** Lógica de la prueba ***********/
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Nuevo')]"))).click();
        esperar(1);

        WebElement nombre = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nombre")));
        nombre.clear();
        nombre.sendKeys(nombreTipo);

        WebElement descripcion = driver.findElement(By.id("descripcion"));
        descripcion.clear();
        descripcion.sendKeys("Atención especializada en huesos y articulaciones (ATDD)");

        driver.findElement(By.xpath("//div[contains(@class,'p-dialog')]//button[contains(.,'Guardar')]")).click();
        esperar(3);

        /************ Verificación de la situación esperada - Assert ***************/
        WebElement mensajeExito = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Tipo de atención creado')]")));
        Assert.assertTrue(mensajeExito.isDisplayed());

        WebElement celda = driver.findElement(By.xpath("//td[contains(.,'" + nombreTipo + "')]"));
        Assert.assertTrue(celda.isDisplayed());
    }
}
