package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login_Popup extends BasePage{

	public Login_Popup(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@name=\'emailOrUsername\']")
	WebElement textusername;
	
	@FindBy(xpath="//input[@name=\'password\']")
	WebElement textpassword;
	
	@FindBy(xpath="//button[normalize-space()='LOGIN']")
	WebElement loginbtn;
	
	
	public void setusername(String username)
	{
		textusername.sendKeys(username);
	}
	public void setpassword(String password)
	{
		textpassword.sendKeys(password);
	}
	public void clickloginbtn()
	{
		loginbtn.click();
	}
}
