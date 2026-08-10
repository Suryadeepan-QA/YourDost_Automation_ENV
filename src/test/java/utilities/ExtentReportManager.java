package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String report_name;
	
	public void onStart(ITestContext testcontext)
	{
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/myReport.html");
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		report_name= "Test-Report-" + timeStamp + ".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\" + report_name);
		sparkReporter.config().setDocumentTitle("YourDost Automation");
		sparkReporter.config().setReportName("Yourdost-Functional-Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "YourDost");
		extent.setSystemInfo("Environment", "Staging");
		extent.setSystemInfo("Framework", "Hybrid Framework");
		extent.setSystemInfo("Automation Tool", "Selenium");
		extent.setSystemInfo("Test Framework", "TestNG");
		extent.setSystemInfo("Execution", "Local");
		extent.setSystemInfo("Java", System.getProperty("java.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("QA Engineer", "Suryadeepan");
		String os=testcontext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		String browser=testcontext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
	    List<String> includedGroups=testcontext.getCurrentXmlTest().getIncludedGroups();
	    if(!includedGroups.isEmpty()) {
	    	extent.setSystemInfo("Groups", includedGroups.toString());
	    }
	}	
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed");
	   

	}
	
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try 
		{
			String image_path=new BaseClass().CaptureScreen(result.getName());
			test.addScreenCaptureFromPath(image_path);
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
	}
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+report_name;
		File extentReport=new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		/*try {
		  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+report_name);
	  
	      Create the email message 
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName("smtp.googlemail.com"); 
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password")); 
		  email.setSSLOnConnect(true);
		  email.setFrom("pavanoltraining@gmail.com"); //Sender
		  email.setSubject("Test Results");
		  email.setMsg("Please find Attached Report....");
		  email.addTo("pavankumar.busyqa@gmail.com"); //Receiver 
		  email.attach(url, "extent report", "please check report..."); 
		  email.send(); // send the email 
		}
		catch(Exception e) 
		{ 
			e.printStackTrace(); 
		  }*/
	}


}
