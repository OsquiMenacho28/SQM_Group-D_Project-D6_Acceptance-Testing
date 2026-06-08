package edu.bo.ucb.manueldelgadillo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como veterinario quiero finalizar un historial clínico abierto
// para cerrar el ciclo de atención del paciente.
//
// Prueba de Aceptación / Caso de Prueba CP45: Verificar el cierre del ciclo clínico.
//
// PASO 1. Iniciar sesión como veterinario en ZooConnect.
// PASO 2. Crear un historial clínico y abrir su detalle en estado "En Curso".
// PASO 3. Presionar el botón Finalizar y confirmar en el diálogo.
//
// Resultado Esperado: El historial cambia a estado "Cerrado", se muestra el mensaje
// de consulta finalizada y ya no se permiten nuevas recetas.
/****************************************/

// mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp45CerrarHistorialTest

public class Cp45CerrarHistorialTest extends BaseTest {

    @Test
    public void cerrarHistorialClinicoTest() {
        /********** Preparación de la prueba **********/
        loginComoVeterinario();
        irAlPanelVeterinario();
        abrirHistorialesClinicos();

        WebDriverWait webDriverWait = wait(20);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Nuevo Historial')]"))).click();
        esperarFormularioHistorialCargado();

        seleccionarPrimeraOpcionPrimeNg("#animal");
        seleccionarPrimeraOpcionPrimeNg("#tipo");

        abrirAcordeon("Diagnósticos Iniciales");

        WebElement diagnostico = webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[formcontrolname='diagnostico_presuntivo']")));
        diagnostico.clear();
        diagnostico.sendKeys("Control ATDD cierre historial");

        driver.findElement(By.xpath("//button[contains(.,'Guardar Historial')]")).click();
        esperar(5);

        WebElement fila = webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//tr[contains(.,'En Curso')]")));
        fila.findElement(By.cssSelector("button .pi-chevron-right, .pi-chevron-right")).click();
        esperar(3);

        /*********** Lógica de la prueba ***********/
        WebElement estadoEnCurso = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'status-badge') and contains(.,'En Curso')]")));
        Assert.assertTrue(estadoEnCurso.isDisplayed());

        driver.findElement(By.xpath("//button[contains(.,'Finalizar')]")).click();
        esperar(1);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'¿Estás seguro de finalizar esta consulta?')]")));

        driver.findElement(By.xpath("//button[contains(.,'Aceptar')]")).click();
        esperar(3);

        /************ Verificación de la situación esperada - Assert ***************/
        WebElement mensajeFinalizado = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Consulta Finalizada')]")));
        Assert.assertTrue(mensajeFinalizado.isDisplayed());

        WebElement estadoCerrado = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".status-badge.closed")));
        Assert.assertTrue(estadoCerrado.getText().contains("Cerrado"));

        WebElement agregarMedicamento = driver.findElement(
                By.xpath("//button[contains(.,'Agregar Medicamento')]"));
        Assert.assertFalse(agregarMedicamento.isEnabled());
    }
}
