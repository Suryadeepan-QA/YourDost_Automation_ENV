package pageObjects;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import org.openqa.selenium.By;

public class Experts_Page extends BasePage {

    private WebDriverWait wait;

    public Experts_Page(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @FindBy(xpath="//mat-select[@name='category']")
    private WebElement clk_category;

    @FindBy(xpath="//mat-option[.//span[normalize-space()='Career']]")
    private WebElement select_category;

    @FindBy(xpath="//mat-select[@name='language']")
    private WebElement clk_language;

    @FindBy(xpath="//mat-option[.//span[normalize-space()='Hindi']]")
    private WebElement select_language;

    @FindBy(xpath="//mat-select[@name='mode']")
    private WebElement clk_mode;

    @FindBy(xpath="//mat-option[.//span[normalize-space()='Video']]")
    private WebElement select_mode;

    @FindBy(xpath="//mat-select[@name='gender']")
    private WebElement clk_gender;

    @FindBy(xpath="//mat-option[.//span[normalize-space()='Female']]")
    private WebElement select_gender;

    @FindBy(xpath="//span[normalize-space()='BOOK APPOINTMENT']")
    private WebElement clk_bookAppoinment;


    public void select_category() {

        wait.until(ExpectedConditions.elementToBeClickable(clk_category))
                .click();

        System.out.println("===== CATEGORY DROPDOWN OPENED =====");

        List<WebElement> options =
                driver.findElements(By.cssSelector("mat-option"));

        System.out.println("Option count: " + options.size());

        for (WebElement option : options) {
            System.out.println("OPTION: [" + option.getText() + "]");
        }

        System.out.println("====================================");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space()='Career']]")
        )).click();
    }



    public void select_language() {

        wait.until(ExpectedConditions.elementToBeClickable(clk_language))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(select_language))
                .click();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
    }


    public void select_mode() {

        wait.until(ExpectedConditions.elementToBeClickable(clk_mode))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(select_mode))
                .click();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
    }


    public void select_gender() {

        wait.until(ExpectedConditions.elementToBeClickable(clk_gender))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(select_gender))
                .click();

        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
    }


    public void c_bookappoinment() {

        wait.until(ExpectedConditions.elementToBeClickable(clk_bookAppoinment))
                .click();
    }
}
