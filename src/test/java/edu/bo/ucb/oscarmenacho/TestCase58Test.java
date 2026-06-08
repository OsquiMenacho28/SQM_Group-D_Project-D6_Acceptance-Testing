package edu.bo.ucb.oscarmenacho;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Historia de Usuario: Como Cuidador quiero ejecutar una tarea de alimentación para que el inventario
 *                      se descuente automáticamente según la dieta configurada.
 * 
 * Prueba de Aceptación (Caso de Prueba 58): Verificar que la transacción de tarea de alimentación descuenta
 *                                           el inventario automáticamente.
 * 
 * Modulo: Gestión de Inventario
 * 
 * Pre-Condiciones:
 *      - Estar autenticado como Cuidador y Administrador.
 *      - Existe una tarea de alimentación pendiente con dieta configurada.
 *      - Haber stock disponible de los productos de la dieta.
 * 
 * Pasos para la Ejecución de la Prueba:
 *      1. Como administrador, hacer clic en el icono de perfil en el header y seleccionar la opción 'Panel de Administración'.
 *      2. Pulsar el botón de menu desplegable a la derecha del nombre de usuario y seleccionar 'Gestión Inventario'.
 *      3. Anotar el stock actual de los productos de la dieta del animal.
 *      4. Iniciar sesión como Cuidador, clic en la imagen de perfil en el header, seleccionar la opción de Panel de Cuidador.
 *      5. Ejecutar la tarea de alimentación desde el módulo de Gestión de Tareas.
 *      6. Verificar el stock de los productos de la dieta en el módulo de Inventario.
 * 
 * Resultado Esperado: El stock de cada producto de la dieta disminuyo en la cantidad correcta según
 *                     la dieta configurada, de forma automática.
 */

// * Para ejecutar en la linea de comando: mvn clean compile test -Dtest=TestCase58Test

public class TestCase58Test {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {

