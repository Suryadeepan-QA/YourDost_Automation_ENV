package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Experts_Page extends BasePage{
	public Experts_Page(WebDriver driver) {
		super(driver);
	}
	
	//@FindBy(xpath="//input[@placeholder='Search Expert by Name' or @id=\"mat-input-0\"]")
	//WebElement search_field;
	
	@FindBy(xpath="//mat-select[@name='category']")
	WebElement clk_category;
	
	@FindBy(xpath="//span[normalize-space()='Career']")
	WebElement select_category;
	
	@FindBy(xpath="//mat-select[@name='language']")
	WebElement clk_language;
	
	@FindBy(xpath="//span[normalize-space()='Hindi']")
	WebElement select_language;
	
	@FindBy(xpath="//mat-select[@name='mode']")
	WebElement clk_mode;
	
	@FindBy(xpath="//span[normalize-space()='Video']")
	WebElement select_mode;
	
	@FindBy(xpath="//mat-select[@name='gender']")
	WebElement clk_gender;
	
	@FindBy(xpath="//span[normalize-space()='Female']")
	WebElement select_gender;
	
	@FindBy(xpath="//span[normalize-space()='BOOK APPOINTMENT']")
	WebElement clk_bookAppoinment; 
	
	public void select_category()
	{
		clk_category.click();
		select_category.click();
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
	}

	public void select_language()
	{
		clk_language.click();
		select_language.click();
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
	}
	
	public void select_mode()
	{
		clk_mode.click();
		select_mode.click();
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
	}
	
	public void select_gender()
	{
		clk_gender.click();
		select_gender.click();
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
	}
	
	public void c_bookappoinment()
	{
		clk_bookAppoinment.click();
	}



}
