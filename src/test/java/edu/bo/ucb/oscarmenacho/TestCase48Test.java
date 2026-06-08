package edu.bo.ucb.oscarmenacho;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Historia de Usuario: Como Administrador quiero crear un nuevo Tipo de Producto para clasificar
 *                      correctamente los productos del inventario.
 * 
 * Prueba de Aceptación (Caso de Prueba 48): Verificar la creación de un nuevo 'Tipo de Producto'.
 * 
 * Modulo: Gestión de Inventario
 * 
 * Pre-Condiciones:
 *      - Estar autenticado como Administrador.
 * 
 * Pasos para la Ejecución de la Prueba:
 *      1. Hacer clic en el icono de perfil en el header y seleccionar "Panel de Administración".
 *      2. Pulsar el botón de menu desplegable a la derecha del nombre de usuario y seleccionar 'Gestión Inventario'.
 *      3. En el menu de navegación lateral seleccionar 'Lista de tipos'.
 *      4. Hacer clic en 'Nuevo Tipo' e ingresar una categoría (ej: "Suplementos Medicos") y su descripción detallada.
 *      5. Presionar 'Guardar'.
 * 
 * Resultado Esperado: La nueva categoría aparece en la lista y queda disponible en el formulario de creación de productos.
 */

// * Para ejecutar en la linea de comando: mvn clean compile test -Dtest=TestCase48Test

public class TestCase48Test {

    private WebDriver driver;

