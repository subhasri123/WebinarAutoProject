package webinar;

import org.openqa.selenium.*;
import org.testng.xml.XmlTest;
/*
 * This class is to launch the GoToMeeting web page and login with the credentials provided
 * in testng.xml
 */
public class loginWebinar {
     
	public String emailId = "emailAddress";
	public String startTrial = ".//input[@value='Start My 30-Day Free Trial']";
	public String loginFirstName = ".//input[@name='firstName']";
	public String lastName = ".//input[@name='lastName']";
	public String emailAddr = "formUserId";
	public String pwd = "password";
	public String promoCode = "PromoCode";
	public String submit = "submit";
	
	public WebDriver webdriver;
	public String url;
	public String emailIdValue;
	public String pwdValue;
	
	
	public loginWebinar(WebDriver webdriver){
		this.webdriver = webdriver;
	}
	
	public void launchGoToMeeting(XmlTest config) throws Exception{
		
		url = config.getParameter("productUrl");
		emailIdValue = config.getParameter("email");
		pwdValue = config.getParameter("password");
		webdriver.get(url);
		System.out.println("====================");
		System.out.println("GoToMeeting webpage opened and looged in ...");
		System.out.println("====================");
	}
	
	public void login(){
		
		webdriver.findElement(By.id(emailId)).sendKeys(emailIdValue);
		webdriver.findElement(By.id(pwd)).sendKeys(pwdValue);
		webdriver.findElement(By.id(submit)).click();
		
	}
	
}
