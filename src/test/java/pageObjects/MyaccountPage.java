package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class MyaccountPage extends basePage {

	//constructor
	public MyaccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	//locators
	@FindBy(xpath="//div[@id='content']/h2[contains(text(),'My Account')]") WebElement cmt_confirmlogin;
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnk_logout;
	@FindBy(xpath="//a[normalize-space()='Continue']") WebElement btn_confirm_continue;
	
	//Actions 
	public boolean confirmLogin() {
		try {
			 return(cmt_confirmlogin.isDisplayed());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	public void clicklogout()
	{
		lnk_logout.click();
		btn_confirm_continue.click();
	}
}
