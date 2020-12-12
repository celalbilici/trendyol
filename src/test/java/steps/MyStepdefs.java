package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MyStepdefs extends Dictionary {


    private final MyMethods myMethods;
    WebDriver driver = new MyMethods().getDriver();
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    Actions actions = new Actions(driver);
    WebDriverWait wait = new WebDriverWait(driver, 20);

    public MyStepdefs(MyMethods myMethods) {
        this.myMethods = myMethods;
    }


    @Given("^i visit ([\\pL\\d]+(?: [\\pL\\d]+)*)")
    public void openPage(String pageKey) {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(getElement("baseUrl." + pageKey));
        driver.manage().window().maximize();

    }

    @Given("^i click ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void clickElement(String elementKey) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement(elementKey))).click();

    }


    @And("i click on the {string}")
    public void clickText(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[normalize-space(text())='" + text + "']"))).click();

    }

    @Given("^press ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void pressKey(String key) {

        actions.sendKeys(Keys.valueOf(key.toUpperCase())).perform();

    }

    @And("^hover ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void hoverElemeent(String elementKey) {
        actions.moveToElement(driver.findElement(webElement(elementKey)));
    }

    @Given("^save the value of ((?:\"[^\"]*\")+) as ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void saveVaraible(String value, String key) {
        setData(key, value);

    }

    @After()
    public void quitBrowser() {
        driver.quit();
    }

    @And("wait {int} seconds")
    public void waitSeconds(int seconds) throws InterruptedException {

        Thread.sleep(seconds * 1000L);
    }

    @And("check images")
    public void checkImages() {

        String key = getElement("elements.images");

        for (int i = 0; i < 1000; i++) {
            pressKey("DOWN");
        }

        int size = driver.findElements(By.xpath(key)).size();

        for (int i = 1; i <= size; i++) {

            WebElement image = driver.findElement(By.xpath("(" + key + ")[" + i + "]"));


            if ((Boolean) js.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image)) {

                System.out.println(image.getAttribute("alt") + "   resim başarıyla yüklendi");
            } else {
                try {
                    System.out.println(image.getAttribute("alt") + "   resim yüklenemedi");
                } catch (Throwable t) {
                    System.out.println("element: (" + key + ")[" + i + "] -- " + t);
                }
            }

        }

    }


    public void waitForAjax() {

        for (int s = 0; s < 20; s++) {
            try {


                if ((Boolean) js.executeScript("jQuery.active == 0")) {
                    break;
                }
                Thread.sleep(2000);
            } catch (Throwable t) {

            }
        }
    }


    @And("run this {string} javascript command")
    public void runThisJavascript(String script) {
        js.executeScript(script);
    }

    @And("save random number less than (\\d+) as ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void generateRandomNum(int number, String varaibleKey) {

        setData(varaibleKey, String.valueOf(this.myMethods.generateRandNum(number)));

    }


    @And("save {string} {string} value as {string}")
    public void saveAttributeValue(String elementKey, String attribute, String varaibleKey) {
        setData(varaibleKey, driver.findElement(webElement(elementKey)).getAttribute(removeQuotes(attribute)));
    }

    public By webElement(String elementKey) {
        String element = getElement("elements." + elementKey);
        boolean type = this.myMethods.xpathLocatorStatus(element);

        if (type) {
            return By.xpath(element);
        } else {
            return By.cssSelector(element);
        }


    }

    @And("type {string}")
    public void type(String text) {
        actions.sendKeys(text).perform();
    }

    public boolean checkText(String text) {
        return driver.getPageSource().contains(text);
    }

    @And("see {string} in {int} seconds")
    public void see(String text, int seconds) throws Exception {
        text = this.myMethods.getSaveVaraible(text);
        for (int i = 0; i <= seconds; i++) {
            if (!checkText(text)) {
                waitSeconds(1);
                continue;
            } else if (i == seconds) {
                System.out.println("expect:have text-- but page has no :" + text);
            }
            break;
        }

    }

    @And("do not see {string} in {int} seconds")
    public void doNotSee(String text, int seconds) throws Exception {
        text = this.myMethods.getSaveVaraible(text);
        for (int i = 0; i <= seconds; i++) {
            if (!checkText(text)) {
                break;
            } else if (i == seconds) {
                System.out.println("expect:has no text-- but page have text :" + text);
            }
            waitSeconds(1);
        }
    }


    @And("i see ([\\pL\\d]+(?: [\\pL\\d]+)*) in (\\d+) seconds$")
    public void seeElement(String elementKey, int seconds) throws Exception {

        try {
            for (int i = 0; i <= seconds; i++)
                if (wait.until(ExpectedConditions.elementToBeClickable(webElement(elementKey))).isDisplayed()) {
                    break;
                } else if (i == seconds) {
                    throw new Exception("couldnt find " + elementKey);
                }
            waitSeconds(1);
        } catch (Throwable t) {
            System.out.println(t);
        }

    }

    @And("do not see ([\\pL\\d]+(?: [\\pL\\d]+)*) in (\\d+) seconds$")
    public void doNotSeeElement(String elementKey, int seconds) throws Exception {

        try {
            for (int i = 0; i <= seconds; i++) {
                if (wait.until(ExpectedConditions.elementToBeClickable(webElement(elementKey))).isDisplayed()) {
                    waitSeconds(1);
                    continue;
                }
                break;
            }

        } catch (Throwable t) {
            System.out.println(t);
        }

    }

    @And("^save ([\\pL\\d]+(?: [\\pL\\d]+)*) element text as ([\\pL\\d]+(?: [\\pL\\d]+)*)$")
    public void saveSElementText(String elemetKey, String varaibleKey) {
        setData(varaibleKey, driver.findElement(webElement(elemetKey)).getText());
    }
}
