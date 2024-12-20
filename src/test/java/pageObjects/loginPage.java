package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class loginPage extends basePage {

	
	//Constructor 
	public loginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	//Locators 
	@FindBy(xpath="//input[@id='input-email']")  WebElement txt_emailadd ;
	
	@FindBy(xpath="//input[@id='input-password']")  WebElement txt_password;
	
	@FindBy(xpath="//button[normalize-space()='Login']")  WebElement btn_login;

	
	//Action
	
	public void insertemailforlogin(String emailladdr) 
	{
	txt_emailadd.sendKeys(emailladdr);	
	}
	
	public void insertpasswordforlogin(String paswd) 
	{
		txt_password.sendKeys(paswd);
	}
	
	public void clickbtnforlogin() {
		btn_login.click();
	}
	
	
}
