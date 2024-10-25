package openmrs.test;

import org.openmrs.demo.page.objects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Properties;

public class OpenMrsTestNgBaseClass {

    public WebDriver driver;
    public LoginPage loginPage;
    public HomePage homePage;
    public RegisterPage registerPage;
    public DetailsPage detailsPage;
    public FindPatientPage findPatientPage;


    @BeforeSuite(alwaysRun = true)
    public void setUpChromeDriver() {
        Utils.readPropertiesFile("/src/test/resources/test.properties");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+Utils.getTestProperty("driver.path"));
    }

    @BeforeTest(alwaysRun = true)
    public void initializeDriver() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        detailsPage = new DetailsPage(driver);
        findPatientPage = new FindPatientPage(driver);
    }

    @BeforeClass(alwaysRun = true)
    public void navigateToApplication() {

        loginPage.launchOpenMrsApplication(Utils.getTestProperty("url"));
        Assert.assertTrue(loginPage.verifyPageTitle("Login"));
    }

    @BeforeMethod(alwaysRun = true)
    public void loginToApplication() {
        loginPage.loginToOpenMrs(Utils.getTestProperty("user.name"), Utils.getTestProperty("password"), Utils.getTestProperty("module.name"));
        Assert.assertTrue(homePage.verifyElementDisplayed(homePage.getLogoutElement()) && homePage.verifyPageTitle("Home"));
    }

    @AfterMethod(alwaysRun = true)
    public void logoutApplication() {
        try {
            Thread.sleep(10000);
            homePage.clickLogout();
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking logout");
        }
        Assert.assertTrue(loginPage.verifyPageTitle("Login"));
    }

    @AfterClass(alwaysRun = true)
    public void closeApplication() {
        driver.quit();
    }

    @AfterTest(alwaysRun = true)
    public void destroyTheObjects() {
        driver = null;
        loginPage = null;
        homePage = null;
        registerPage = null;
        detailsPage = null;
        findPatientPage = null;
    }
}
