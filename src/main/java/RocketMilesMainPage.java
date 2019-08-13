import org.jsoup.Connection;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.RandomDataGenerator;
import utils.WebBrowser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static utils.RandomDataGenerator.randomize;

public class RocketMilesMainPage extends BasePage {

    public static By checkInDateInput = By.xpath("//input[@placeholder='Check in']");

    public static By checkOutDateInput = By.xpath("//input[@placeholder='Check out']");

    public static By numberOfGuestsInput = By.xpath("//div[@ng-model='searchForm.params.adults']//button");

    public static By numberOfRoomsInput = By.xpath("//div[@ng-model='searchForm.params.rooms']//button");

    public static By submitButton = By.xpath("//button[@type='submit']");

    public static By cookiesDialogueCloseButton = By.xpath("//button[text()='OK']");

    public static By createAccountDialogueCloseButton = By.xpath("//div[@class='modal fade ng-isolate-scope user-email-modal in']//button[@class ='close']");

    public static By accountDropDown = By.xpath("//a[contains(@class,'btn btn-black btn-account')]");

    public static By firstNameInput = By.xpath("//input[contains(@name,'firstName')]");

    public static By lastNameInput = By.xpath("//input[contains(@name,'lastName')]");

    public static By phoneNumberInput = By.xpath("//input[contains(@name,'lineNumber')]");

    public static By allowEmailMarketingCheckbox = By.xpath("//label[@for='allowEmailMarketing']");

    public static By addCompanyProfileToReceiptCheckbox = By.xpath("//label[@for='addCompanyProfileToReceipt']");

    public static By existingPasswordInput = By.xpath("//input[contains(@name,'currentPassword')]");

    public static By newPasswordInput = By.xpath("//input[contains(@name,'password')]");

    public static By confirmPasswordInput = By.xpath("//input[contains(@name,'confirmPassword')]");

    public static By saveChangesButton = By.xpath("//input[contains(@value,'Save Changes')]");

    public static By successUpdatingAccount = By.xpath(".//li[text()='Successfully updated account.']");



    /**
     * enters the search data and submits for Hotel Room Search
     */
    public static void performSearchForHotel(String city, String rewardsProgram, String numberOfGuests, String numberOfRooms) {
        WebDriver webDriver = WebBrowser.Driver();
        WebElement cityDropdown = webDriver.findElement(By.xpath("//input[@placeholder='Where do you need a hotel?']"));
        WebElement rewardsDropdown = webDriver.findElement(By.xpath("//input[@placeholder='Select reward program']"));

        //clear the Cookies Disclaimer popup
        waitForElement(cookiesDialogueCloseButton).click();

        //clear the Sign Up popup
        waitForElement(createAccountDialogueCloseButton).click();
        waitForElementToDisappear(createAccountDialogueCloseButton);

        //Selects 'Chicago, IL' as City
        new Actions(webDriver)
                .moveToElement(cityDropdown)
                .click()
                .pause(500)
                .build()
                .perform();

        waitForElement(By.xpath(".//*[text()='" + city + "']"));

        new Actions(webDriver)
                .moveToElement(webDriver.findElement(By.xpath(".//*[text()='" + city + "']")))
                .click()
                .build()
                .perform();


        //Enters 'Amazon Gift Card' as Rewards Program
        new Actions(webDriver)
                .moveToElement(rewardsDropdown)
                .click()
                .pause(500)
                .build()
                .perform();

        waitForElement(By.xpath(".//*[text()='" + rewardsProgram + "']"));

        new Actions(webDriver)
                .moveToElement(webDriver.findElement(By.xpath(".//*[text()='" + rewardsProgram + "']")))
                .click()
                .build()
                .perform();

        //Selects tomorrow's date as Check In Date
        BasePage.getElement(checkInDateInput).click();
        BasePage.getElement(By.xpath("//a[text()='" + tomorrowsDate() + "']")).click();

        //Selects next week's date as Check Out Date
        BasePage.getElement(checkOutDateInput).click();
        BasePage.getElement(By.xpath("//a[text()='" + weekAfterDate() + "']")).click();

        //Enters Number of Guests
        BasePage.getElement(numberOfGuestsInput).click();

        //Enters Number of Rooms
        BasePage.getElement(numberOfRoomsInput).sendKeys(numberOfRooms);

        //Clicks 'Submit' button
        BasePage.getElement(submitButton).click();

        BasePage.waitForPageLoad(1000);
    }

    public static void signUpOnMainPage(String testEmailAddress,String firstName,String lastName,String countryCode,String phoneNumber,String currentPassword) {

        String newPassword = randomize("1234567");

        //clear the Cookies Disclaimer popup
        waitForElement(cookiesDialogueCloseButton).click();

        //enter user's email address and Sign Up
        BasePage.getElement(By.xpath("//div[@class='modal fade ng-isolate-scope user-email-modal in']//input[@placeholder ='Your Email Address']")).sendKeys(randomize(testEmailAddress));
        BasePage.getElement(By.xpath("//div[@class='modal fade ng-isolate-scope user-email-modal in']//button[@type ='submit']")).click();
        waitForElementToDisappear((By.xpath("//div[@class='modal fade ng-isolate-scope user-email-modal in']//button[@type ='submit']")));

        //select profile from drop down and fill in additional user's information
        waitForElement(accountDropDown).click();

        //Fill out the Profile Form and click 'Save Changes'
        BasePage.getElement(firstNameInput).sendKeys(firstName);
        BasePage.getElement(lastNameInput).sendKeys(lastName);
        //select US as Country Code for phone #
        BasePage.getElement(By.xpath("//div[@name='countryCode']//button")).click();
        BasePage.getElement(By.xpath("//a[text()='" + countryCode + "']")).click();
        //randomize phone number and enter it
        BasePage.getElement(phoneNumberInput).sendKeys(randomize(phoneNumber));
        BasePage.getElement(allowEmailMarketingCheckbox).click();
        BasePage.getElement(addCompanyProfileToReceiptCheckbox).click();
        //randomize and select passwords
        BasePage.getElement(existingPasswordInput).sendKeys(currentPassword);
        BasePage.getElement(newPasswordInput).sendKeys(newPassword);
        BasePage.getElement(confirmPasswordInput).sendKeys(newPassword);
        //Clicking Submit
        BasePage.scrollIntoView(WebBrowser.Driver().findElement(By.xpath("//input[@type='submit']")));
        BasePage.getElement(saveChangesButton).click();
        waitForPageLoad(2000);
    }

    /**
     * returns the number of day of the month for tomorrow
     */
    public static int tomorrowsDate() {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
        int effectiveDate = localDate.getDayOfMonth();
        return effectiveDate;
    }

    /**
     * returns the number of day of the month for a week from today
     */
    public static int weekAfterDate() {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(7);
        int effectiveDate = localDate.getDayOfMonth();
        return effectiveDate;
    }

    /**
     * returns the number of day of the month for a week from today
     */
    public static void closeCookiesDialogueBox() {
        BasePage.getElement(By.xpath("//button[text()='OK']")).click();
    }

    /**
     * returns the number of day of the month for a week from today
     */
    public static void closeJoinRocketmilesDialogueBox() {
        BasePage.getElement(By.xpath("//div[@class='modal fade ng-isolate-scope user-email-modal in']//button[@class ='close']")).click();
    }


    /**
     * verify account was created by finding Success message on screen
     */
    public static void verifyAccountWasUpdated() {
        BasePage.scrollIntoView(WebBrowser.Driver().findElement(successUpdatingAccount));
        Assert.assertTrue("account was not created", BasePage.isElementDisplayed(successUpdatingAccount));
    }
}
