package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cancel_Appoinments extends BasePage{
	 WebDriverWait wait;

		public Cancel_Appoinments(WebDriver driver) {
			super(driver);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		}
		
		@FindBy(xpath="//div[@class='UpcomingAppointmentSection_upcomingAppointmentCardBox__xI_7L']//div[text()='Cancel Appointment']")
		List <WebElement> link_cancel_appointment;
		
		@FindBy(xpath="//button[normalize-space()='Cancel the Appointment']")
		WebElement cancel_appoinment;
		

		
		/*public void cancelappointment() throws InterruptedException
		{
			  while (true) {

		            // Refresh the appointment list
		            link_cancel_appointment = driver.findElements(
		                    By.xpath("//div[@class='UpcomingAppointmentSection_upcomingAppointmentCardBox__xI_7L']//div[text()='Cancel Appointment']"));

		            // Exit if no appointments exist
		            if (link_cancel_appointment.isEmpty()) {
		                break;
		            }

		            // Cancel the first appointment
		            wait.until(ExpectedConditions.elementToBeClickable(link_cancel_appointment.get(0))).click();

		            // Confirm cancellation
		            wait.until(ExpectedConditions.elementToBeClickable(cancel_appoinment)).click();

		            // Wait until the popup closes
		            wait.until(ExpectedConditions.invisibilityOf(cancel_appoinment));
	        
		}
		}*/
		
		public void cancelAllAppointments() throws InterruptedException {
			while (true) {

		        // Refresh appointment list
		        link_cancel_appointment = driver.findElements(
		                By.xpath("//div[@class='UpcomingAppointmentSection_upcomingAppointmentCardBox__xI_7L']//div[text()='Cancel Appointment']"));

		        // Stop if no appointments
		        if (link_cancel_appointment.isEmpty()) {
		            break;
		        }

		        // Click first Cancel Appointment
		        wait.until(ExpectedConditions.elementToBeClickable(link_cancel_appointment.get(0))).click();

		        // Confirm cancellation
		        wait.until(ExpectedConditions.elementToBeClickable(cancel_appoinment)).click();

		        // Wait until popup disappears
		        wait.until(ExpectedConditions.invisibilityOf(cancel_appoinment));
		    }
		    }

}
