package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout_Page extends BasePage{

    WebDriverWait wait;

	public Logout_Page(WebDriver driver) {
		super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}
	
	@FindBy(xpath="//div[contains(@class,'DesktopNavBar_usernameContainer')]")
	//div[@class='DesktopNavBar_nameText__hAqgn']
	WebElement usericon;
	@FindBy(xpath="//li[normalize-space()='Logout']")
	WebElement btn_logout;

	public void logout_session()
	{
		//usericon.click();
		//btn_logout.click();
		   wait.until(ExpectedConditions.elementToBeClickable(usericon)).click();
		   wait.until(ExpectedConditions.elementToBeClickable(btn_logout)).click();
	}
}
