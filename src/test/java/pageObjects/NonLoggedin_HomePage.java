package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BaseClass;

public class NonLoggedin_HomePage extends BasePage{

	public NonLoggedin_HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//button[@class='NavigationBar_loginButton__wbY8a' and text()='Login/Signup']")
	WebElement loginbtn;
	//button[@class='NavigationBar_loginButton__wbY8a' and text()=\"Login/Signup\"]
	
	public void clickloginbtn() throws IOException
	{

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    wait.until(ExpectedConditions.elementToBeClickable(loginbtn)).click();
		//loginbtn.click();
	}
}
