package testCase;

import org.testng.annotations.Test;

import pageObjects.Login_Popup;
import pageObjects.Logout_Page;
import pageObjects.NonLoggedin_HomePage;
import testBase.BaseClass;

public class TC1_VerifyUserLogin extends BaseClass {
	@Test
	public void Verify_login()
	{
		logger.info("----- I click on the home page login/signup button ------");
		NonLoggedin_HomePage Hp=new NonLoggedin_HomePage(driver);
		Hp.clickloginbtn();
		
		logger.info("------ Enter the user credentials ------");
		Login_Popup Lp=new Login_Popup(driver);
		Lp.setusername(p.getProperty("username"));
		Lp.setpassword(p.getProperty("password"));
		Lp.clickloginbtn();
		logger.info("--- User loggedin and redirected to the dashboard page ----");
		
		logger.info("--- User click on the logout button ---");
		Logout_Page Pl=new Logout_Page(driver);
		Pl.logout_session();
		logger.info("--- User Logged Out ---");
	}
	
 
}
