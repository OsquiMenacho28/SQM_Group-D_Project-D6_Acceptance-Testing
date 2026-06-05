package edu.bo.ucb.jesusvelasco;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import edu.bo.ucb.jesusvelasco.BaseTest;

/****************************************/
// Historia de Usuario: Como administrador del zoologico quiero registrar un nuevo animal
// en el sistema con todos sus datos para que sea visible en el catalogo publico
//
// Prueba de Aceptacion: Verificar que un animal con datos completos se registre
// exitosamente y se muestre la confirmacion correspondiente
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Ingresar al formulario de creacion de animal
// Paso 3. Llenar todos los campos requeridos (nombre, especie, habitat, descripcion)
// Paso 4. Enviar el formulario y esperar la confirmacion
//
        // Resultado Esperado: El animal se crea exitosamente y se muestra un mensaje de exito
// o redireccion a la pagina de detalle
/****************************************/

public class RegistroAnimalTest extends BaseTest {

    @Test(priority = 3)
    public void registroAnimalConDatosCompletos() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // Paso 1. La sesion ya se inicio en BaseTest.setUp()

        // Paso 2. Navegar por la UI hasta el formulario de creacion de animal
        driver.findElement(By.cssSelector("zoo-profile-button button")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Panel de Administraci\u00f3n']/ancestor::a")).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gesti\u00f3n de Animales']/ancestor::li")
        ).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir Animal']/ancestor::button")
        ).click();
        sleep();

        // Paso 3. Llenar todos los campos requeridos
        String nombreAnimal = "Simba " + ts;
        driver.findElement(By.id("nombre")).sendKeys(nombreAnimal);
        driver.findElement(By.id("procedencia")).sendKeys("Sabana");

        // Seleccionar especie usando el filtro del dropdown
        driver.findElement(By.id("especieId")).click();
        sleep();
        driver.findElement(By.cssSelector(".p-select-filter[placeholder='Buscar especie']"))
            .sendKeys(createdEspecieNombre);
        sleep();
        driver.findElement(By.cssSelector("li[role='option']:not(.p-select-empty-message)")).click();
        sleep();

        // Seleccionar habitat usando el filtro del dropdown
        driver.findElement(By.id("habitatId")).click();
        sleep();
        driver.findElement(By.cssSelector(".p-select-filter[placeholder='Buscar h\u00e1bitat']"))
            .sendKeys(createdHabitatNombre);
        sleep();
        driver.findElement(By.cssSelector("li[role='option']:not(.p-select-empty-message)")).click();
        sleep();

        driver.findElement(By.id("descripcion")).sendKeys("Un leon majestuoso de la sabana africana");

        // Paso 4. Enviar el formulario (step 1 -> "Crear y Continuar")
        driver.findElement(By.xpath("//span[text()='Crear y Continuar']/ancestor::button")).click();
        sleep();

        // Paso 5. Verificar en la lista de animales
        driver.findElement(
            By.xpath("//span[text()='Lista de Animales']/ancestor::button")
        ).click();
        sleep();

        String body = driver.findElement(By.cssSelector(".p-dataview-content")).getText();
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Animal creado: " + nombreAnimal);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(body.contains(nombreAnimal), "El animal '" + nombreAnimal + "' debe aparecer en la lista");
    }
}
