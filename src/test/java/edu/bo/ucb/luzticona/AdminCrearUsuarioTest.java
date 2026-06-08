package edu.bo.ucb.luzticona;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/****************************************/
// Historia de Usuario: Como Administrador quiero crear un nuevo usuario con rol Veterinario
//
// Prueba de Aceptacion - Caso de Prueba #5 (PDF)
// Titulo: Verificar que un Administrador puede crear un nuevo usuario con rol Veterinario
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Acceder al Panel de Administracion
// Paso 3. Navegar a Gestion de Usuarios y presionar "Nuevo Usuario"
// Paso 4. Completar datos y asignar rol 'Veterinario'
// Paso 5. Guardar y verificar que aparece en la lista
//
// Resultado Esperado: El usuario con rol Veterinario es creado exitosamente
/****************************************/

public class AdminCrearUsuarioTest extends BaseTest {

    @Test(priority = 3)
    public void adminCreaUsuarioVeterinario() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // 1. PREPARACION DE LA PRUEBA 
        String username = "veterinario" + ts;
        String email = "veterinario" + ts + "@zoo.com";
        String password = "Vet123456";

        // 2. LOGICA DE LA PRUEBA
        // Paso 1: Iniciar sesion como administrador
        loginAdmin();

        // Paso 2: Acceder al Panel de Administracion
        navigateToAdminPanel();

        // Paso 3: Navegar a Gestion de Usuarios
        navigateToGestionUsuarios();

        // Presionar el boton "Crear Usuario"
        driver.findElement(By.xpath("//span[text()='Crear Usuario']/ancestor::button")).click();
        sleep();

        // Paso 4: Completar datos y asignar rol 'Veterinario'
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("email")).sendKeys(email);

        // Seleccionar rol Veterinario
        driver.findElement(By.id("rol")).click();
        sleep();
        driver.findElement(By.xpath("//li[@role='option'][contains(., 'Veterinario')]")).click();
        sleep();

        // Paso 5: Crear
        driver.findElement(By.xpath("//span[text()='Crear']/ancestor::button")).click();
        sleep();

        // 3.ASSERT 
        // Verificar que aparece en la lista de usuarios
        String body = driver.findElement(By.cssSelector(".p-dataview-content")).getText();
        Assert.assertTrue(body.contains(username), "El usuario '" + username + "' no aparece en la lista");

        // Verificar que tiene el rol Veterinario
        Assert.assertTrue(body.contains("Veterinario"), "El rol Veterinario no aparece asignado");

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Usuario Veterinario creado: " + username);
        System.out.println("Email: " + email);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");
    }
}