package edu.bo.ucb.luzticona;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como usuario nuevo quiero registrarme en Zoo Connect
//
// Prueba de Aceptacion - Caso de Prueba #1 (PDF)
// Titulo: Verificar que un usuario puede registrarse correctamente con datos validos
//
// Paso 1. Navegar a la pagina de registro
// Paso 2. Completar todos los campos con datos validos
// Paso 3. Hacer clic en el boton 'Crear cuenta'
// Paso 4. Verificar que redirige a la pantalla de inicio de sesion
//
// Resultado Esperado: El usuario se registra exitosamente y puede iniciar sesion
/****************************************/

public class RegistroUsuarioTest extends BaseTest {

    @Test(priority = 1)
    public void registroUsuarioExitoso() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        //1. PREPARACION DE LA PRUEBA 
        String username = "luztest" + ts;
        String email = "luztest" + ts + "@zoo.com";
        String password = "Test123456";
        createdUsuarioUsername = username;
        createdUsuarioEmail = email;

        //  2. LOGICA DE LA PRUEBA 
        // Paso 1: Navegar a la pagina de registro
        driver.get(BASE_URL + "/registro");
        sleep();

        // Paso 2: Completar todos los campos con datos validos
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(password);

        // Paso 3: Hacer clic en el boton 'Crear cuenta'
        driver.findElement(By.xpath("//button[contains(text(),'Crear cuenta')]")).click();
        sleep();

        //  3.ASSERT
        // Verificar que redirige a la pantalla de inicio de sesion
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"), "No redirigio a la pagina de login");

        // Verificar que muestra mensaje de confirmacion
        String bodyText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(bodyText.contains("Cuenta creada") || bodyText.contains("registro exitoso"),
                "No se encontro mensaje de confirmacion");

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Usuario creado: " + username);
        System.out.println("Email: " + email);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");
    }
}