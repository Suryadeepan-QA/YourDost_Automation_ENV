package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Logout_Page extends BasePage{

	public Logout_Page(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//div[@class='DesktopNavBar_nameText__hAqgn']")
	WebElement usericon;
	
	@FindBy(xpath="//li[normalize-space()='Logout']")
	WebElement btn_logout;

	public void logout_session()
	{
		usericon.click();
		btn_logout.click();
	}
}
