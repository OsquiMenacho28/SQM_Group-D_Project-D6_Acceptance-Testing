package edu.bo.ucb.manueljimenez;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero crear una rutina
// recurrente para automatizar tareas periodicas de cuidado animal
//
// Prueba de Aceptacion: Verificar que una rutina recurrente con datos completos
// (titulo, tipo, frecuencia) se cree exitosamente desde el planificador
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Navegar al modulo de Gestion de Tareas
// Paso 3. Abrir el planificador de rutinas
// Paso 4. Crear una nueva rutina con datos completos
// Paso 5. Guardar la rutina
//
//      Resultado Esperado: La rutina aparece en la tabla del planificador
/****************************************/

public class PlanificarRutinaTest extends BaseTest {

    @Test(priority = 3)
    public void planificarRutinaRecurrente() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // Paso 1. La sesion ya se inicio en BaseTest.setUp()

        // Paso 2. Navegar hasta el modulo de Gestion de Tareas
        navigateToGestionTareas();

        // Paso 3. Abrir el planificador de rutinas
        driver.findElement(
            By.xpath("//app-nav-menu-gestion//span[text()='Planificador de Rutinas']/ancestor::button")
        ).click();
        sleep();

        // Paso 4. Hacer clic en Nueva Rutina
        driver.findElement(
            By.xpath("//span[text()='Nueva Rutina']/ancestor::button")
        ).click();
        sleep();

        // Paso 5. Llenar el formulario de la rutina
        String tituloRutina = "Alimentacion matutina " + ts;
        driver.findElement(By.id("titulo")).sendKeys(tituloRutina);
        sleep();

        // Seleccionar tipo de tarea
        driver.findElement(By.id("tipo")).click();
        sleep();
        driver.findElement(By.xpath("//li[@role='option'][contains(., 'Alimentacion')]")).click();
        sleep();

        // La frecuencia default es "Diariamente", no se necesita cambiar

        driver.findElement(By.id("desc")).sendKeys("Rutina automatica de alimentacion para todos los animales");
        sleep();

        // Paso 6. Guardar la rutina
        driver.findElement(By.xpath("//span[text()='Guardar Rutina']/ancestor::button")).click();

        waitForVisible(By.cssSelector(".p-datatable-table-container"));
        sleep();

        // Paso 7. Verificar que la rutina aparece en la tabla del planificador
        String body = driver.findElement(By.cssSelector(".p-datatable-table-container")).getText();
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Rutina creada: " + tituloRutina);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(body.contains(tituloRutina), "La rutina '" + tituloRutina + "' debe aparecer en la tabla del planificador");
    }
}
