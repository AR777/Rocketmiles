import org.openqa.selenium.By;
import utils.Screenshot;

public class RocketMilesSearchResultsPage extends BasePage {


    public static void verifySearchResults(String searchCityState, String rewardsProgram, String numberOfGuests, String numberOfRooms) {
        String rewardsVerification = null;

        if (rewardsProgram =="American Airlines AAdvantage program"){
            rewardsVerification = "AAdvantage";
        }
        else if (rewardsProgram == "Amazon.com Gift Card"){
            rewardsVerification = "Amazon.com Gift Card";
        }

        logInfo("Verifying if search produced results for " + searchCityState);
        Screenshot.take();
        verifyTrue(isElementDisplayed(By.xpath("//var[contains(text(),'" + searchCityState + "')]")),
                "Expected element was not found: " + searchCityState);
        logInfo("Verifying if search produced results with " + rewardsProgram);
        verifyTrue(isElementDisplayed(By.xpath("//var[contains(text(),'" + rewardsVerification + "')]")),
                "Expected element was not found: " + rewardsProgram);

    }
}
