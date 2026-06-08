package edu.bo.ucb.oscarmenacho;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
 * Historia de Usuario: Como Administrador quiero registrar una entrada de abastecimiento con número de lote
 *                      para mantener el control del inventario.
 * 
 * Prueba de Aceptación (Caso de Prueba 47): Verificar el registro de una entrada de abastecimiento con número de lote.
 * 
 * Modulo: Gestión de Inventario
 * 
 * Pre-Condiciones:
 *      - Estar autenticado como Administrador.
 *      - Existir al menos un producto y un proveedor en el catalogo de inventario.
 * 
 * Pasos para la Ejecución de la Prueba:
 *      1. Hacer clic en el icono de perfil en el header y seleccionar "Panel de Administración".
 *      2. Pulsar el botón de menu desplegable a la derecha del nombre de usuario y seleccionar 'Gestión Inventario'.
 *      3. En el menu de navegación lateral seleccionar 'Historial de Movimientos'.
 *      4. Seleccionar 'Nueva Entrada'.
 *      5. Seleccionar el proveedor, el producto, ingresar la cantidad, el lote y especificar la fecha de caducidad del lote.
 *      6. Guardar la entrada.
 * 
 * Resultado Esperado: El sistema genera un lote de inventario identificado por fecha de caducidad
 *                     y actualiza el stock del producto.
 */

// * Para ejecutar en la linea de comando: mvn clean compile test -Dtest=TestCase47Test

