package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.MyaccountPage;
import pageObjects.loginPage;
import testBase.Base_Class;
import utilities.Dataproviders;

public class TC003_LoginDDT extends Base_Class {

	// (dataProvider = "loginData") ye krne kya Data providers chal jaega nahi , kyu
	// ki data provider hmari same class mai ni hai
	// abhi hmara data provider different package ke differet class mai hai uske
	// liye hame ek cheez aur likhni hogi
	// (dataProvider = "loginData", data)
	// @Test(dataProvider = "loginData" , dataProviderClass = Dataproviders.class)
	// mtlab hamne bta diya ki is class mai hai uthalo , aur uska package import
	// krlo

	@Test(dataProvider="loginData", dataProviderClass = Dataproviders.class ,groups = "ddt")
	public void verify_loginDDT(String email, String pwd, String expout) {
		logger.info("###########>>>>><<<<<<<STARTING TC003_VERIFY_LoginDDT>>>>>><<<<<#########");

		try {

			// home page
			HomePage hp = new HomePage(driver);
			hp.clickmyaccount();
			hp.clickLogin();
			logger.info("HOME PAGE PASSED");
			
			// login check
			loginPage lp = new loginPage(driver);
			lp.insertemailforlogin(email);
			lp.insertpasswordforlogin(pwd);
			lp.clickbtnforlogin();
			logger.info("CREDENTIALS ADDED");

			logger.info("ACCOUNT CHECKING!!!");

			MyaccountPage myacc = new MyaccountPage(driver);
			boolean targetpage = myacc.confirmLogin();

			// Data is valid --> login success --> Test Pass
			// Data is valid --> login failed --> Test failed

			// Data is invalid --> login sucess --> test failed
			// Data is invalid --> login failed --> test passed

			if (expout.equalsIgnoreCase("Valid")) {
				if (targetpage == true) {
					logger.info("--------------------- ");
					logger.info("VALID CASE");
					logger.info("!!!!!!!! ACCOUNT EXSIST !!!!!");
					myacc.clicklogout();
					Assert.assertTrue(true);
				} else {
					logger.info("--------------------- ");
					logger.info("VALID CASE ");
					logger.info("XXXX ACCOUNT DOES NOT EXSIST XXXX");
					Assert.assertTrue(false);
				}
			}

			if (expout.equalsIgnoreCase("Invalid")) {
				if (targetpage == true) {
					logger.info("--------------------- ");
					logger.info("INVALID CASE ");
					logger.info("!!! IDK HOW YOUR ACCOUNT EXSIST XXX ");
					myacc.clicklogout();
					Assert.assertTrue(false);
				} else {
					logger.info("--------------------- ");
					logger.info("!!!!!!!! INVALID CASE AND NO ACCOUNT !!!!!");
					Assert.assertTrue(true);
				}
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			Assert.fail();
		}
		finally {
			logger.info("--------------------- ");
		logger.info("###########>>>>><<<<<<<FINISHED TC003_VERIFY_LoginDDT>>>>>><<<<<#########");
	}	

	}

}
