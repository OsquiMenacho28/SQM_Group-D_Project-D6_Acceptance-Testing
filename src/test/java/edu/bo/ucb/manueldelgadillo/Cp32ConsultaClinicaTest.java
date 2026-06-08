package edu.bo.ucb.manueldelgadillo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como veterinario quiero registrar un historial clínico
// para documentar la atención médica de un animal.
//
// Prueba de Aceptación / Caso de Prueba CP32: Verificar que el veterinario puede
// registrar una consulta clínica.
//
// PASO 1. Iniciar sesión como veterinario en ZooConnect.
// PASO 2. Ingresar al módulo de Historiales Clínicos y abrir el formulario Nuevo Historial.
// PASO 3. Completar paciente, tipo de atención, constantes vitales y diagnóstico presuntivo.
// PASO 4. Guardar el historial clínico.
//
// Resultado Esperado: El historial queda registrado y aparece en la lista con estado "En Curso".
/****************************************/

// mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp32ConsultaClinicaTest

public class Cp32ConsultaClinicaTest extends BaseTest {

    @Test
    public void registrarConsultaClinicaTest() {
        /********** Preparación de la prueba **********/
        loginComoVeterinario();
        irAlPanelVeterinario();
        abrirHistorialesClinicos();

        WebDriverWait webDriverWait = wait(20);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Nuevo Historial')]"))).click();
        esperarFormularioHistorialCargado();

        /*********** Lógica de la prueba ***********/
        seleccionarPrimeraOpcionPrimeNg("#animal");
        seleccionarPrimeraOpcionPrimeNg("#tipo");

        WebElement peso = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#peso input")));
        peso.clear();
        peso.sendKeys("120");

        WebElement temperatura = driver.findElement(By.cssSelector("#temp input"));
        temperatura.clear();
        temperatura.sendKeys("38.5");

        abrirAcordeon("Diagnósticos Iniciales");

        WebElement diagnostico = webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[formcontrolname='diagnostico_presuntivo']")));
        diagnostico.clear();
        diagnostico.sendKeys("Anemia leve (ATDD)");

        driver.findElement(By.xpath("//button[contains(.,'Guardar Historial')]")).click();
        esperar(5);

        /************ Verificación de la situación esperada - Assert ***************/
        Assert.assertTrue(driver.getCurrentUrl().contains("/vet/historiales/lista"));

        WebElement filaEnCurso = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[contains(.,'En Curso')]")));
        Assert.assertTrue(filaEnCurso.isDisplayed());

        WebElement botonNuevo = driver.findElement(By.xpath("//button[contains(.,'Nuevo Historial')]"));
        Assert.assertTrue(botonNuevo.isDisplayed());
    }
}
