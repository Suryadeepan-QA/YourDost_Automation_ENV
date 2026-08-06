package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NonLoggedin_HomePage extends BasePage{

	public NonLoggedin_HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//button[@class='NavigationBar_loginButton__wbY8a' and text()=\"Login/Signup\"]")
	WebElement loginbtn;
	
	public void clickloginbtn()
	{
		loginbtn.click();
	}
}
