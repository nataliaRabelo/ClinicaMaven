package seleniumTests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class NewSeleneseIT {

    private WebDriver driver;

    @BeforeAll
    public static void configuraDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\ClinicaMaven\\src\\main\\resources\\chromedriver.exe");
    }

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
        driver.get("http://www.netbeans.org");
    }

    @Test
    public void testSimple() throws Exception {
        // Coloque aqui o código do seu teste
    }

    @AfterEach
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
