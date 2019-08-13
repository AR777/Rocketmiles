import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebBrowser;
import utils.Screenshot;

import java.util.List;

public class BasePage extends WebBrowser {

        private static final int WAITTIMEOUT = 30;
        private static final Logger LOGGER = Logger.getLogger(RocketMilesMainPage.class.getName());


    /**
     * Method leads to the target page
     * @param pageAddress - url of target page
     */
    public static void getPage(String pageAddress) {
        try {
            Driver().get(pageAddress);
            waitForPageLoad(WAITTIMEOUT);
            Logger().log(LogStatus.PASS, "Redirected to '" + pageAddress + "'");
            LOGGER.info(  "Redirected to '" + pageAddress + "'");
        } catch (Exception e) {
            Logger().log(LogStatus.INFO, "Redirecting to '" + pageAddress + "'");
            LOGGER.info("Redirecting to '" + pageAddress + "'");
            Logger().log(LogStatus.FAIL, "Page is not available" + Logger().addScreenCapture(Screenshot.take()));
            LOGGER.error("Page is not available");
            e.printStackTrace();
            getSoftAssert().fail(pageAddress);
        }
    }

        /**
         * Method waits while page is loading
         * @param timeout expected timeout
         */
        public static void waitForPageLoad(long timeout) {
            new WebDriverWait(Driver(), timeout).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                    .executeScript("return document.readyState").equals("complete"));
        }

        public static void waitForSearchResults(long timeout){

            String loader = "//div[@class='search-transition']";

            new WebDriverWait(Driver(), timeout).until((ExpectedCondition<Boolean>) wd -> (Driver().findElements(By.xpath(loader)).size() == 0));
        }


        /**
         * Method is waiting for element
         * @param by - selector of target element
         * @return WebElement that we need
         */
        public static WebElement getEl(By by) {
            LOGGER.info("Trying to get element Selector: '" + by.toString() + "'");
            WebElement element = null;
            WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
            try {
                element = wait.until(ExpectedConditions.elementToBeClickable(by));
                LOGGER.info("Gotten element Selector: '" + by.toString() + "'");
            } catch (Exception e) {
                LOGGER.info("Element " + by.toString() + " not received");
                e.printStackTrace();
            }
            return element;
        }

        /**
         * Method gets text value of target element
         * @param by - selector of target element
         * @return String expected value
         */
        private static String getValue(String by) {
            LOGGER.info("Trying to get label for element Selector: '" + by.toString() + "'");
            String value = "";
            value = getEl(By.xpath(by)).getText();
            LOGGER.info("Gotten element Selector: '" + by.toString() + "'");
            return value;
        }

        /**
         * Method gets invisible element
         * @param by - selector of target element
         * @return expected element
         */
        private static WebElement getInvisibleElement(By by) {
            LOGGER.info("Trying to get element Selector: '" + by.toString() + "'");
            WebElement element = null;
            WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LOGGER.info("Gotten element Selector: '" + by.toString() + "'");
            return element;
        }

        /**
         * Method returns target element
         * @param by - selector of the target element
         * @return WebElement - expected element
         */
        public static WebElement getElement(By by) {
            WebElement element = null;
            try {
                element = getEl(by);
                LOGGER.info("Gotten element Selector: '" + by.toString() + "'");
            } catch (Exception e) {
                Logger().log(LogStatus.INFO, "Trying to get element Selector: '" + by.toString() + "'");
                LOGGER.info("Trying to get element Selector: '" + by.toString() + "'");
                Logger().log(LogStatus.FAIL,
                        "Cannot get element " + Logger().addScreenCapture(Screenshot.take()) + e.getCause());
                LOGGER.error("Cannot get element Selector: '" + by.toString() + "'");
                e.printStackTrace();
            }
            return element;
        }

        /**
         * Method returns list of WebElements
         * @param by - selector of target list of elements
         * @return list of WebElements
         */
        public static List<WebElement> getElements(By by) {
            List<WebElement> elements = null;
            try {
                WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
                elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
//			elements = Driver().findElements(by);
                LOGGER.info("Gotten elements Selector: '" + by.toString() + "'");
            } catch (Exception e) {
                logInfo("Trying to get element Selector: '" + by.toString() + "'");
                LOGGER.info("Trying to get element Selector: '" + by.toString() + "'");
                logStatusFailWithScreenshot("Cannot get elements", e);
                LOGGER.error("Cannot get element Selector: '" + by.toString() + "'");
                e.printStackTrace();
            }
            return elements;
        }

        /**
         * Method verifies the condition
         * @param verification - condition that must be verified
         */
        public static void verifyTrue(boolean verification, String message) {
            LOGGER.info("Verification method is invoked.");
            if (verification) {
                Logger().log(LogStatus.PASS, "TRUE");
                LOGGER.info("Verification status: TRUE");
            } else {
                Logger().log(LogStatus.FAIL, "FALSE. " + message  + Logger().addScreenCapture(Screenshot.take()));
                LOGGER.info("Verification status: FALSE");
                getSoftAssert().fail(message);
            }
        }

        /**
         * Method checks if element is displayed
         * @param by - selector of current element
         * @return boolean result clickable or not
         */
        public static boolean isElementDisplayed(By by) {
            WebElement element = null;
            try {
                element = getEl(by);
                if (element.isDisplayed())
                    return true;
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        /**
         * Method checks if element exists
         * @param by selector of current element
         * @return boolean result of checking
         */
        public static boolean isExist(By by) {
            WebElement element = null;
            try {
                element = getInvisibleElement(by);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Method is verifying all asserts
         */
        public static void verifyAll() {
            getSoftAssert().assertAll();
        }

        // Deprecated
        public void scrollUpUsingJS() {
            ((JavascriptExecutor) Driver()).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
        }

        /**
         * Waits to element disappears
         * @param by - selector of target element
         */
        public static void waitForElementNotPresent(By by) {
            WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
            waitForElement(by);
            waitForElementToDisappear(by);
        }

        /**
         * Waiting for for element and return it
         * @param element - selector of target element
         * @return WebElement or null if element was not found
         */
        public static WebElement waitForElement(By element){
            for(long currentTime=0 ; currentTime <  WAITTIMEOUT * 5; currentTime++){
                waitABit();
                try {
                    if (Driver().findElements(element).get(0).isDisplayed()) {
                        LOGGER.info("Element was found");
                        return Driver().findElements(element).get(0);
                    }
                } catch (Exception e){
                    //e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * Waits to element disappears
         * @param element - selector of target element
         */
        public static void waitForElementToDisappear(By element){
            for(long currentTime=0 ; currentTime <  WAITTIMEOUT * 5; currentTime++){
                waitABit();
                try {
                    if (Driver().findElements(element).size() == 0 || !Driver().findElements(element).get(0).isDisplayed()) {
                        LOGGER.info("Element disappeared");
                        return;
                    }
                } catch (Exception e){
                    //e.printStackTrace();
                }
            }
            return;
        }

        /**
         * Waits 200 milliseconds
         */
        public static void waitABit(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    public static void scrollIntoView(WebElement element){
        ((JavascriptExecutor) WebBrowser.Driver()).executeScript("arguments[0].scrollIntoView()", element);
    }

}
