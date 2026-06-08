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
// Esta prueba es AUTOCONTENIDA: crea su propia especie y habitat para no depender
// de otros tests. Tematica: Panda Gigante
//
// Paso 1. Iniciar sesion como administrador
// Paso 2. Navegar al modulo Gestion de Animales
// Paso 3. Crear una nueva especie (Panda Gigante)
// Paso 4. Crear un nuevo habitat (Bosque de Bambu)
// Paso 5. Crear un nuevo animal (Sombra) asociado a especie y habitat creados
// Paso 6. Verificar que el animal aparece en la lista
//
// Resultado Esperado: El animal se crea exitosamente y aparece en la lista
/****************************************/

public class RegistroAnimalTest extends BaseTest {

    @Test
    public void registroAnimalAutocontenido() {
        long startTime = System.currentTimeMillis();
        String ts = String.valueOf(startTime);

        // ========================================
        // NAVEGAR AL MODULO GESTION DE ANIMALES
        // ========================================

        driver.findElement(By.xpath("//zoo-profile-button//button")).click();
        sleep();

        driver.findElement(By.xpath("//span[text()='Panel de Administraci\u00f3n']/ancestor::a")).click();
        sleep();

        driver.findElement(By.xpath("//p-button[@slot='nav-toggle']//button")).click();
        sleep();

        driver.findElement(
            By.xpath("//zoo-sidebar-admin-menu//span[text()='Gesti\u00f3n de Animales']/ancestor::li")
        ).click();
        sleep();

        driver.findElement(By.xpath("//button[.//span[contains(@class, 'pi-times')]]")).click();
        sleep();

        // ========================================
        // PARTE 1: CREAR ESPECIE PANDA GIGANTE
        // ========================================

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir Especie']/ancestor::button")
        ).click();
        sleep();

        String especieNombre = "Panda Gigante " + ts;
        driver.findElement(By.id("nombreCientifico")).sendKeys("Ailuropoda melanoleuca " + ts);
        driver.findElement(By.id("nombreComun")).sendKeys(especieNombre);
        driver.findElement(By.id("filo")).sendKeys("Chordata");
        driver.findElement(By.id("clase")).sendKeys("Mammalia");
        driver.findElement(By.id("orden")).sendKeys("Carnivora");
        driver.findElement(By.id("familia")).sendKeys("Ursidae");
        driver.findElement(By.id("descripcion")).sendKeys("Oso nativo de China, conocido por su dieta de bambu");

        driver.findElement(By.xpath("//span[text()='Crear Especie']/ancestor::button")).click();
        sleep();

        // ========================================
        // PARTE 2: CREAR HABITAT DE BAMBU
        // ========================================

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir H\u00e1bitat']/ancestor::button")
        ).click();
        sleep();

        String habitatNombre = "Bosque de Bambu " + ts;
        driver.findElement(By.id("nombre")).sendKeys(habitatNombre);
        driver.findElement(By.id("tipo")).sendKeys("Bosque Templado");
        driver.findElement(By.id("descripcion")).sendKeys("Bosque templado con abundante bambu");
        driver.findElement(By.id("condicionesClimaticas")).sendKeys("Templado y humedo");

        driver.findElement(By.xpath("//span[text()='Crear y Continuar']/ancestor::button")).click();
        sleep();

        // ========================================
        // PARTE 3: CREAR ANIMAL SOMBRA
        // ========================================

        driver.findElement(
            By.xpath("//span[text()='A\u00f1adir Animal']/ancestor::button")
        ).click();
        sleep();

        String nombreAnimal = "Sombra " + ts;
        driver.findElement(By.id("nombre")).sendKeys(nombreAnimal);
        driver.findElement(By.id("procedencia")).sendKeys("China");

        driver.findElement(By.id("fechaNac")).sendKeys("2026-03-15");
        sleep();
        driver.findElement(By.id("fechaIng")).sendKeys("2026-06-01");
        sleep();

        // Seleccionar especie Panda
        driver.findElement(By.id("especieId")).click();
        sleep();
        driver.findElement(By.xpath("//input[@placeholder='Buscar especie']"))
            .sendKeys(especieNombre);
        sleep();
        driver.findElement(By.cssSelector("li[role='option']:not(.p-select-empty-message)")).click();
        sleep();

        // Seleccionar habitat Bambu
        driver.findElement(By.id("habitatId")).click();
        sleep();
        driver.findElement(By.xpath("//input[@placeholder='Buscar h\u00e1bitat']"))
            .sendKeys(habitatNombre);
        sleep();
        driver.findElement(By.cssSelector("li[role='option']:not(.p-select-empty-message)")).click();
        sleep();

        driver.findElement(By.id("descripcion")).sendKeys("Panda gigante de pelaje blanco y negro");

        driver.findElement(By.xpath("//span[text()='Crear y Continuar']/ancestor::button")).click();
        sleep();

        // ========================================
        // PARTE 4: VERIFICACION
        // ========================================

        driver.findElement(
            By.xpath("//span[text()='Lista de Animales']/ancestor::button")
        ).click();
        sleep();

        String body = driver.findElement(By.xpath("//zoo-lista-animales/div/p-dataview/div[2]")).getText();
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("Especie creada: " + especieNombre);
        System.out.println("Habitat creado: " + habitatNombre);
        System.out.println("Animal creado: " + nombreAnimal);
        System.out.println("Tiempo de ejecucion: " + elapsed + " ms");

        Assert.assertTrue(body.contains(nombreAnimal), "El animal '" + nombreAnimal + "' debe aparecer en la lista");
    }
}
