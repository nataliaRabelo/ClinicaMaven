//package exselenium;
//
//import static org.junit.Assert.*;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.support.ui.Select;
//
//public class TestaAdministrador {
//	
//	protected WebDriver driver;
//	
//	@BeforeClass
//	public static void configuraDriver() {
//            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/src/resources/msedgedriver.exe");
//	}
//	
//    @Before
//    public void createDriver() {  
//		driver = new EdgeDriver();
//        driver.get("http://localhost:8080/ClinicaMaven/");
//    }	
//
// @Test
//    public void test() {
//        WebElement fazerLoginButton = driver.findElement(By.linkText("Log In"));
//        fazerLoginButton.click();
//        
//        WebElement tipoDeAcessoButton = driver.findElement(By.cssSelector("#papel"));
//        Select select = new Select(tipoDeAcessoButton);
//        select.selectByIndex(1); // Índice 1 representa a segunda opção (índice começa em 0)
//
//        
//        // Fill in first name and last name
//        WebElement cpfInput = driver.findElement(By.name("CPF"));
//        cpfInput.sendKeys("249.252.810-38");
//        
//        WebElement senhaInput = driver.findElement(By.name("senha"));
//        senhaInput.sendKeys("111");
//        
//        WebElement enviarButton = driver.findElement(By.xpath("/html/body/div/form/input[3]"));
//        enviarButton.click();
//        
//        WebElement verMedicosButton = driver.findElement(By.xpath("/html/body/div/div/form[2]/button"));
//        verMedicosButton.click();
//        
//        WebElement cadastrarMedicoButton = driver.findElement(By.xpath("/html/body/form/button"));
//        cadastrarMedicoButton.click();
//        
//        WebElement nomeInput = driver.findElement(By.name("nome"));
//        nomeInput.sendKeys("JoãoTeste1");
//        
//        WebElement crmInput = driver.findElement(By.name("crm"));
//        crmInput.sendKeys("4321");
//        
//        WebElement estadoDoCrmButton = driver.findElement(By.name("estadocrm"));
//        Select select1 = new Select(estadoDoCrmButton);
//        select1.selectByIndex(3); // Índice 1 representa a segunda opção (índice começa em 0)
////        
//        WebElement novoCpfInput = driver.findElement(By.name("CPF"));
//        novoCpfInput.sendKeys("100.100.810-38");
//        
//        WebElement novaSenhaInput = driver.findElement(By.name("senha"));
//        novaSenhaInput.sendKeys("111");
//        
//        WebElement especialidadeButton = driver.findElement(By.name("especialidade"));
//        Select select2 = new Select(especialidadeButton);
//        select2.selectByIndex(1); // Índice 1 representa a segunda opção (índice começa em 0)
//        
//        WebElement cadastrarButton = driver.findElement(By.xpath("/html/body/div/form/input[5]"));
//        cadastrarButton.click();
//    }
//    
//	
//    @After
//    public void quitDriver() {
//       driver.quit();
//    }
//}
