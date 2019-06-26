import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Backbone implements TestRule {

    private Logger logger;
    private WebDriver driver;
    private final String serverAddress  = "http://192.168.137.13";
    private final String serverUser     = "user";
    private final String serverPassword = "bitnami";
    private final String projectName    = "Test project #1";


    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                logon();
                base.evaluate();
                teardown();
            }
        };
    }



    private void logon() {
        driver.get(serverAddress);
        driver.findElement(By.cssSelector("#tl_login")).sendKeys(serverUser);
        driver.findElement(By.cssSelector("#tl_password")).sendKeys(serverPassword);
        driver.findElement(By.cssSelector("input[type=submit]")).click();
    }

    private void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }



    public Backbone(){
        this.logger = LogManager.getLogger();
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    public WebDriver getDriver(){
        return this.driver;
    }

    public Logger getLogger(){
        return this.logger;
    }


}
