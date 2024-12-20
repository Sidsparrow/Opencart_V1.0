package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends basePage {

	// constructor
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// locators
	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txt_firstname;
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txt_lastName;
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txt_eMailfield;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txt_password;
	@FindBy(xpath = "//input[@name='agree']")
	WebElement chk_privacycheck;

	@FindBy(xpath = "//input[@id='input-newsletter']")
	WebElement chk_newslettercheck;
	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement btn_registerbutton;
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement cmt_confirmcreate;

	// Action
	public void setFirstName(String fname) {
		txt_firstname.sendKeys(fname);
	}

	public void setLastName(String lname) {

		txt_lastName.sendKeys(lname);
	}

	public void setEmail(String email) {
		txt_eMailfield.sendKeys(email);
	}

	public void setPassword(String passwd) {
		txt_password.sendKeys(passwd);
	}

	public void isPrivacy() {
		chk_privacycheck.click();
	}

	public void isNewsLetter() {
		chk_newslettercheck.click();
	}

	public void clickContinue() {
		btn_registerbutton.click();
		
		//sol1
		//btn_registerbutton.submit();
		
		//sol2
		//Actions act = new Actions(driver);
		//act.moveToElement(btn_registerbutton).click().perform();
		
		//sol3
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click();", btn_registerbutton);
		
		//sol4
		//btn_registerbutton.sendKeys(Keys.RETURN);
		
		
	}
	
	public String getConfirmation() {
		
		try {
			return (cmt_confirmcreate.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
}
