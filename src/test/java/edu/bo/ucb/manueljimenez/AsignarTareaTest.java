package edu.bo.ucb.manueljimenez;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero asignar una tarea
// a un cuidador especifico desde la bandeja de entrada
//
// Prueba de Aceptacion: Verificar que una tarea pendiente se asigne exitosamente
// a un cuidador y aparezca en la seccion de asignadas hoy
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Navegar al tablero de operaciones
// Paso 3. Identificar la tarea creada previamente en la bandeja de entrada
// Paso 4. Hacer clic en Asignar y seleccionar un cuidador
// Paso 5. Confirmar la asignacion
//
//      Resultado Esperado: La tarea aparece en la seccion de tareas asignadas hoy
//
// PRECONDICION: Debe existir al menos un usuario con rol de cuidador en el sistema
/****************************************/

public class AsignarTareaTest extends BaseTest {

    @Test(priority = 2)
    public void asignarTareaACuidador() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // Paso 1. La sesion ya se inicio en BaseTest.setUp()

        // Paso 2. Navegar hasta el modulo de Gestion de Tareas
        navigateToGestionTareas();

        // Paso 3. Abrir el tablero de operaciones
        driver.findElement(
            By.xpath("//app-nav-menu-gestion//span[text()='Tablero de Operaciones']/ancestor::button")
        ).click();
        sleep();

        // Paso 4. Crear una tarea manual para asignar
        driver.findElement(
            By.xpath("//span[text()='Crear Tarea Manual']/ancestor::button")
        ).click();
        sleep();

        String tituloTarea = "Tarea para asignar " + ts;
        driver.findElement(By.id("titulo")).sendKeys(tituloTarea);
        sleep();

        driver.findElement(By.id("desc")).sendKeys("Tarea creada para probar el flujo de asignacion");
        sleep();

        driver.findElement(By.id("tipo")).click();
        sleep();
        driver.findElement(By.xpath("//li[@role='option'][contains(., 'Alimentacion')]")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Crear Tarea']/ancestor::button")).click();

        waitForInvisible(By.cssSelector(".p-dialog-mask"));
        sleep();

        // Refrescar la bandeja de entrada
        driver.findElement(By.xpath("//span[text()='Actualizar']/ancestor::button")).click();

        // Paso 5. Buscar la tarea en la bandeja de entrada y hacer clic en Asignar
        waitForVisible(By.xpath("//h4[contains(@class, 'task-title') and contains(., '" + tituloTarea + "')]"));
        WebElement taskCard = driver.findElement(
            By.xpath("//h4[contains(@class, 'task-title') and contains(., '" + tituloTarea + "')]/ancestor::div[contains(@class, 'task-card')]")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", taskCard);
        sleep();

        taskCard.findElement(By.xpath(".//button[contains(@class, 'assign-btn')]")).click();
        sleep();

        // Paso 6. Seleccionar un cuidador en el dialogo de asignacion
        driver.findElement(By.id("caretaker")).click();
        waitForVisible(By.cssSelector("li[role='option']:not(.p-select-empty-message)"));
        sleep();
        driver.findElement(By.cssSelector("li[role='option']:not(.p-select-empty-message)")).click();
        sleep();

        // Paso 7. Confirmar la asignacion
        driver.findElement(By.xpath("//span[text()='Confirmar']/ancestor::button")).click();

        waitForInvisible(By.cssSelector(".p-dialog-mask"));
        sleep();

        // Scroll al tope para que el botón Actualizar sea visible
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
        sleep();

        // Refrescar el tablero
        driver.findElement(By.xpath("//span[text()='Actualizar']/ancestor::button")).click();
        waitForVisible(By.xpath("//section[contains(@class, 'radar-panel')]//*[contains(., '" + tituloTarea + "')]"));

        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Tarea asignada: " + tituloTarea);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");
    }
}
