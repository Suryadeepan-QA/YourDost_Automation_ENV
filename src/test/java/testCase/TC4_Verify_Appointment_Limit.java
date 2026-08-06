package testCase;

import org.testng.annotations.Test;

import flows.BookingFlow;
import pageObjects.Appointment_Limit;
import pageObjects.Cancel_Appoinments;
import pageObjects.User_Dashboard;
import testBase.BaseClass;

public class TC4_Verify_Appointment_Limit extends BaseClass {
	@Test
	public void appoinment_limit() throws InterruptedException
	{
		
		login();

		Cancel_Appoinments ca = new Cancel_Appoinments(driver);
		ca.cancelAllAppointments();
		
		BookingFlow Ba=new BookingFlow(driver, logger);
		Ba.bookAppointment();
		Ba.bookAppointment();
				
		logger.info("--- User on logged in home page click on book appoinment ---");
		User_Dashboard ud=new User_Dashboard(driver);
		ud.btnappoinment();
		
		Appointment_Limit Al=new Appointment_Limit(driver);
		Al.clk_return_homepage();
		
		logger.info("--- User Cancelled the appoinments ---");
		Cancel_Appoinments Ca=new Cancel_Appoinments(driver);
		Ca.cancelAllAppointments();
		
		logout();
		logger.info("--- User Logged Out ---");
		
	}
 
}
