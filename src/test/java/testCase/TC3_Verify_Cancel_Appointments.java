package testCase;

import org.testng.annotations.Test;

import pageObjects.Cancel_Appoinments;
import pageObjects.Login_Popup;
import pageObjects.Logout_Page;
import pageObjects.NonLoggedin_HomePage;
import testBase.BaseClass;

public class TC3_Verify_Cancel_Appointments extends BaseClass{

	@Test(groups= {"Functional"})
	public void cancel_appoinments() throws InterruptedException
	{
		NonLoggedin_HomePage Hp=new NonLoggedin_HomePage(driver);
		Hp.clickloginbtn();
		
		logger.info("--- User enter the credentails on login page ---");
		Login_Popup Lp=new Login_Popup(driver);
		Lp.setusername(p.getProperty("username"));
		Thread.sleep(1000);
		Lp.setpassword(p.getProperty("password"));
		Thread.sleep(1000);
		Lp.clickloginbtn();
		
		logger.info("--- User on logged in home page click on book appoinment ---");
		Cancel_Appoinments Ca=new Cancel_Appoinments(driver);
		Ca.cancelAllAppointments();
		
		Logout_Page Pl=new Logout_Page(driver);
		Pl.logout_session();
	}

}
