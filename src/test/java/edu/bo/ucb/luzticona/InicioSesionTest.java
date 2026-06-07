package edu.bo.ucb.luzticona;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como usuario registrado quiero iniciar sesion
//
// Prueba de Aceptacion - Caso de Prueba #3 (PDF)
// Titulo: Verificar que un usuario puede iniciar sesion con credenciales correctas
//
// Paso 1. Navegar a la pagina de inicio de sesion
// Paso 2. Ingresar correo y contrasena correctos
// Paso 3. Hacer clic en el boton 'Iniciar sesion'
// Paso 4. Verificar que el usuario es autenticado y ve el icono de perfil
//
// Resultado Esperado: El usuario inicia sesion exitosamente y los botones de acceso
// son reemplazados por el icono de perfil
/****************************************/

public class InicioSesionTest extends BaseTest {

    @Test(priority = 2, dependsOnMethods = { "registroUsuarioExitoso" })
    public void inicioSesionCredencialesCorrectas() {
        long startTime = System.currentTimeMillis();

        // 1. PREPARACION DE LA PRUEBA 
        // Usamos el usuario creado en RegistroUsuarioTest
        String email = createdUsuarioEmail;
        String password = "Test123456";

        // 2. LOGICA DE LA PRUEBA
        // Paso 1: Navegar a la pagina de inicio de sesion
        driver.get(BASE_URL + "/login");
        sleep();

        // Paso 2: Ingresar correo y contrasena 
        driver.findElement(By.id("Usuario")).sendKeys(email);
        driver.findElement(By.cssSelector(".p-password-input")).sendKeys(password);

        // Paso 3: Hacer clic en el boton 'Iniciar sesion'
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep();

        //3.ASSERT 
        // Verificar que el icono de perfil esta visible (usuario autenticado)
        boolean profileButtonVisible = driver.findElements(By.cssSelector("zoo-profile-button button")).size() > 0;
        Assert.assertTrue(profileButtonVisible, "El icono de perfil no aparece - usuario no autenticado");

        // Verificar que NO esta el boton de login/registro
        boolean loginButtonVisible = driver.findElements(By.xpath("//a[contains(text(),'Iniciar')]")).size() > 0;
        Assert.assertFalse(loginButtonVisible, "Aun aparece el boton de iniciar sesion");

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Usuario autenticado: " + createdUsuarioUsername);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");
    }
}