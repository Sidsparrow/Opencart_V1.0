package utilities;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.logging.log4j.core.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.Base_Class;

public class ExtextReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;

	public ExtentReports extent;

	public ExtentTest test;

	String repname; // report name variable

	public void onStart(ITestContext testContext) {

		System.out.println("---Process Starts---");
		
		// to mantain history we are using naming of report jisse history rahe

		// ome way to generate time
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); //time
		 * stamp pattern Date dt = new Date(); //Date String currentTimestamp =
		 * df.format(dt); //Now to convert date according to the format
		 * 
		 */
		// Another way
		// doing it in one shot Variable mai hi kr diya saara
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp pattern
		repname = "Test-Report-"+ timeStamp +".html"; // mara outpu kuch -- >Test-Report-23:56:60.html
		System.out.println(repname);
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/"+repname);

		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); // title of report
		sparkReporter.config().setReportName("OpenCart Functional Testing"); // Name of the Report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		
		//Attaching the UI frm system data we need to mapped them 
		extent.attachReporter(sparkReporter);
		
		
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customer");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); // Get current user
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Created By ", "Siddhartha Tewari");

		// to get current OS we need to add paramter in .XML file and get the value
		String os = testContext.getCurrentXmlTest().getParameter("os"); // ye "browser" same waha XML mai hona b chayee
		extent.setSystemInfo("Operating System", os);

		// Same process to get Browser for XML
		String browsername = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browsername);

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();

		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups Includes", includedGroups.toString());

		}
	
	}

	

	public void onTestSuccess(ITestResult result) {
		System.out.println("On test success chla");
		test = extent.createTest(result.getTestClass().getName());// to get class name
		test.assignCategory(result.getMethod().getGroups()); // to display groups
		test.log(Status.PASS, "Test Case passed is " + result.getName() + "got Executed Successfully");
		System.out.println(result.getTestClass().getName() + " is passed");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("on test failure chla");
		System.out.println(result.getTestClass().getName() + " is Failed");
		// test is basically a represntative of report , report mai koi b cheez dalni
		// hai toh test ka use karo
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups
		test.log(Status.FAIL, result.getName() + "got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {

			// Base_Class bs = new Base_Class();
			// String imagepath = bs.captureScreen(result.getName());

			String imagepath = new Base_Class().captureScreen(result.getName());
			// Now this image path we need to attach in the report
			test.addScreenCaptureFromPath(imagepath);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("On test failure b chla ");
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + "Got Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
	}

	
	
	
	public void onFinish(ITestContext testContext) {
		System.out.println("Finished Providing Report on your Display  ");

		extent.flush();// wrtie all the test information from the standard repositories
		// to their output view

		// right now we are opening the report manually , click on this that
		// but now when we deploy it we can do it on Automatically

		// How to do Automatically

		String pathOfExtentReport = System.getProperty("user.dir")+"/reports/"+repname;
		System.out.println("On Finished Executed");
		File extent_report = new File(pathOfExtentReport);

		try { // Desktop.getDesktop() retrieves the Desktop instance.

			// .browse() method opens a file or URL in the default browser.
//extent_report.toURI() converts the file path into a URI that the browser can recognize.

			Desktop.getDesktop().browse(extent_report.toURI()); // opems the report automatically

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			// 1.convert path to URL
			URL url = new URL("file:///"+System.getProperty("user.dir")+"/reports/"+repname);
		//	URL url = new URL("file:///home/user/eclipse/CodesBySId/Opencart_V1.0/reports/" +repname);
			///
			// 2. Need to add java dependency

			// create the email message
			// Ek email object banaya gaya hai jo HTML content aur embedded images ko handle
			// kar sakta hai.
			ImageHtmlEmail email = new ImageHtmlEmail();

			// Yeh line ensure karti hai ki agar email ke andar koi images ya resources
			// hain, toh unhe url ke zariye resolve kiya ja sake.
			email.setDataSourceResolver(new DataSourceUrlResolver(url));

			// Yeh SMTP server ka host name set karta hai, jo email bhejne ke liye zaruri
			// hai.
			// Yahan mail.nkcprojectsinc.com SMTP provider hai.
			email.setHostName("mail.nkcprojectsinc.com");

			// SMTP port number specify kiya gaya hai. Port 465 SSL (Secure Socket Layer) ke
			// liye use hota hai.
			email.setSmtpPort(465);

			//Yeh sender ke email credentials set karta hai:
			email.setAuthenticator(new DefaultAuthenticator("siddhartha.tewari@rapidsoft.co.in", "siddhartha19"));

			// Yeh ensure karta hai ki email server ke saath connection secure (SSL) hoga.
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);

			email.setFrom("siddhartha.tewari@rapidsoft.co.in"); // sender email address
			email.setSubject("Test Result");
			email.setMsg("Please find the attached Report of Test Case...");
			//email.addTo("sushil.vishwakarma@rapidsoft.co.in"); // reciever email address
			//email.addTo("abhishek.pathak@rapidsoft.co.in");
			//email.addTo("abhishek.pathak@watsoo.com");
		//	email.addTo("nikhil.verma@rapidsoft.co.in");
			//email.addTo("poornima.verma@rapidsoft.co.in");
			//email.addTo("manoranjan.jena@rapidsoft.co.in");
			email.addTo("chandrashekhar.kumar@rapidsoft.co.in");

			

			// Email ke sath ek attachment add kiya gaya hai:
			// url: File ka URL ya location jaha report stored hai.
			// extent Report: Attachment ka naam.
			// Please check report..: Attachment ka description ya note.
			email.attach(url, "Test Report(Extent)", "Please check report..");
			email.send(); // send the email
			

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		

	}

}
