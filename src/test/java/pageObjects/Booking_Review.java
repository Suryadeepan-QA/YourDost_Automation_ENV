package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Booking_Review extends BasePage{

	public Booking_Review(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath="//input[@id='phoneNumber']")
	WebElement set_phoneno;
	
	@FindBy(xpath="//mat-select[@id='category-selection']")
	WebElement clk_category;
	
	@FindBy(xpath="//span[normalize-space()='Abuse & Discrimination']")
	WebElement sele_category;
	
	@FindBy(xpath="//mat-select[@id='sub-category-selection']")
	WebElement clk_subcategory;
	
	@FindBy(xpath="//span[normalize-space()='Gender-based trauma & discrimination']")
	WebElement sele_subcategory;
	
	@FindBy(xpath="//mat-select[@formcontrolname='city']")
	WebElement clk_city;
	
	@FindBy(xpath="//span[normalize-space()='Bangalore']")
	WebElement sele_city;
	
	@FindBy(id="mat-mdc-checkbox-1")
	WebElement sele_checkbox;
	
	@FindBy(xpath="//span[normalize-space()='BOOK APPOINTMENT']")
	WebElement Bookappoinment;
	
	@FindBy(xpath="//input[@id='otp-book-input']")
	WebElement set_otp;
	
	@FindBy(xpath="//button[normalize-space()='VALIDATE']")
	WebElement otp_validatebtn;
	
	public void select_category()
	{
		clk_category.click();
		sele_category.click();
	}
	public void select_subcategory()
	{
		clk_subcategory.click();
		sele_subcategory.click();
	}
	public void select_city()
	{
		clk_city.click();
		sele_city.click();
	}
	public void clk_checkbox()
	{
		sele_checkbox.click();
	}
	public void clk_Bookappoinment()
	{
		Bookappoinment.click();
	}
	public void validate_otp()
	{
		set_otp.sendKeys("      ");
		otp_validatebtn.click();
	}
}
