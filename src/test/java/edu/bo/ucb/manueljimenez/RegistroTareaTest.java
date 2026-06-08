package edu.bo.ucb.manueljimenez;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero crear una tarea manual
// para asignar actividades especificas al equipo de cuidadores
//
// Prueba de Aceptacion: Verificar que una tarea manual con datos completos
// (titulo, descripcion, tipo) se cree exitosamente desde el tablero de operaciones
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Navegar al modulo de Gestion de Tareas
// Paso 3. Abrir el formulario de creacion de tarea manual
// Paso 4. Llenar todos los campos requeridos
// Paso 5. Guardar la tarea
//
//      Resultado Esperado: La tarea aparece en la bandeja de entrada del tablero
/****************************************/

public class RegistroTareaTest extends BaseTest {

    @Test(priority = 1, groups = {"crear-tarea"})
    public void registroTareaManualConDatosCompletos() {
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

        // Paso 4. Abrir el formulario de creacion de tarea manual
        driver.findElement(
            By.xpath("//span[text()='Crear Tarea Manual']/ancestor::button")
        ).click();
        sleep();

        // Paso 5. Llenar todos los campos requeridos
        String tituloTarea = "Revision de recinto " + ts;
        createdTaskTitulo = tituloTarea;

        driver.findElement(By.id("titulo")).sendKeys(tituloTarea);
        sleep();

        driver.findElement(By.id("desc")).sendKeys("Inspeccionar el estado del recinto y reportar novedades");
        sleep();

        // Seleccionar tipo de tarea: Alimentacion
        driver.findElement(By.id("tipo")).click();
        sleep();
        driver.findElement(By.xpath("//li[@role='option'][contains(., 'Alimentacion')]")).click();
        sleep();

        // Paso 6. Guardar la tarea
        driver.findElement(By.xpath("//span[text()='Crear Tarea']/ancestor::button")).click();

        // Esperar a que el dialogo se cierre antes de interactuar con la pantalla
        waitForInvisible(By.cssSelector(".p-dialog-mask"));
        sleep();

        // Refrescar la bandeja de entrada
        driver.findElement(By.xpath("//span[text()='Actualizar']/ancestor::button")).click();
        waitForVisible(By.xpath("//h4[contains(@class, 'task-title') and contains(., '" + tituloTarea + "')]"));

        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Tarea creada: " + tituloTarea);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");
    }
}
