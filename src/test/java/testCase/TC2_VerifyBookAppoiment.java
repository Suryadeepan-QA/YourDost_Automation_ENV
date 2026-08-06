package testCase;

import org.testng.annotations.Test;

import flows.BookingFlow;
import testBase.BaseClass;

public class TC2_VerifyBookAppoiment extends BaseClass{

	@Test(groups= {"Master"})
	public void Verify_Appoinment() throws InterruptedException
	{
		login();
		BookingFlow Ba=new BookingFlow(driver, logger);
		Ba.bookAppointment();
		logout();
		
	}

}