    @BeforeClass
    public void setDriver() throws Exception {

        String path = "D:\\Users\\usuario\\chromedriver-win64\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", path);

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }

    @AfterClass
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    @SuppressWarnings("CallToPrintStackTrace")
    public void testToVerifyNewProductTypeCreation() {

        //********** 1. Preparación de la Prueba **********//

        // Nombre y descripción detallada de la nueva categoría que se va a crear
        String newCategoryName = "Suplementos Medicos " + System.currentTimeMillis();
        String newCategoryDescription = "Productos diseñados para complementar la dieta y apoyar la salud general.";

        // Paso 1. Ingresar a la pagina principal de la aplicación
        String appUrl = "http://localhost:4200";
        
        driver.get(appUrl);

        // Esperamos a que se cargue la pagina principal
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina principal cargada...");

        // Iniciar sesión como Administrador
        WebElement loginButton = driver.findElement(By.xpath("//zoo-header//span[text()='Iniciar Sesión']/ancestor::button"));
        loginButton.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina de Inicio de Sesión cargada...");

        WebElement adminUserInput = driver.findElement(By.xpath("//*[@id=\"Usuario\"]"));
        adminUserInput.clear();
        adminUserInput.sendKeys("admin@zconnect.com");

        WebElement adminPasswordInput = driver.findElement(By.xpath("//*[@id=\"Contraseña\"]/input"));
        adminPasswordInput.clear();
        adminPasswordInput.sendKeys("admin123");

        WebElement adminLoginButton = driver.findElement(By.xpath("/html/body/app-root/app-login/div/p-card/div/div/div/div[2]/div[2]/form/div[2]/p-button/button"));
        adminLoginButton.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina principal de Administrador cargada...");

        // Hacer clic en el icono de perfil en el header
        WebElement profileIcon = driver.findElement(By.xpath("/html/body/app-root/zoo-layout/zoo-header/header/div[2]/div[2]/zoo-profile-button/div/p-button/button"));
        profileIcon.click();

        // Esperamos a que se despliegue el menu de perfil
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Seleccionar la opción "Panel de Administración"
        WebElement adminPanelOption = driver.findElement(By.xpath("//span[text()='Panel de Administración']"));
        adminPanelOption.click();

        // Esperamos a que se cargue el Dashboard administrativo
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Dashboard administrativo cargado...");

        //********** 2. Lógica de la Prueba **********//

        // Paso 2. Pulsar el botón de menu desplegable a la derecha del nombre de usuario y seleccionar 'Gestión Inventario'
        WebElement dropdownMenu = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/zoo-header/header/div[2]/div[2]/p-button/button"));
        dropdownMenu.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de Panel de Administrador desplegado...");

        WebElement inventoryManagementOption = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/p-drawer/div/div[2]/zoo-sidebar-admin-menu/ul/li[4]"));
        inventoryManagementOption.click();

        WebElement closeDropdownMenu = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/p-drawer/div/div[1]/p-button/button"));
        closeDropdownMenu.click();

        // Esperamos a que se muestre la lista de productos del inventario
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Modulo de Gestión de Inventario cargado...");

        // Paso 3. En el menu de navegación lateral seleccionar 'Lista de tipos'
        WebElement typesListLink = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[1]/div/nav/div/app-nav-menu-gestion/div/p-button[5]/button"));
        typesListLink.click();

        // Esperamos a que se cargue la lista de tipos de productos
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lista de tipos de productos cargada...");

        // Paso 4. Hacer clic en 'Nuevo Tipo' e ingresar la nueva categoría y su descripción detallada
        WebElement newTypeButton = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[3]/div/p-scrollpanel/div[1]/div/div/div/zoo-main-container/div/app-lista-tipos/div/div/div/p-button[2]/button"));
        newTypeButton.click();

        // Esperamos a que se muestre el formulario para agregar un nuevo tipo
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Formulario para nuevo tipo de producto abierto...");

        // Ingresar el nombre de la nueva categoría en el campo de texto
        WebElement categoryNameInput = driver.findElement(By.xpath("//*[@id=\"nombre\"]"));
        categoryNameInput.clear();
        categoryNameInput.sendKeys(newCategoryName);

        System.out.println("Nombre de la Nueva Categoría ingresada: " + newCategoryName);

        // Ingresar la descripción detallada de la nueva categoría en el campo de texto
        WebElement categoryDescriptionInput = driver.findElement(By.xpath("//*[@id=\"descripcion\"]"));
        categoryDescriptionInput.clear();
        categoryDescriptionInput.sendKeys(newCategoryDescription);

        System.out.println("Descripción de la Nueva Categoría ingresada: " + newCategoryDescription);

        // Paso 5. Presionar 'Guardar'
        WebElement saveButton = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[3]/div/p-scrollpanel/div[1]/div/div/div/zoo-main-container/div/app-crear-tipo/div/p-card/div[2]/div/form/div[2]/p-button[2]/button"));
        saveButton.click();

        // Esperamos a que el sistema procese y actualice la lista de tipos
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tipo de producto guardado, verificando resultado...");

        //********** 3. Verificación de la situación esperada - Assert **********//

        // Se espera que aparezca un mensaje de éxito o que la nueva categoría aparezca en la lista de tipos de productos, indicando que la creación fue exitosa.

        // Verificar mensaje de éxito tras guardar la nueva categoría
        WebElement messageTitle = driver.findElement(By.xpath("//p-toastitem/div/div/div[1]/div[1]"));
        String messageTitleText = messageTitle.getText();
        WebElement successMessage = driver.findElement(By.xpath("//p-toastitem/div/div/div[1]/div[2]"));
        String successMessageText = successMessage.getText();
        System.out.println("Mensaje mostrado: " + messageTitleText + ". " + successMessageText);

        // Verificar que el mensaje de éxito sea visible
        Assert.assertTrue(messageTitle.isDisplayed(), "El titulo de éxito no se muestra tras guardar la nueva categoría.");
        Assert.assertTrue(successMessage.isDisplayed(), "El mensaje de éxito no se muestra tras guardar la nueva categoría.");

        // Verificar que la nueva categoría aparece en la lista de tipos de productos

        // Buscar el elemento que contiene el nombre de la nueva categoría en la lista
        WebElement newTypeInList = driver.findElement(By.xpath("//h3[text()='" + newCategoryName + "']"));
        String typeText = newTypeInList.getText();
        System.out.println("Tipo encontrado en la lista: " + typeText);

        // Buscar el elemento que contiene la descripción detallada de la nueva categoría en la lista
        WebElement newTypeDescriptionInList = driver.findElement(By.xpath("//h3[text()='" + newCategoryName + "']/ancestor::zoo-tipo-item//p[@class='descripcion']"));

        // Scroll al elemento para forzar render y luego leer el texto
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", newTypeDescriptionInList);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String typeDescriptionText = newTypeDescriptionInList.getText();
        System.out.println("Descripción encontrada en la lista: " + typeDescriptionText);

        // Verificar que el nombre y la descripción detallada de la nueva categoría es visible en la lista
        Assert.assertTrue(newTypeInList.isDisplayed(), "El nuevo tipo de producto no es visible en la lista.");
        Assert.assertTrue(newTypeDescriptionInList.isDisplayed(), "La descripción de la nueva categoría no es visible en la lista.");

        // Verificar que el nombre y la descripción detallada de la categoría creada coincide con el ingresado
        Assert.assertEquals(typeText, newCategoryName, "La nueva categoría no aparece en la lista con el nombre correcto.");
        Assert.assertEquals(typeDescriptionText, newCategoryDescription, "La descripción de la nueva categoría no aparece en la lista con el texto correcto.");

        System.out.println("Verificación completada: La nueva categoría '" + newCategoryName + "' y su descripción detallada '" + newCategoryDescription + "' aparecen correctamente en la lista de tipos de productos.");
    }
}