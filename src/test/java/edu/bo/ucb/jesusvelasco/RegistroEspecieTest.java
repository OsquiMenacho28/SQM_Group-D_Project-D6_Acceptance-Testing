package edu.bo.ucb.jesusvelasco;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import edu.bo.ucb.jesusvelasco.BaseTest;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero registrar una nueva especie
// con su clasificacion taxonomica completa para catalogar los animales del zoologico
//
// Prueba de Aceptacion: Verificar que una especie con datos taxonomicos completos
// (nombre cientifico, nombre comun, filo, clase, orden, familia) se registre exitosamente
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Ingresar al formulario de creacion de especie
// Paso 3. Llenar todos los campos taxonomicos requeridos
// Paso 4. Enviar el formulario y esperar la confirmacion
//
        // Resultado Esperado: La especie se crea exitosamente y se muestra un mensaje de confirmacion
/****************************************/

public class RegistroEspecieTest extends BaseTest {

    @Test(priority = 1)
    public void registroEspecieConDatosTaxonomicos() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // Paso 1. La sesion ya se inicio en BaseTest.setUp()

        // Paso 2. Navegar por la UI hasta el formulario de creacion de especie
        driver.findElement(By.cssSelector("zoo-profile-button button")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Panel de Administraci\u00f3n']/ancestor::a")).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gesti\u00f3n de Animales']/ancestor::li")
        ).click();
        sleep();

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir Especie']/ancestor::button")
        ).click();
        sleep();

        // Paso 3. Llenar todos los campos taxonomicos requeridos
        String nombreComun = "Leon " + ts;
        createdEspecieNombre = nombreComun;

        driver.findElement(By.id("nombreCientifico")).sendKeys("Panthera leo " + ts);
        driver.findElement(By.id("nombreComun")).sendKeys(nombreComun);
        driver.findElement(By.id("filo")).sendKeys("Chordata");
        driver.findElement(By.id("clase")).sendKeys("Mammalia");
        driver.findElement(By.id("orden")).sendKeys("Carnivora");
        driver.findElement(By.id("familia")).sendKeys("Felidae");
        driver.findElement(By.id("descripcion")).sendKeys("Gran felino africano cazador");

        // Paso 4. Enviar el formulario
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep();

        // Paso 5. Verificar en la lista de especies
        driver.findElement(
            By.xpath("//span[text()='Lista de Especies']/ancestor::button")
        ).click();
        sleep();

        String body = driver.findElement(By.cssSelector(".p-dataview-content")).getText();
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Especie creada: " + nombreComun);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(body.contains(nombreComun), "La especie '" + nombreComun + "' debe aparecer en la lista");
    }
}
