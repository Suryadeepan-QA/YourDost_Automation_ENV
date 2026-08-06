package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Contact_Mode extends BasePage{

	public Contact_Mode(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//div[@class='mode-contact']//button")
	List<WebElement> sele_video;
	
	@FindBy(xpath="//div[@class='tab-container ng-star-inserted']//button[1]")
	WebElement sele_date;
	
	@FindBy(xpath="//mat-card-content[@class='mat-mdc-card-content d-flex p-0 flow-row-wrap position-relative mx-0-5']//button[1]")
	WebElement sele_time;
	
	public void clk_video(int index)
	{
		sele_video.get(index).click();
	}
	public void clk_date()
	{
		sele_date.click();
	}
	public void clk_time()
	{
		sele_time.click();
	}
}
