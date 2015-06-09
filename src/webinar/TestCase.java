package webinar;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.xml.XmlTest;

public class TestCase {
  
	public RemoteWebDriver driver;
	public XmlTest config;
	
	@BeforeClass
	public void launchUrl(XmlTest config){
		try{
			this.driver = Driver.getDriver(config);
			this.config = config;
			openGoToMeeting();
		}catch(Exception e){
			
		}
	}
	
	public void openGoToMeeting(){
		loginWebinar login = PageFactory.initElements(getDriver(), loginWebinar.class);
		try{
			login.launchGoToMeeting(getConfig());
			login.login();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public RemoteWebDriver getDriver(){
		return this.driver;
	}
	
	public XmlTest getConfig(){
		return this.config;
	}
}
