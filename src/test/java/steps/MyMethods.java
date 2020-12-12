package steps;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;



public class MyMethods extends Dictionary {


    public String readFile(String path) throws IOException {

        return Files.readString(Paths.get(path));

    }

    public Object convertObject(String body) {
        return Configuration.defaultConfiguration().jsonProvider().parse(body);
    }

    public String getElementsJsonPath(Object body, String key) {
        return JsonPath.read(body, "$." + key);
    }

    public boolean xpathLocatorStatus(String locator) {
        return locator.startsWith("/") || locator.startsWith("(")|| locator.startsWith(".//");
    }


    public WebDriver getDriver() {

        try {


        WebDriver driver;
        String os = getData("os");
        String currentBrowser = getData("driver");
        switch (currentBrowser) {
            case "chrome":
                if (os.toLowerCase().contains("mac")) {
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chrome/chromedriver");
                } else if (os.toLowerCase().contains("windows")) {
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chrome/chromedriver.exe");

                }
                driver = new ChromeDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "firefox":

                if (os.toLowerCase().contains("mac")) {
                    System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/chrome/geckodriver");
                } else if (os.toLowerCase().contains("windows")) {
                    System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/chrome/geckodriver.exe");

                }

            default:
                throw new IllegalStateException("Unexpected value: " + currentBrowser);
        }
        return driver;}catch (Throwable t){
            return new FirefoxDriver();
        }

    }
    public int generateRandNum(int max){
        return ThreadLocalRandom.current().nextInt(1,max);
    }

    public String getSaveVaraible(String text){
        if(text.startsWith("{") &&text.endsWith("}")){
            text=getData(text.replace("{","").replace("}","").trim());
        }

        return text;
    }

}
