package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Appointment_Limit extends BasePage{
	public Appointment_Limit(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//span[@class='return-button-text']")
	WebElement Return_homepage;
	
	public void clk_return_homepage()
	{
		Return_homepage.click();
	}


}
