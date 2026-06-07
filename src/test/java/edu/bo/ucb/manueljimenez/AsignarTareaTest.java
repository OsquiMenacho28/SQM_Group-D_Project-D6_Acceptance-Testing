package edu.bo.ucb.manueljimenez;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/******************************************************************************
 * Historia de Usuario: Como administrador del zoológico quiero asignar una tarea
 * a un cuidador específico desde la bandeja de entrada, con un flujo de 
 * creación y asignación pausado y seguro.
 ******************************************************************************/
public class AsignarTareaTest extends BaseTest {

    // Método auxiliar para escribir como humano (evita errores de renderizado de JS)
    private void escribirLento(By locator, String texto) {
        WebElement element = driver.findElement(locator);
        for (char c : texto.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }

    @Test(priority = 2, description = "Verificar la asignación exitosa de una tarea con flujo pausado")
    public void asignarTareaACuidador() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);
        String tituloTarea = "Tarea " + ts;

        // Paso 1. Inicio de sesión realizado por BaseTest.setUp()

        // Paso 2. Navegar hasta el módulo de Gestión de Tareas
        navigateToGestionTareas();

        // Paso 3. Abrir el tablero de operaciones
        By btnTablero = By.xpath("//app-nav-menu-gestion//span[text()='Tablero de Operaciones']/ancestor::button");
        waitForVisible(btnTablero);
        driver.findElement(btnTablero).click();
        
        // Pausa breve para permitir la carga del tablero
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Paso 4. Crear una tarea manual con ritmo normal
        By btnCrearManual = By.xpath("//span[text()='Crear Tarea Manual']/ancestor::button");
        waitForVisible(btnCrearManual);
        driver.findElement(btnCrearManual).click();

        // Llenar el formulario con calma
        By txtTitulo = By.id("titulo");
        waitForVisible(txtTitulo); 
        escribirLento(txtTitulo, tituloTarea); // <-- Escritura humana
        
        try { Thread.sleep(300); } catch (InterruptedException e) {}

        By txtDesc = By.id("desc");
        escribirLento(txtDesc, "Tarea creada paso a paso para el flujo de asignacion");

        // Selección de tipo de tarea
        By ddlTipo = By.id("tipo");
        waitForVisible(ddlTipo); 
        driver.findElement(ddlTipo).click();
        
        try { Thread.sleep(400); } catch (InterruptedException e) {} // Pausa antes de seleccionar

        By optAlimentacion = By.xpath("//li[@role='option'][contains(., 'Alimentacion')]");
        waitForVisible(optAlimentacion);
        driver.findElement(optAlimentacion).click();

        // Enviar formulario
        driver.findElement(By.xpath("//span[text()='Crear Tarea']/ancestor::button")).click();
        waitForInvisible(txtTitulo); // Esperamos a que cierre el modal

        // Sincronización: Actualizar bandeja
        By btnActualizar = By.xpath("//span[text()='Actualizar']/ancestor::button");
        waitForVisible(btnActualizar);
        driver.findElement(btnActualizar).click();
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {} // Esperar refresco de datos

        // Paso 5. Buscar y asignar
        By xpathTaskTitle = By.xpath("//h4[contains(@class, 'task-title') and contains(., '" + tituloTarea + "')]");
        WebElement taskTitleElement = waitForVisible(xpathTaskTitle);
        
        WebElement taskCard = taskTitleElement.findElement(By.xpath("./ancestor::div[contains(@class, 'task-card')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", taskCard);

        By btnAsignarEnCard = By.xpath(".//button[contains(@class, 'assign-btn')]");
        taskCard.findElement(btnAsignarEnCard).click();

// Paso 6. Seleccionar cuidador específico ("iv") usando el buscador
        String nombreCuidador = "iv"; // Definimos a quién buscamos
        
        By ddlCaretaker = By.id("caretaker");
        waitForVisible(ddlCaretaker);
        driver.findElement(ddlCaretaker).click();
        
        // --- AQUÍ ESTÁ EL CAMBIO: BUSCAR Y FILTRAR ---
        // Esperamos a que aparezca el input de búsqueda dentro del dropdown
        // (Ajusta este selector si el ID/Clase del input es diferente, pero suele ser un input dentro del panel)
        By searchInput = By.cssSelector("input[type='text']"); 
        waitForVisible(searchInput);
        
        // Escribimos el nombre del cuidador para filtrar la lista
        driver.findElement(searchInput).sendKeys(nombreCuidador);
        
        // Esperamos brevemente a que el filtro de PrimeNG actúe
        try { Thread.sleep(800); } catch (InterruptedException e) {} 
        
        // Buscamos específicamente la opción que contiene "iv"
        // Usamos un XPath que busca el texto exacto o contenido
        By optCaretaker = By.xpath("//li[contains(., '" + nombreCuidador + "')]");
        
        waitForVisible(optCaretaker);
        driver.findElement(optCaretaker).click();

    }
}