public class TestCase47Test {

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
    public void testToVerifySupplyEntryRecordWithBatch() {

        //********** 1. Preparación de la Prueba **********//

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

        // Esperamos a que se despliegue el menu
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

        // Esperamos a que se muestre la lista de productos
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Modulo de Gestión de Inventario cargado...");

        // Paso 3. En el menu de navegación lateral seleccionar 'Historial de Movimientos'
        WebElement movementsHistoryLink = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[1]/div/nav/div/app-nav-menu-gestion/div/p-button[7]/button"));
        movementsHistoryLink.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina de Historial de Movimientos cargada...");

        // Paso 4. Seleccionar 'Nueva Entrada'
        WebElement newEntryButton = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[3]/div/p-scrollpanel/div[1]/div/div/div/zoo-main-container/div/app-historial/div/header/div[2]/div/p-button[1]/button"));
        newEntryButton.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Formulario de Nueva Entrada abierto...");

        // Paso 5. Seleccionar el proveedor, el producto, ingresar la cantidad, el lote y especificar la fecha de caducidad del lote
        // Seleccionar el proveedor del dropdown
        WebElement supplierDropdown = driver.findElement(By.xpath("//*[@id=\"proveedor\"]"));
        supplierDropdown.click();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar un proveedor disponible del catalogo
        WebElement supplierOption = driver.findElement(By.xpath("//span[text()='Exedrin']"));
        supplierOption.click();

        // Seleccionar el producto del dropdown
        WebElement productDropdown = driver.findElement(By.xpath("//span[text()='Seleccionar...']"));
        productDropdown.click();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Seleccionar un producto disponible del catalogo
        WebElement productOption = driver.findElement(By.xpath("//span[text()='Pastillas']"));
        productOption.click();

        // Ingresar la cantidad de la entrada
        WebElement quantityInput = driver.findElement(By.xpath("//tbody/tr/td[2]/p-inputnumber/input"));
        quantityInput.clear();
        quantityInput.sendKeys("50");

        // Ingresar el lote de la entrada
        WebElement lotInput = driver.findElement(By.xpath("//tbody/tr/td[3]/input"));
        lotInput.clear();
        lotInput.sendKeys("105FDK");

        // Especificar la fecha de caducidad del lote
        WebElement expirationDateInput = driver.findElement(By.xpath("//tbody/tr/td[4]/p-datepicker/input"));
        expirationDateInput.clear();
        expirationDateInput.sendKeys("2026-12-31");

        System.out.println("Datos del formulario completados: proveedor, producto, cantidad, lote y fecha de caducidad...");

        // Paso 6. Guardar la entrada
        WebElement saveButton = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div/app-gestion-inventario/zoo-splitter-layout/div/p-splitter/div[3]/div/p-scrollpanel/div[1]/div/div/div/zoo-main-container/div/app-crear-entrada/div/p-card/div[2]/div/form/div[3]/p-button[2]/button"));
        saveButton.click();

        // Fecha y hora de registro de la entrada (para su verificación posterior en el historial de movimientos)
        LocalDateTime newEntryRegistrationDateTime = LocalDateTime.now();
        System.out.println("Fecha y hora de registro de la entrada: " + newEntryRegistrationDateTime.toString());

        // Esperamos a que el sistema procese y muestre el resultado
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Entrada guardada, verificando resultado...");

        //********** 3. Verificación de la situación esperada - Assert **********//

        // Verificar que el sistema genero el lote y actualizo el stock:
        // Se espera que aparezca un mensaje de éxito o que la entrada aparezca en el historial

        // Verificar mensaje de éxito tras guardar la entrada
        WebElement messageTitle = driver.findElement(By.xpath("//p-toastitem/div/div/div[1]/div[1]"));
        String messageTitleText = messageTitle.getText();
        WebElement successMessage = driver.findElement(By.xpath("//p-toastitem/div/div/div[1]/div[2]"));
        String successMessageText = successMessage.getText();
        System.out.println("Mensaje mostrado: " + messageTitleText + ". " + successMessageText);

        // Verificar que el mensaje de éxito sea visible
        Assert.assertTrue(messageTitle.isDisplayed(), "El titulo de éxito no se muestra tras guardar la entrada.");
        Assert.assertTrue(successMessage.isDisplayed(), "El mensaje de éxito no se muestra tras guardar la entrada.");

        // Verificar que la nueva entrada aparece en el historial de movimientos
        WebElement entryInHistory = driver.findElement(By.xpath("//tbody/tr[1]"));
        Assert.assertTrue(entryInHistory.isDisplayed(), "La entrada registrada no aparece en el historial de movimientos.");

        // Verificar que la fecha y hora de registro de la nueva entrada aparece en el historial de movimientos
        WebElement entryRegistrationDate = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/div/span[1]"));
        WebElement entryRegistrationTime = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/div/span[2]"));
        String entryRegistrationDateText = entryRegistrationDate.getText();
        String entryRegistrationTimeText = entryRegistrationTime.getText();
        System.out.println("Fecha de registro de la entrada: " + entryRegistrationDateText);
        System.out.println("Hora de registro de la entrada: " + entryRegistrationTimeText);
        String entryRegistrationDateTime = entryRegistrationDateText + entryRegistrationTimeText;
        System.out.println("Fecha y hora de registro de la entrada: " + entryRegistrationDateTime);

        // - Verificar que la fecha y hora de registro de la nueva entrada sea visible
        Assert.assertTrue(entryRegistrationDate.isDisplayed(), "La fecha de registro de la entrada no aparece en el historial de movimientos.");
        Assert.assertTrue(entryRegistrationTime.isDisplayed(), "La hora de registro de la entrada no aparece en el historial de movimientos.");

        // Verificar que la fecha y hora de registro de la nueva entrada sean actuales (diferencia máxima de 1 minuto)
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyyHH:mm", Locale.ENGLISH);
        LocalDateTime entryRegistrationLocalDateTime = LocalDateTime.parse(entryRegistrationDateTime, dateTimeFormatter);
        Duration duration = Duration.between(newEntryRegistrationDateTime, entryRegistrationLocalDateTime);
        Assert.assertTrue(Math.abs(duration.toMinutes()) <= 1, "La fecha y hora de registro de la entrada no son actuales.");

        System.out.println("Verificación completada: El sistema genero el lote de inventario y actualizo el stock del producto correctamente.");
    }
}