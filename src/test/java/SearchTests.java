import org.testng.annotations.Test;
import utils.RandomDataGenerator;

public class SearchTests extends BasePage{

    @Test(testName = "ROCKETMILES Search Test", description = "We will search for hotels in Chicago")
    public void searchTest1(){

        //Initialize Search Data for test
        String rocketMilesUrl = "https://www.rocketmiles.com";
        String searchCityState = "Chicago, IL";
        String rewardsProgram = "Amazon.com Gift Card";
        String numberOfGuests = "2";
        String numberOfRooms = "1";

        //Get Rocketmiles main web page
        logInfo("Navigating to " + rocketMilesUrl);
        getPage(rocketMilesUrl);

        //Execute the Search method with above data
        RocketMilesMainPage.performSearchForHotel(searchCityState, rewardsProgram, numberOfGuests, numberOfRooms);

        //Wait until the loader goes away
        BasePage.waitForSearchResults(2000);

        //Verify that Search Results are accurate
        RocketMilesSearchResultsPage.verifySearchResults(searchCityState, rewardsProgram, numberOfGuests, numberOfRooms);


    }


    @Test(testName = "ROCKETMILES Search Test", description = "We will search for hotels in New York")
    public void searchTest2(){

        //Initialize Search Data for test
        String rocketMilesUrl = "https://www.rocketmiles.com";
        String searchCityState = "New York, NY";
        String rewardsProgram = "American Airlines AAdvantage program";
        String numberOfGuests = "2";
        String numberOfRooms = "2";

        //Get Rocketmiles main web page
        logInfo("Navigating to " + rocketMilesUrl);
        getPage(rocketMilesUrl);

        //Execute the Search method with above data
        RocketMilesMainPage.performSearchForHotel(searchCityState, rewardsProgram, numberOfGuests, numberOfRooms);

        //Wait until the loader goes away
        BasePage.waitForSearchResults(2000);

        //Verify that Search Results are accurate
        RocketMilesSearchResultsPage.verifySearchResults(searchCityState, rewardsProgram, numberOfGuests, numberOfRooms);
    }

    @Test(testName = "ROCKETMILES Create Account & Login Test", description = "We will create an account and log into Rocketmiles")
    public void createAccountTest(){

        //Initialize Search Data for test
        String rocketMilesUrl = "https://www.rocketmiles.com";
        String testEmailAddress = ("test@rocketmilesTest.com");
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String countryCode = "United States (1)";
        String phoneNumber = "1234567890";
        String currentPassword = "test000";
        String newPassword = "test123";


        //Get Rocketmiles main web page
        logInfo("Navigating to " + rocketMilesUrl);
        getPage(rocketMilesUrl);

        //Create account with Rocketmiles and verify success
        RocketMilesMainPage.signUpOnMainPage(testEmailAddress, firstName, lastName, countryCode, phoneNumber, currentPassword);

        try{
            RocketMilesMainPage.verifyAccountWasUpdated();
        }
        catch (Exception e){
            waitABit();
            RocketMilesMainPage.verifyAccountWasUpdated();
        }

    }

}
