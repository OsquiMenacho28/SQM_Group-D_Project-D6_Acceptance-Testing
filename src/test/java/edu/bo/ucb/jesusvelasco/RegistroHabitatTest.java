package edu.bo.ucb.jesusvelasco;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import edu.bo.ucb.jesusvelasco.BaseTest;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero registrar un nuevo habitat
// con sus condiciones climaticas para asignar animales a entornos adecuados
//
// Prueba de Aceptacion: Verificar que un habitat con datos completos
// (nombre, tipo, descripcion, condiciones climaticas) se registre exitosamente
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Ingresar al formulario de creacion de habitat
// Paso 3. Llenar todos los campos requeridos del habitat
// Paso 4. Enviar el formulario y esperar la confirmacion
//
        // Resultado Esperado: El habitat se crea exitosamente y se muestra un mensaje de exito
/****************************************/

public class RegistroHabitatTest extends BaseTest {

    @Test(priority = 2, groups = {"setup"})
    public void registroHabitatConDatosCompletos(ITestContext context) {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // Paso 1. La sesion ya se inicio en BaseTest.setUp()

        // Paso 2. Navegar por la UI hasta el formulario de creacion de habitat
        driver.findElement(By.xpath("//zoo-profile-button//button")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Panel de Administraci\u00f3n']/ancestor::a")).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gesti\u00f3n de Animales']/ancestor::li")
        ).click();
        sleep();

        driver.findElement(By.xpath("//button[.//span[contains(@class, 'pi-times')]]")).click();
        sleep();

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir H\u00e1bitat']/ancestor::button")
        ).click();
        sleep();

        // Paso 3. Llenar todos los campos requeridos
        String nombreHabitat = "Sabana " + ts;
        context.getSuite().setAttribute("habitatNombre", nombreHabitat);

        driver.findElement(By.id("nombre")).sendKeys(nombreHabitat);
        driver.findElement(By.id("tipo")).sendKeys("Tropical");
        driver.findElement(By.id("descripcion")).sendKeys("Sabana africana con clima calido y vegetacion dispersa");
        driver.findElement(By.id("condicionesClimaticas")).sendKeys("Calido y seco");

        // Paso 4. Enviar el formulario (step 1 -> "Crear y Continuar")
        driver.findElement(By.xpath("//span[text()='Crear y Continuar']/ancestor::button")).click();
        sleep();

        // Paso 5. Verificar en la lista de habitats
        driver.findElement(
            By.xpath("//span[text()='Lista de H\u00e1bitats']/ancestor::button")
        ).click();
        sleep();

        String body = driver.findElement(By.xpath("//zoo-lista-habitats/div/p-dataview/div[2]")).getText();
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Habitat creado: " + nombreHabitat);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(body.contains(nombreHabitat), "El habitat '" + nombreHabitat + "' debe aparecer en la lista");
    }
}
