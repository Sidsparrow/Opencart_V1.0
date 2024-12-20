package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v127.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends basePage{
	
	//1.Constrcutor 
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
	}
	
	
	//Locators 
	@FindBy(xpath="//span[normalize-space()='My Account']") WebElement lnkMyaccount ;
	
	@FindBy(xpath="//a[normalize-space()='Register']") WebElement lnkRegister;
	
	@FindBy(xpath="//a[normalize-space()='Login']") WebElement lnkLogin ; 
	
	
	
	//Action 
	
	public void clickmyaccount() 
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	
	public void clickLogin() 
	{
		lnkLogin.click();
	}
	
	
	
}
