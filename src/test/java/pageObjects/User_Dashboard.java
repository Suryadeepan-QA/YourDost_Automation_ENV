package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class User_Dashboard extends BasePage{

	public User_Dashboard(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//div[@class='OrganizationOfferings_cardsContainer__WJzhf']/div[1]/button[1]")
	WebElement clkbookappoinment; 
	
	@FindBy(xpath="//div[text()=' Select your Expert and we will book the appointment for you. ']")
	WebElement sele_expert;
	
	
	public void btnappoinment() {
		clkbookappoinment.click();
	}
	
	public void selecontinue_btn(){
		sele_expert.click();
	}
	
}