        String path = "D:\\Users\\usuario\\chromedriver-win64\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", path);

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    @SuppressWarnings("CallToPrintStackTrace")
    public void testToVerifyInventoryDiscountWhenRunningAFeedingTask() {

        //********** 1. Preparación de la Prueba **********//

        // Paso 1. Ingresar a la pagina principal e iniciar sesión como Administrador
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
        WebElement login = driver.findElement(By.xpath("/html/body/app-root/zoo-layout/zoo-header/header/div[2]/div[2]/div[1]/p-button[1]/button"));
        login.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina de Inicio de Sesión cargada...");

        WebElement userInput = driver.findElement(By.xpath("//*[@id=\"Usuario\"]"));
        userInput.clear();
        userInput.sendKeys("admin@zconnect.com");

        WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"Contraseña\"]/input"));
        passwordInput.clear();
        passwordInput.sendKeys("admin123");

        WebElement adminLogin = driver.findElement(By.xpath("/html/body/app-root/app-login/div/p-card/div/div/div/div[2]/div[2]/form/div[2]/p-button/button"));
        adminLogin.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pagina principal de Administrador cargada...");

        // Hacer clic en el icono de perfil en el header (sesión de Administrador)
        WebElement adminProfileIcon = driver.findElement(By.xpath("//zoo-profile-button//button"));
        adminProfileIcon.click();

        // Esperamos a que se despliegue el menu de perfil
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Seleccionar la opción 'Panel de Administración'
        WebElement adminPanelOption = driver.findElement(By.xpath("//span[text()='Panel de Administración']"));
        adminPanelOption.click();

        // Esperamos a que se cargue el Dashboard administrativo
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Dashboard administrativo cargado (sesión Administrador)...");

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
        System.out.println("Módulo de Gestión de Inventario cargado...");

        // Paso 3. Anotar el stock actual de los productos de la dieta del animal (valores previos a la tarea)
        WebElement dietProductStockElement = driver.findElement(By.xpath("//h3[text()='Balanceado seco']/ancestor::zoo-producto-item//span[contains(@class,'value')]"));
        String previousStockText = dietProductStockElement.getText();
        int previousStock = Integer.parseInt(previousStockText.replaceAll("[^0-9]", ""));
        System.out.println("Stock previo a la tarea de alimentación: " + previousStock);

        // Paso 4. Iniciar sesión como Cuidador
        // Cerrar sesión del Administrador

        // Hacer clic en el icono de perfil en el header (sesión de Administrador)
        WebElement adminProfileIconToLogOut = driver.findElement(By.xpath("//zoo-profile-button//button"));
        adminProfileIconToLogOut.click();

        // Esperamos a que se despliegue el menu de perfil
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Seleccionar la opción 'Cerrar Sesión' para el Administrador
        WebElement adminLogOut = driver.findElement(By.xpath("//span[text()='Cerrar Sesión']"));
        adminLogOut.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sesión de Administrador cerrada...");
        System.out.println("Pagina de Inicio de Sesión cargada...");

        // Ingresar credenciales del Cuidador
        WebElement keeperUserInput = driver.findElement(By.xpath("//*[@id=\"Usuario\"]"));
        keeperUserInput.clear();
        keeperUserInput.sendKeys("keeper@zconnect.com");

        WebElement keeperPasswordInput = driver.findElement(By.xpath("//*[@id=\"Contraseña\"]/input"));
        keeperPasswordInput.clear();
        keeperPasswordInput.sendKeys("keeperABC123!");

        WebElement loginButton = driver.findElement(By.xpath("/html/body/app-root/app-login/div/p-card/div/div/div/div[2]/div[2]/form/div[2]/p-button/button"));
        loginButton.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sesión iniciada como Cuidador...");

        // Hacer clic en la imagen de perfil del Cuidador en el header
        WebElement keeperProfileIcon = driver.findElement(By.xpath("//zoo-profile-button//button"));
        keeperProfileIcon.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Seleccionar la opción Panel Cuidador
        WebElement keeperPanelOption = driver.findElement(By.xpath("//span[text()='Panel Cuidador']"));
        keeperPanelOption.click();

        // Esperamos a que se muestre el panel con las tareas asignadas al cuidador
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Panel de Cuidador cargado con tareas asignadas...");

        // Paso 5. Ejecutar la tarea de alimentación desde el módulo de Gestión de Tareas
        // Ejecutar / completar la tarea de alimentación
        WebElement executeTaskButton = driver.findElement(By.xpath("//tr[contains(.,'Arpía') or contains(.,'Alimentación')]/td[6]/p-button/button"));
        executeTaskButton.click();

        // Esperamos a que el modal de Registro de Alimentación se muestre
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Modal de Registro de Alimentación mostrado...");

        WebElement consumedAmountInput = driver.findElement(By.xpath("/html/body/app-root/app-vet-layout/div/div/app-mis-tareas/p-dialog/div/div/div[2]/div/div[2]/div/div[2]/div[2]/p-inputnumber/input"));
        consumedAmountInput.clear();
        consumedAmountInput.sendKeys("20");

        WebElement observationsInput = driver.findElement(By.xpath("//*[@id=\"obs\"]"));
        observationsInput.clear();
        observationsInput.sendKeys("Dieta administrada correctamente, el animal consumió toda la porción asignada.");

        WebElement registerButton = driver.findElement(By.xpath("/html/body/app-root/app-vet-layout/div/div/app-mis-tareas/p-dialog/div/div/div[3]/div/p-button[2]/button"));
        registerButton.click();

        // Esperamos a que la tarea sea procesada y marcada como completada
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tarea de alimentación ejecutada y marcada como completada...");

        // Verificar que la tarea fue marcada como completada
        WebElement taskHistoryButton = driver.findElement(By.xpath("/html/body/app-root/app-vet-layout/div/div/app-mis-tareas/div/div[1]/div[2]/p-selectbutton/p-togglebutton[2]"));
        taskHistoryButton.click();

        // Esperamos a que el historial de tareas se muestre con la tarea actual marcada como completada
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Historial de tareas mostrado con la tarea actual marcada como completada...");

        WebElement taskStatus = driver.findElement(By.xpath("//tbody/tr/td[5]/p-tag/span"));
        String statusText = taskStatus.getText();
        System.out.println("Estado de la tarea: " + statusText);

        // Paso 6. Verificar el stock de los productos de la dieta en el modulo de Inventario
        // Volver al panel del Administrador para revisar el inventario
        WebElement keeperProfileIcon2 = driver.findElement(By.xpath("//zoo-profile-button//button"));
        keeperProfileIcon2.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Cerrar sesión del Cuidador e iniciar con Administrador nuevamente
        WebElement keeperLogOut = driver.findElement(By.xpath("//span[text()='Cerrar Sesión']"));
        keeperLogOut.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sesión de Cuidador cerrada...");
        System.out.println("Pagina de Inicio de Sesión cargada...");

        // Iniciar sesión como Administrador
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
        System.out.println("Sesión iniciada como Administrador...");

        // Navegar al modulo de Gestión de Inventario

        // Hacer clic en el icono de perfil en el header (sesión de Administrador)
        WebElement adminProfileIcon2 = driver.findElement(By.xpath("//zoo-profile-button//button"));
        adminProfileIcon2.click();

        // Esperamos a que se despliegue el menu de perfil
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de perfil desplegado...");

        // Seleccionar la opción 'Panel de Administración'
        WebElement adminPanelOption2 = driver.findElement(By.xpath("//span[text()='Panel de Administración']"));
        adminPanelOption2.click();

        // Esperamos a que se cargue el Dashboard administrativo
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Dashboard administrativo cargado (sesión Administrador)...");

        WebElement dropdownMenu2 = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/zoo-header/header/div[2]/div[2]/p-button/button"));
        dropdownMenu2.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Menú de Panel de Administrador desplegado...");

        WebElement inventoryManagementOption2 = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/p-drawer/div/div[2]/zoo-sidebar-admin-menu/ul/li[4]"));
        inventoryManagementOption2.click();

        WebElement closeDropdownMenu2 = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/p-drawer/div/div[1]/p-button/button"));
        closeDropdownMenu2.click();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Módulo de Gestión de Inventario cargado para verificación final...");

        // Obtener el stock actual del producto después de la ejecución de la tarea
        WebElement productStockAfterElement = driver.findElement(By.xpath("//h3[text()='Balanceado seco']/ancestor::zoo-producto-item//span[contains(@class,'value')]"));
        String stockAfterText = productStockAfterElement.getText();
        int stockAfter = Integer.parseInt(stockAfterText.replaceAll("[^0-9]", ""));
        System.out.println("Stock después de la tarea de alimentación: " + stockAfter);

        //********** 3. Verificación de la situación esperada - Assert **********//

        // Verificar que la tarea fue marcada como completada
        Assert.assertEquals(statusText, "Completada", "La tarea de alimentación no fue marcada como completada.");

        // Verificar que el stock disminuyo correctamente después de ejecutar la tarea
        // El stock posterior debe ser menor que el stock previo
        Assert.assertTrue(stockAfter < previousStock, "El stock no disminuyo tras ejecutar la tarea de alimentación. Stock previo: " + previousStock + " | Stock posterior: " + stockAfter);

        // Calcular la cantidad descontada y verificar que sea la correcta según la dieta
        int discountedAmount = previousStock - stockAfter;
        int expectedDietAmount = 10; // Reemplazar con la cantidad real configurada en la dieta del animal
        System.out.println("Cantidad descontada del inventario: " + discountedAmount);

        Assert.assertEquals(discountedAmount, expectedDietAmount, "La cantidad descontada del inventario no coincide con la dieta configurada. Se esperaba: " + expectedDietAmount + " | Se descontaron: " + discountedAmount);

        System.out.println("Verificación completada: El inventario se descontó correctamente de forma automática según la dieta configurada. Stock previo: " + previousStock + " | Stock posterior: " + stockAfter + " | Cantidad descontada: " + discountedAmount);
    }
}