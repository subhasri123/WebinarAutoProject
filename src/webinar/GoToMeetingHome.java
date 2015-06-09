package webinar;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * This file contains the library functions for operations that be performed
 * on GoToMeeting home page. For now only the meeting schedule method and 
 * verification method is written.
 */
public class GoToMeetingHome {

	public WebDriver webdriver;
	public String schedule = "scheduleWebinar";
	public String myWeb = "My Webinars";
	public String search = "upcomingWebinar-searchWebinarSearchBox";
	
	String meetingXpath = "";
	String dateValue = "";
	String timeValue = "";
	String meetingName = "";
	
	public GoToMeetingHome(WebDriver webdriver){
		this.webdriver = webdriver;
	}
	
	public void scheduleMeeting(){
		webdriver.findElement(By.id(schedule)).click();
	}
	
	public boolean verifyValues(HashMap<String,String> hm, String date) throws InterruptedException{
		// below code tests the search field functionality 
		webdriver.findElement(By.linkText(myWeb)).click();
		webdriver.findElement(By.id(search)).sendKeys(hm.get("meetingName"));
		webdriver.findElement(By.id(search)).sendKeys(Keys.ENTER);
		
		// code added to overcome StaleElementexception. This can be optimized in a better way.
		int attempts = 5;
		while(attempts > 0){
			try{
				String nameDisplayed = "//li/a/span[contains(text(),'"+hm.get("meetingName")+"')]";
				meetingName = webdriver.findElement(By.xpath(nameDisplayed)).getText();
				System.out.println("meeting name:" + meetingName + hm.get("meetingName"));
				if(meetingName.equals(hm.get("meetingName"))){
					System.out.println("\n");
					System.out.println("Verification pass - meeting name");
					break;
				}else{
					System.out.println("Verification failed - meeting name...");
					System.out.println("EXPECTED VALUE : " + hm.get("meetingName"));
					System.out.println("ACTUAL VALUE : " + meetingName);
					return false;
				}
			}catch(StaleElementReferenceException e){
				
			}
			attempts--;
		}
		
		
		webdriver.findElement(By.id(search)).clear();
		webdriver.findElement(By.id(search)).sendKeys(Keys.ENTER);
		
		/*hard coded wait added for page to load. This should be replaced with explicit wait condition
		 Below code is to verify the data & time values associated with the created meeting schedule. As
		 all the webelements in the html page for all the meetings displayed are active, the below code loops through
		 the entire set of meetings listed and fetches the desired value.
		 There is definitely scope to optimize this code to gain efficiency in script execution.
		*/ 
		Thread.sleep(180000);
		List<WebElement> meetingList = webdriver.findElements(By.xpath("//div[@class='table-data-row openWebinar']"));
		
		int count = 1;
		for(int i=1;i<=meetingList.size();i++){
			meetingXpath = "//div[@id='upcomingWebinar']/div/div[" +count +"]/ul/li/a/span[contains(text(),'" +meetingName+"')]";
			
			if(isElementPresent(meetingXpath)){
				dateValue = webdriver.findElement(By.xpath("//div[@id='upcomingWebinar']/div/div[" +count +"]/ul/li[contains(@class,'myWebinarDate')]")).getText();
				timeValue = webdriver.findElement(By.xpath("//div[@id='upcomingWebinar']/div/div[" +count +"]/ul[2]/div/li/span[contains(@class,'myWebinarDetailInfo')]")).getText();
				break;
				}
			count = count + 6;
		}
		
		String actualTime = hm.get("timeStart") + " - " + hm.get("timeEnd");
		
		if(timeValue.contains(actualTime)){
			System.out.println("\n");
			System.out.println("Verification pass - time ...");
		}else{
			System.out.println("Verification failed - time");
			System.out.println("EXPECTED VALUE : " + actualTime);
			System.out.println("ACTUAL VALUE : " + timeValue);
			return false;
		}
		
		if(date.contains(dateValue)){
			System.out.println("\n");
			System.out.println("Verification pass - date...");
		}else{
			System.out.println("Verification failed - date...");
			System.out.println("EXPECTED VALUE : " + date);
			System.out.println("ACTUAL VALUE : " + dateValue);
			return false;
		}
		
		return true;
	}
	
	private boolean isElementPresent(String xpath) {
        try {
            webdriver.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
