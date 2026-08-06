package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Booking_Confirmation extends BasePage{

	public Booking_Confirmation(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='Appointment Confirmed!']")
	WebElement Ap_confirm;
	
	@FindBy(xpath="//button[@id='skip-confirm-button']")
	WebElement skip_btn;
	
	@FindBy(xpath="//button[normalize-space()='GO TO HOMEPAGE']")
	WebElement goto_homebtn;

	public boolean Appoinmentconfirm_isdisplay()
	{
		return Ap_confirm.isDisplayed();
	}
	public void clk_skipbtn()
	{
		skip_btn.click();
	}
	public void clk_gohomebtn()
	{
		goto_homebtn.click();
	}
}
