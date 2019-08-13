package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


public class WebBrowser extends ReportManager {

    private String browserName;
    private WebDriver driver;
    private SoftAssert softAssert;
    private static ThreadLocal<SoftAssert> threadLocalSoftAssert = new ThreadLocal<SoftAssert>();

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<String> threadLocalBrowserName = new ThreadLocal<String>();
    private static final Logger LOGGER = Logger.getLogger(WebBrowser.class.getName());

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
    public void initWebBrowser(@Optional(value = "Chrome") String browser, Method m) {
        browserName = browser;
        if (browser.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
            LOGGER.info("Firefox has started");
        } else if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("no-sandbox");
            options.addArguments("disable-infobars");
            driver = new ChromeDriver(options);
            LOGGER.info("Chrome has started");
        }
        softAssert = new SoftAssert();
        threadLocalSoftAssert.set(softAssert);
        threadLocalDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
//		driver.manage().window().maximize();

        threadLocalBrowserName.set(browserName);
        startReporting(m);
    }

    public static SoftAssert getSoftAssert() {
        return threadLocalSoftAssert.get();
    }

    public static WebDriver Driver() {
        return threadLocalDriver.get();
    }

    public static String getCurrentBrowserName() {
        return threadLocalBrowserName.get();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebBrowser(ITestResult m) {
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            LOGGER.info("Browser closed");
        }
        stopReporting(m);
    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        closeReporter();
    }
}
