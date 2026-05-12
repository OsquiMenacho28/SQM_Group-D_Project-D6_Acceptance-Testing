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

/****************************************/
// Historia de Usuario: Como usuario quiero verificar que el botón "Buscar con Google" se despliega
//
// Prueba de Aceptación: Verificar que el botón de búsqueda tenga el texto "Buscar con Google"
//
// Paso 1. Ingresar a la pagina de Google: https://www.google.com
// Paso 2. Buscar el botón "Buscar con Google"

// 
// Resultado Esperado: El botón "Buscar con Google" debe estar presente y ser visible
/****************************************/


//Para ejecutar en la linea de comando: mvn clean compile test -Dtest=BuscarGoogleTest

public class BuscarGoogleTest {
    
    private WebDriver driver;
    
    @BeforeTest
    public void setDriver() throws Exception{

        //https://sites.google.com/chromium.org/driver/getting-started
        
    	String path = "D:\\Users\\usuario\\chromedriver-win64\\chromedriver.exe";
        
        System.setProperty("webdriver.chrome.driver", path);
        
        WebDriverManager.chromedriver().setup();
        
        driver = new ChromeDriver();
        
    }
    
    @AfterTest
    public void closeDriver() throws Exception{
        driver.quit();
    }
    
    @Test
    public void paginaPrincipalGoogle(){
        
        /**************  1. Preparación de la prueba***********/
    	
    	//Paso 1. Ingresar a la pagina de Google
        String googleUrl = "https://www.google.com";
        driver.get(googleUrl);
        
        
        /************** 2. Lógica de la prueba***************/
        // Paso 2. Buscar el botón "Buscar con Google"
        
        /*Capturar el botón "Buscar con Google"*/
        
        WebElement button = driver.findElement(By.name("btnK"));

        try{
            TimeUnit.SECONDS.sleep(5);
        }
        catch(InterruptedException e){
            // e.printStackTrace();
        }    

        String txtButton = button.getAttribute("value");
        System.out.println("Texto del botón: "+txtButton);
        
        /************ 3. Verificación de la situación esperada - Assert ***************/
        
        Assert.assertEquals(txtButton,"Buscar con Google");
    } 
}