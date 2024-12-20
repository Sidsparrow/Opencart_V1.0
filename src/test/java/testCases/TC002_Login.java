package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.HomePage;
import pageObjects.MyaccountPage;
import pageObjects.loginPage;
import testBase.Base_Class;

public class TC002_Login extends Base_Class {

	@Test(groups = {"sanity", "master"})
	public void verify_login() {
		logger.info("###########>>>>><<<<<<<STARTING TC002_Login>>>>>><<<<<#########");

		try {
			//home page
			HomePage hp = new HomePage(driver);
			hp.clickmyaccount();
			logger.info("CLICKED ON MY ACCOUNT");
			hp.clickLogin();
			logger.info("CLICKED ON MY LOGIN");

			
			//login page
			loginPage lp = new loginPage(driver);
			lp.insertemailforlogin(p.getProperty("emailIdforlogin"));
			lp.insertpasswordforlogin(p.getProperty("passwordforlogin"));
			lp.clickbtnforlogin();
			logger.info("CLICKED ON LOGIN BUTTON");
			
			
			//My Account
			MyaccountPage myacc = new MyaccountPage(driver);
			logger.info("VALIDATING EXPECTED MESSAGE...");
			boolean targetpage = myacc.confirmLogin();
			if (targetpage) {
				logger.info("ACCOUNT LOGGED IN !!! ");
				Assert.assertTrue(true);
				
			} else {
				logger.error("-x-x-x-x-x-TEST FAILED-x-x-x-x-x-");
				logger.info("UNABLE TO LOG IN ");
				Assert.assertTrue(false);
			}
			
			myacc.clicklogout();
			logger.info("ACCOUNT LOGGED OUT !!!");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Test encountered an error: ", e);
			logger.debug("Debug...");
			Assert.fail();

		}

		logger.info("###########>>>>><<<<<<<FINISHED TC002_Login>>>>>><<<<<#########");
		// Ensure all assertions are validated

	}

}
