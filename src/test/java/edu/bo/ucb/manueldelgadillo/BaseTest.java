package edu.bo.ucb.manueldelgadillo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Utilidades compartidas para las pruebas de aceptación ATDD de Gestión Clínica.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    protected static final String BASE_URL =
            System.getProperty("zooconnect.baseUrl", "http://localhost:4200");
    protected static final String API_URL =
            System.getProperty("zooconnect.apiUrl", "http://localhost:8000/zooconnect");
    protected static final String VET_EMAIL =
            System.getProperty("zooconnect.vetEmail", "vet@zconnect.com");
    protected static final String VET_PASSWORD =
            System.getProperty("zooconnect.vetPassword", "vetABC123!");

    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void esperar(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected WebDriverWait wait(int segundos) {
        return new WebDriverWait(driver, segundos);
    }

    protected void limpiarSesion() {
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
    }

    protected void escribirLento(WebElement campo, String texto) {
        campo.click();
        campo.clear();
        for (char caracter : texto.toCharArray()) {
            campo.sendKeys(String.valueOf(caracter));
            esperarMs(40);
        }
    }

    private void esperarMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void cerrarToasts() {
        try {
            WebElement toastClose = driver.findElement(By.cssSelector(".p-toast-message-icon-close"));
            if (toastClose.isDisplayed()) {
                toastClose.click();
            }
        } catch (Exception ignored) {
        }
    }

    protected void esperarPerfilApiVeterinario() {
        WebDriverWait webDriverWait = wait(60);
        webDriverWait.until(webDriver -> {
            Object roleId = ((JavascriptExecutor) webDriver).executeAsyncScript(
                    "var callback = arguments[arguments.length - 1];"
                            + "var token = window.localStorage.getItem('access_token');"
                            + "if (!token) { callback(0); return; }"
                            + "fetch('"
                            + API_URL
                            + "/auth/me', { headers: { 'Authorization': 'Bearer ' + token } })"
                            + ".then(function(r) { return r.json(); })"
                            + ".then(function(u) { callback(u.role_id); })"
                            + ".catch(function() { callback(0); });");
            if (roleId instanceof Number) {
                return ((Number) roleId).intValue() == 4;
            }
            if (roleId instanceof Long) {
                return ((Long) roleId).intValue() == 4;
            }
            return false;
        });
    }

    protected void esperarHeaderHidratado() {
        WebDriverWait webDriverWait = wait(60);
        try {
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".header-placeholder")));
        } catch (Exception ignored) {
        }

        WebElement header = webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("header.header")));
        new Actions(driver).moveToElement(header).pause(500).perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("zoo-profile-button button, zoo-profile-button .avatar-button")));
    }

    protected void loginComoVeterinario() {
        for (int intento = 0; intento < 2; intento++) {
            driver.get(BASE_URL + "/login");
            limpiarSesion();
            driver.navigate().refresh();

            WebDriverWait webDriverWait = wait(60);
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-content form")));
            esperar(2);

            WebElement usuario = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("Usuario")));
            WebElement password = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("#Contraseña input, p-password input")));

            usuario.clear();
            escribirLento(usuario, VET_EMAIL);
            password.clear();
            escribirLento(password, VET_PASSWORD);

            WebElement loginBtn = webDriverWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Iniciar Sesión')]")));
            clickJs(loginBtn);

            try {
                webDriverWait.until(ExpectedConditions.urlContains("/inicio"));
                esperarPerfilApiVeterinario();
                esperar(2);
                cerrarToasts();
                esperarHeaderHidratado();
                return;
            } catch (Exception e) {
                if (intento == 1) {
                    throw e;
                }
            }
        }
    }

    protected void irAlPanelVeterinario() {
        if (driver.getCurrentUrl().contains("/vet")) {
            return;
        }

        cerrarToasts();
        WebDriverWait webDriverWait = wait(30);

        for (int intento = 0; intento < 3; intento++) {
            if (driver.getCurrentUrl().contains("/vet")) {
                return;
            }

            try {
                abrirPanelVeterinarioPorMenu();
                webDriverWait.until(ExpectedConditions.urlContains("/vet"));
                esperar(2);
                webDriverWait.until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/vet/historiales"),
                        ExpectedConditions.presenceOfElementLocated(
                                By.cssSelector(".veterinario-layout, .loading-state, [slot='nav-toggle']"))));
                return;
            } catch (Exception e) {
                cerrarToasts();
                esperar(1);
            }
        }

        throw new RuntimeException(
                "No se pudo abrir el Panel Veterinario. URL: " + driver.getCurrentUrl());
    }

    private void abrirPanelVeterinarioPorMenu() {
        WebDriverWait webDriverWait = wait(20);

        WebElement perfil = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("zoo-profile-button button, zoo-profile-button .avatar-button button")));

        new Actions(driver).moveToElement(perfil).pause(800).click().perform();
        esperar(1);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".p-menu-overlay, .p-menu-list, .p-menu, .user-info")));

        WebElement item = webDriverWait.until(ExpectedConditions.elementToBeClickable(localizarPanelVeterinarioMenu()));
        new Actions(driver).moveToElement(item).pause(200).click().perform();
    }

    private By localizarPanelVeterinarioMenu() {
        return By.xpath(
                "//*[@role='menuitem' and contains(.,'Panel Veterinario')]"
                        + " | //li[contains(@class,'p-menuitem') and contains(.,'Panel Veterinario')]"
                        + " | //span[contains(@class,'p-menuitem-text') and contains(.,'Panel Veterinario')]"
                        + " | //a[contains(@class,'p-menuitem-link') and contains(.,'Panel Veterinario')]");
    }

    protected void abrirHistorialesClinicos() {
        if (!driver.getCurrentUrl().contains("/vet/historiales")) {
            cerrarDrawerSiAbierto();
            WebElement menu = wait(15).until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("[slot='nav-toggle'] button")));
            new Actions(driver).moveToElement(menu).pause(200).click().perform();
            esperar(1);
            WebElement historiales = wait(15).until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[contains(@class,'vet-menu-list')]//span[contains(text(),'Historiales Clínicos')]")));
            clickJs(historiales);
            cerrarDrawerSiAbierto();
            esperar(2);
        }
    }

    protected void cerrarDrawerSiAbierto() {
        try {
            WebElement mask = driver.findElement(By.cssSelector(".p-drawer-mask"));
            if (mask.isDisplayed()) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                esperar(1);
            }
        } catch (Exception ignored) {
        }
    }

    protected void esperarFormularioHistorialCargado() {
        WebDriverWait webDriverWait = wait(30);
        webDriverWait.until(ExpectedConditions.urlContains("/vet/historiales/crear"));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(.,'Nuevo Historial Clínico')]")));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("animal")));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("tipo")));
        esperar(1);
    }

    protected void seleccionarPrimeraOpcionPrimeNg(String contenedorCss) {
        String fieldId = contenedorCss.startsWith("#") ? contenedorCss.substring(1) : contenedorCss;
        WebDriverWait webDriverWait = wait(30);
        By opcionLocator = By.xpath(
                "(//div[contains(@class,'p-select-overlay')]//li[@role='option' and not(@aria-disabled='true')])[1]"
                        + " | (//ul[contains(@class,'p-select-list')]//li[not(@aria-disabled='true')])[1]");

        RuntimeException ultimoError = null;
        for (int intento = 0; intento < 3; intento++) {
            cerrarOverlaySelectSiAbierto();
            esperarMs(400);
            if (!abrirSelectPrimeNg(fieldId)) {
                continue;
            }
            esperarMs(600);
            try {
                WebElement opcion = webDriverWait.until(ExpectedConditions.elementToBeClickable(opcionLocator));
                clickJs(opcion);
                esperar(1);
                cerrarOverlaySelectSiAbierto();
                return;
            } catch (RuntimeException e) {
                ultimoError = e;
            }
        }
        if (ultimoError != null) {
            throw ultimoError;
        }
        throw new RuntimeException("No se pudo seleccionar opción en #" + fieldId);
    }

    private boolean abrirSelectPrimeNg(String fieldId) {
        Object abierto = ((JavascriptExecutor) driver).executeScript(
                "var root = document.getElementById(arguments[0]);"
                        + "if (!root) { return false; }"
                        + "root.scrollIntoView({ block: 'center' });"
                        + "var trigger = root.querySelector("
                        + "  'button[aria-label=\"dropdown trigger\"], [data-pc-section=\"trigger\"], .p-select-dropdown, .p-select-label'"
                        + ");"
                        + "if (!trigger) { return false; }"
                        + "trigger.click();"
                        + "return true;",
                fieldId);
        return Boolean.TRUE.equals(abierto);
    }

    private void cerrarOverlaySelectSiAbierto() {
        try {
            if (!driver.findElements(By.cssSelector(".p-select-overlay")).isEmpty()) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                esperarMs(300);
            }
        } catch (Exception ignored) {
        }
    }

    protected void abrirAcordeon(String titulo) {
        WebDriverWait webDriverWait = wait(15);
        WebElement header = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//p-accordion-header[contains(.,'" + titulo + "')]"
                        + " | //*[@role='button' and contains(.,'" + titulo + "')]"
                        + " | //button[contains(.,'" + titulo + "')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", header);
        new Actions(driver).moveToElement(header).pause(200).click().perform();
        esperar(1);
    }

    protected void clickJs(WebElement elemento) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", elemento);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
    }
}
