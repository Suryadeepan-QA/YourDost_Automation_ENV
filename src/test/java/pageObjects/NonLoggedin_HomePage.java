package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
	
	//@FindBy(xpath="//button[@class='NavigationBar_loginButton__wbY8a' and text()='Login/Signup']")
	//WebElement loginbtn;
	//button[@class='NavigationBar_loginButton__wbY8a' and text()=\"Login/Signup\"]
	
	  @FindBy(xpath="//button[normalize-space()='Login/Signup']")
	    List<WebElement> loginButtons;

	
	public void clickloginbtn() throws IOException
	{

		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//wait.until(ExpectedConditions.visibilityOf(loginbtn));
		//wait.until(ExpectedConditions.elementToBeClickable(loginbtn));
		//loginbtn.click();
		//loginbtn.click();
		
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	                By.xpath("//button[normalize-space()='Login/Signup']")));

	        for (WebElement button : loginButtons) {

	            if (button.isDisplayed() && button.isEnabled()) {
	                button.click();
	                return;
	            }
	}
}}