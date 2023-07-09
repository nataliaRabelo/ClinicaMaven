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
        
    /**
     * Cria uma instância do driver do Selenium antes de cada teste e abre a página inicial do sistema de clínica médica.
     */	
    @Before
    public void createDriver() {  
        driver = new EdgeDriver();
        driver.get("http://localhost:8080/ClinicaMaven/");
    }	

    /**
     * Teste para verificar o fluxo de consulta onde o paciente solicita a consulta
     * e o médico registra que a consulta foi realizada.
    */
     @Test
    public void testFluxo() {

        WebElement fazerLoginButton = driver.findElement(By.linkText("Log In"));
        fazerLoginButton.click();

        WebElement tipoDeAcessoButton = driver.findElement(By.cssSelector("#papel"));
        Select select = new Select(tipoDeAcessoButton);
        select.selectByIndex(0); 

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

        WebElement enviar1Button = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviar1Button.click();

        WebElement voltarButtom = driver.findElement(By.xpath("/html/body/div/a/button"));
        voltarButtom.click();

        WebElement fazerLogoutButton = driver.findElement(By.linkText("Log Out"));
        fazerLogoutButton.click();

        WebElement fazerLoginMedicoButton = driver.findElement(By.linkText("Log In"));
        fazerLoginMedicoButton.click();

        WebElement tipoDeAcessoMedicoButton = driver.findElement(By.cssSelector("#papel"));
        Select select2 = new Select(tipoDeAcessoMedicoButton);
        select2.selectByVisibleText("M�dico");

        WebElement cpfMedicoInput = driver.findElement(By.name("CPF"));
        cpfMedicoInput.sendKeys("693.339.230-98");

        WebElement senhaMedicoInput = driver.findElement(By.name("senha"));
        senhaMedicoInput.sendKeys("111");

        WebElement enviar2Button = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviar2Button.click();

        WebElement visualizarConsultasButton = driver.findElement(By.xpath("/html/body/div/form/button"));
        visualizarConsultasButton.click();

        WebElement concluirConsultaButton = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[8]/a/button"));
        concluirConsultaButton.click();

        WebElement descricaoInput = driver.findElement(By.name("descricao"));
        descricaoInput.sendKeys("concluir");

        WebElement enviar3Button = driver.findElement(By.xpath("/html/body/div/form/input[4]"));
        enviar3Button.click(); 
    }
   
    /**
    * Teste para verificar o fluxo de consulta onde o paciente preenche o input do cpf 
    * com caracteres em excesso.
    */
    @Test
    public void testCarga() {
        WebElement fazerLoginButton = driver.findElement(By.linkText("Log In"));
        fazerLoginButton.click();
        
        WebElement tipoDeAcessoButton = driver.findElement(By.cssSelector("#papel"));
        Select select = new Select(tipoDeAcessoButton);
        select.selectByIndex(0); 

        WebElement cpfInput = driver.findElement(By.name("CPF"));
        cpfInput.sendKeys("937.397.160-379999999999999999999999999999999999999999999999999");
        
        WebElement senhaInput = driver.findElement(By.name("senha"));
        senhaInput.sendKeys("111");
        
        WebElement enviarButton = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviarButton.click();
    }
   
    /**
    *Teste de recuperação de erro para verificar o fluxo de consulta, em que o paciente insere corretamente o número do CPF no campo de entrada,
    *mas fornece uma senha incorreta de acordo com os dados armazenados no banco de dados.
    */ 
    @Test
    public void testErro() {
        WebElement fazerLoginButton = driver.findElement(By.linkText("Log In"));
        fazerLoginButton.click();
        
        WebElement tipoDeAcessoButton = driver.findElement(By.cssSelector("#papel"));
        Select select = new Select(tipoDeAcessoButton);
        select.selectByIndex(0); 

        WebElement cpfInput = driver.findElement(By.name("CPF"));
        cpfInput.sendKeys("937.397.160-379");
        
        WebElement senhaInput = driver.findElement(By.name("senha"));
        senhaInput.sendKeys("11109");
        
        WebElement enviarButton = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
        enviarButton.click();
    }
  
}
