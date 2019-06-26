import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class Project {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Project #2", "proj2", "Project #2 description" },
                { "Project #3", "proj3", "Project #3 description" },
        });
    }




    @Test
    public void createProject() {

        logger.info("create project");

        /* move to project management */
        driver.switchTo().defaultContent();
        driver.switchTo().frame("titlebar");
        driver.findElement(By.cssSelector("a[accesskey=h]")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.findElement(By.xpath("//a[contains(text(),'Test Project Management')]")).click();

        /* create new */
        driver.findElement(By.cssSelector("input#create")).click();

        /* fill data */
        driver.findElement(By.cssSelector("input[name=tprojectName]")).sendKeys(projectName);
        driver.findElement(By.cssSelector("input[name=tcasePrefix]")).sendKeys(projectPrefix);
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("body")).sendKeys(projectDescription);

        /* save project */
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.findElement(By.cssSelector("input[name=doActionButton]")).click();

        /* check result */
        logger.info("check result");

        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");

        /* check project name */
        int projectNameExist = driver.findElements(By.xpath("//table[@id='item_view']//following::a[contains(text(),'" + projectName + "')]")).size();
        assertThat(1, equalTo(projectNameExist));

        /* check project description */
        int projectDescriptionExist = driver.findElements(By.xpath("//table[@id='item_view']//following::p[contains(text(),'" + projectDescription + "')]")).size();
        assertThat(1, equalTo(projectDescriptionExist));

        /* check project prefix */
        int projectPrefixExist = driver.findElements(By.xpath("//table[@id='item_view']//following::*[contains(text(),'" + projectPrefix + "')]")).size();
        assertThat(1, equalTo(projectPrefixExist));

        WebElement row = driver.findElement(By.xpath("//a[contains(text(),'" + projectName + "')]/../.."));

        /* check project active status */
        int isProjectActive = row.findElements(By.cssSelector("input[title*='Active']")).size();
        assertThat(1, equalTo(isProjectActive));

        /* check project public status */
        int isProjectPublic = row.findElements(By.cssSelector("img[title*='Public']")).size();
        assertThat(1, equalTo(isProjectPublic));

    }









    @Rule
    public Backbone backbone = new Backbone();

    private WebDriver driver = backbone.getDriver();
    private Logger logger = backbone.getLogger();

    private String projectName;
    private String projectPrefix;
    private String projectDescription;


    public Project(String projectName, String projectPrefix, String projectDescription){
        this.projectName        = projectName;
        this.projectPrefix      = projectPrefix;
        this.projectDescription = projectDescription;
    }
}
