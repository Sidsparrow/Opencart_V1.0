package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.Base_Class;

public class TC001_AccountRegistrationTest extends Base_Class {

	@Test(groups = {"master" , "regression"})
	public void verify_account_registration() {
		logger.info("###########>>>>><<<<<<<STARTING TC001_AccountRegistrationTest>>>>>><<<<<#########");

		try {

			HomePage hp = new HomePage(driver);
			hp.clickmyaccount();
			logger.info("CLICKED ON MY ACCOUNT LINK");

			hp.clickRegister();
			logger.info("CLICKED ON REGISTERED LINK");

			AccountRegistrationPage arp = new AccountRegistrationPage(driver);

			logger.info("PROVIDE CUSOTMER DETAILS....");
			arp.setFirstName(randomString().substring(0, 1).toUpperCase() + randomString().substring(1).toLowerCase());
			arp.setLastName(randomString().substring(0, 1).toUpperCase() + randomString().substring(1).toLowerCase());
			arp.setEmail(randomString() + "@gmail.com");

			String pw = randomNumber();
			System.out.println("The Passswprd --> " + pw);
			arp.setPassword(pw);

			arp.isPrivacy();
			arp.isNewsLetter();
			arp.clickContinue();

			logger.info("VALIDATING EXPECTED MESSAGE...");
			String confmsg = arp.getConfirmation();
			// validation
			Assert.assertEquals(confmsg, "Your Account Has Been Created!");

		}
		
		catch (Exception e) {
			// TODO: handle exception
			logger.error("-x-x-x-x-x-x-x-TEST FAILED-x-x-x-x-x-x-x",e);
		//	logger.debug("Debugs Logs..",e);
			Assert.fail();
			
		}
		
		logger.info("###########>>>>><<<<<<<FINISHED TC001_AccountRegistrationTest>>>>>><<<<<#########");

	}

}
