package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static utils.ReportManager.logInfo;
import static utils.WebBrowser.Driver;

public class Screenshot {
    public static String take() {
        logInfo("Taking Screenshot");
        String path = "./screenshots/" + ""+ new Random().nextInt(100000) + ".png";
        TakesScreenshot screen = (TakesScreenshot) Driver();
        File source = screen.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
