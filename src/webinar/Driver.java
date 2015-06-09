package webinar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.xml.XmlTest;
/*
 * This class is to initialize the RemoteWebDriver object by
 * setting the desired capabilities. Code has been written to run on only Chrome browser for now. 
 * It can be enhanced for other supported browsers as well.
 */
public class Driver {
    
	public static RemoteWebDriver getDriver(XmlTest config) throws MalformedURLException{
		
		String browserName = config.getParameter("browser").toLowerCase();
		String remoteServerIP = config.getParameter("remoteServerIP");
		String remoteServerPort = config.getParameter("remoteServerPort");
		
		String url = "http://" + remoteServerIP + ":" + remoteServerPort + "/wd/hub";
		
		DesiredCapabilities capability;
		if(browserName.contains("chrome") || browserName.equalsIgnoreCase("chrome")){
			capability = DesiredCapabilities.chrome();
			capability.setJavascriptEnabled(true);
			capability.setPlatform(Platform.ANY);
			capability.setVersion("43.0.2357.81 m");

		}else{
			capability = DesiredCapabilities.htmlUnit();
		}
		
		URL clientURL = new URL(url);
		RemoteWebDriver driver = new RemoteWebDriver(clientURL,capability);
		driver.manage().window().maximize();
		//Using implicit timeouts for now, this can be replaced with explicit wait condition as required
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		return driver;
	
	}
}
