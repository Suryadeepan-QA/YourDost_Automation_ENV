package flows;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.Booking_Confirmation;
import pageObjects.Booking_Review;
import pageObjects.Contact_Mode;
import pageObjects.Experts_Page;
import pageObjects.User_Dashboard;
import testBase.BaseClass;

public class BookingFlow {
	
	private WebDriver driver;
    private Logger logger;
    
    public BookingFlow(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
    }
	
	public void bookAppointment()
	{
		try
		{
		logger.info("--- User on logged in home page click on book appoinment ---");
		User_Dashboard ud=new User_Dashboard(driver);
		ud.btnappoinment();
		
		logger.info("--- user select the mode of experts---");
		ud.selecontinue_btn();	
		
		logger.info("--- user select the experts and modes ---");
		Experts_Page Ep=new Experts_Page(driver);
		Ep.select_category();
		//Thread.sleep(1000);
		Ep.select_language();
		//Thread.sleep(1000);
		Ep.select_mode();
		//Thread.sleep(1000);
		Ep.select_gender();
		//Thread.sleep(1000);
		Ep.c_bookappoinment();
		//Thread.sleep(1000);
		
		logger.info("--- select mode of contact ---");
		Contact_Mode Cm=new Contact_Mode(driver);
		Cm.clk_video(0);
		Cm.clk_date();
		//Thread.sleep(1000);
        Cm.clk_time();
		//Thread.sleep(1000);
		
		logger.info("--- Booking review ---");
		Booking_Review Br=new Booking_Review(driver);
		Br.select_category();
		//Thread.sleep(1000);
		Br.select_subcategory();
		//Thread.sleep(1000);
		Br.select_city();
		//Thread.sleep(1000);
		Br.clk_checkbox();
		//Thread.sleep(1000);
		Br.clk_Bookappoinment();
		
		
		Booking_Confirmation Bc=new Booking_Confirmation(driver);
		Assert.assertTrue(Bc.Appoinmentconfirm_isdisplay(), "Appoinment confirmation is not displayed");
		BaseClass.CaptureScreen("Appoinment Confirmation");
		//Thread.sleep(1000);
		Bc.clk_skipbtn();
		Bc.clk_gohomebtn();
		Thread.sleep(1000);
		logger.info("--- Appoinment Booked ---");
		
		BaseClass.CaptureScreen("Upcoming Video Appointments");
		logger.info("--- Home page screenshot taken ");

		}
		catch(Exception e)
		{
			logger.error("Test failed");
			logger.debug("Debug logs..");
			Assert.fail("Test failed because: " + e.getMessage());
		}
		logger.info("--- Script Ended ---");
	}

}
