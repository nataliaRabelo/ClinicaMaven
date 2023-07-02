package exselenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
/**
 *
 * @author carol
 */
public class TestaConsulta {
    protected WebDriver driver;
	
	@BeforeClass
	public static void configuraDriver() {
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/src/resources/msedgedriver.exe");
	}
	
    @Before
    public void createDriver() {  
		driver = new EdgeDriver();
        driver.get("http://localhost:8080/ClinicaMaven/");
    }	

 @Test
  public void test() {
        WebElement fazerLoginButton = driver.findElement(By.linkText("Log In"));
        fazerLoginButton.click();
        
        WebElement tipoDeAcessoButton = driver.findElement(By.cssSelector("#papel"));
        Select select = new Select(tipoDeAcessoButton);
        select.selectByIndex(0); // Índice 1 representa a segunda opção (índice começa em 0)

        
        // Fill in first name and last name
        WebElement cpfInput = driver.findElement(By.name("CPF"));
        cpfInput.sendKeys("937.397.160-37");
        
        WebElement senhaInput = driver.findElement(By.name("senha"));
        senhaInput.sendKeys("111");
        
        WebElement enviarButton = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviarButton.click();
        
        WebElement marcarConsultaButton = driver.findElement(By.xpath("/html/body/div/div/form[2]/button\n"));
        marcarConsultaButton.click();
        
        WebElement dataInput = driver.findElement(By.name("data"));
        dataInput.sendKeys("02/07/2023");
        
        WebElement horarioInput = driver.findElement(By.name("hora"));
        horarioInput.sendKeys("12:00");
        
        WebElement especialidadeMedicoSelect = driver.findElement(By.cssSelector("#papel"));
        Select select1 = new Select(especialidadeMedicoSelect);
        select1.selectByIndex(0); // Índice 1 representa a segunda opção (índice começa em 0)
        
        WebElement enviar1Button = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviar1Button.click();
        
        WebElement voltarButtom = driver.findElement(By.xpath("/html/body/div/a/button"));
        voltarButtom.click();
        
        WebElement fazerLogoutButton = driver.findElement(By.linkText("Log Out"));
        fazerLogoutButton.click();
        
}
}
