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
public class TestaPedirExame {
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
        select.selectByIndex(2); // Índice 1 representa a segunda opção (índice começa em 0)

        
        // Fill in first name and last name
        WebElement cpfInput = driver.findElement(By.name("CPF"));
        cpfInput.sendKeys("381.585.150-53");
        
        WebElement senhaInput = driver.findElement(By.name("senha"));
        senhaInput.sendKeys("111");
        
        WebElement enviarButton = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviarButton.click();
        
        WebElement visualizarConsultasButton = driver.findElement(By.xpath("/html/body/div/form/button"));
        visualizarConsultasButton.click();
        
        WebElement concluirConsultaButton = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[8]/a/button"));
        concluirConsultaButton.click();
        
        WebElement descricaoInput = driver.findElement(By.name("descricao"));
        descricaoInput.sendKeys("concluir");
        
        WebElement enviar2Button = driver.findElement(By.xpath("/html/body/div/form/input[4]"));
        enviar2Button.click();
        
        WebElement voltarButton = driver.findElement(By.xpath("/html/body/div/a/button"));
        voltarButton.click();
        
        WebElement solicitarExameButton = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[8]/a/button"));
        solicitarExameButton.click();
        
        WebElement tipoDeExameButton = driver.findElement(By.name("id_exame"));
        Select select1 = new Select(tipoDeExameButton);
        select1.selectByIndex(0); // Índice 1 representa a segunda opção (índice começa em 0);

        WebElement enviar3Button = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviar3Button.click();
        
        WebElement voltar2Button = driver.findElement(By.xpath("/html/body/div/a/button"));
        voltar2Button.click();
        
}
}
