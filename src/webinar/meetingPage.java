package webinar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
/*
 * This file contains the library functions for creating a meeting 
 * schedule of different types. 
 */

public class meetingPage {
	
	public WebDriver webdriver;
	public String title = "name";
	public String description = "description";
	public String scheduleOneSession = "//a[contains(text(),'One Session')]";
	public String scheduleSeries = "//a[contains(text(),'Series')]";
	public String scheduleSequence = "//a[contains(text(),'Sequence')]";
	
	public String calenderImage = "//img[@title='Choose a date']";
	public String nextMonth = "span.ui-icon.ui-icon-circle-triangle-e";
	public String prevMonth = "span.ui-icon.ui-icon-circle-triangle-w";
	
	public String startTimeValue = "webinarTimesForm.dateTimes_0.startTime";
	
	public String endTimeValue = "webinarTimesForm.dateTimes_0.endTime";
	
	public String scheduleButton = "schedule.submit.button";
	
	public boolean datenotFound;
	public String calMonth = "";
	public String calYear = "";
	
	int expStartYear, expStartMonth;
	String expStartDate;
	
	int expEndYear, expEndMonth;
	String expEndDate;
	
	public WebElement dateSelector;
	
	List<WebElement> noOfCols;
	
	List<String> monthList = (List<String>) Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	
	public meetingPage(WebDriver webdriver){
		this.webdriver = webdriver;
	}
	
	
	public void schedule(HashMap<String,String>hm) throws InterruptedException{
		webdriver.findElement(By.id(title)).sendKeys(hm.get("meetingName"));
		webdriver.findElement(By.id(description)).sendKeys(hm.get("desc"));
		
		String sessionType = hm.get("type");
		
		String[] timeStartValues = hm.get("timeStart").split(" ");
		String[] timeEndValues = hm.get("timeEnd").split(" ");
		
		expStartMonth = Integer.parseInt(hm.get("expStartMonth"));
		expStartYear = Integer.parseInt(hm.get("expStartYear"));
		expStartDate = hm.get("expStartDate");
		
		//Code for meeting End date input. For now I have considered only One session meeting. Explicit validation for the
		//field value existence is not added for now, so the below code is commented.
		//expEndMonth = monthList.indexOf(hm.get("monthEnd"));
		//expEndYear = Integer.parseInt(hm.get("yearEnd"));
		//expEndDate = hm.get("dateEnd");
		
		if(sessionType.equalsIgnoreCase("One Session")){
			//webdriver.findElement(By.id(scheduleOneSession)).click();
			
		}else if(sessionType.equalsIgnoreCase("Series")){
			webdriver.findElement(By.id(scheduleSeries)).click();
			Select occurSeries = new Select(webdriver.findElement(By.id("recurrenceForm_recurs")));
			occurSeries.selectByVisibleText(hm.get("occurance"));
			
			//setCalender(expEndYear,expEndMonth,expEndDate);
			
		}else if(sessionType.equalsIgnoreCase("Sequence")){
			webdriver.findElement(By.id(scheduleSequence)).click();
			Select occurSeq = new Select(webdriver.findElement(By.id("recurrenceForm_recurs")));
			occurSeq.selectByVisibleText(hm.get("occurance"));
			
			//setCalender(expEndYear,expEndMonth,expEndDate);
			
		}else{
			System.out.println("invalid meeting sesssion type");
		}
	
		webdriver.findElement(By.xpath(calenderImage)).click();
		setCalender(expStartYear,expStartMonth,expStartDate);
		
		webdriver.findElement(By.id("webinarTimesForm_timeZone_trig")).sendKeys(hm.get("timeZone"));
		webdriver.findElement(By.id("webinarTimesForm_timeZone_trig")).sendKeys(Keys.RETURN);
		
		WebElement mnuOptionElement;
		mnuOptionElement = webdriver.findElement(By.id("webinarTimesForm_timeZone__menu"));
		Actions builder = new Actions(webdriver);
		builder.moveToElement(mnuOptionElement).click();
		
		webdriver.findElement(By.id("language_trig")).sendKeys(hm.get("lang"));
		webdriver.findElement(By.id("language_trig")).sendKeys(Keys.RETURN);
		
		WebElement startTm = webdriver.findElement(By.id(startTimeValue));
		startTm.sendKeys(Keys.CONTROL + "a");
		startTm.sendKeys(Keys.DELETE);
		startTm.sendKeys(timeStartValues[0]);
		Thread.sleep(200);
		
		webdriver.findElement(By.id("webinarTimesForm_dateTimes_0_startAmPm_trig")).sendKeys(timeStartValues[1]);
		webdriver.findElement(By.id("webinarTimesForm_dateTimes_0_startAmPm_trig")).sendKeys(Keys.RETURN);
		
		Thread.sleep(200);
		
		WebElement EndTm = webdriver.findElement(By.id(endTimeValue));
		EndTm.sendKeys(Keys.CONTROL + "a");
		EndTm.sendKeys(Keys.DELETE);
		EndTm.sendKeys(timeEndValues[0]);
		
		Thread.sleep(200);
		
		webdriver.findElement(By.id("webinarTimesForm_dateTimes_0_endAmPm_trig")).sendKeys(timeEndValues[1]);
		webdriver.findElement(By.id("webinarTimesForm_dateTimes_0_endAmPm_trig")).sendKeys(Keys.RETURN);
		
		Thread.sleep(200);
		
		webdriver.findElement(By.id(scheduleButton)).click();
	}
	
	public void setCalender(int year, int month, String date){
		datenotFound = true;
		while(datenotFound){
			calMonth = webdriver.findElement(By.className("ui-datepicker-month")).getText();
			calYear = webdriver.findElement(By.className("ui-datepicker-year")).getText();
			
			if(monthList.indexOf(calMonth)+1 == month && year == Integer.parseInt(calYear)){
				setDate(date);
				datenotFound = false;
			}else if(monthList.indexOf(calMonth)+1 < month && (year == Integer.parseInt(calYear)) || year > Integer.parseInt(calYear)){
				webdriver.findElement(By.cssSelector(nextMonth)).click();
			}else if(monthList.indexOf(calMonth)+1 > month && (year == Integer.parseInt(calYear)) || year < Integer.parseInt(calYear)){
				webdriver.findElement(By.cssSelector(prevMonth)).click();
			}
		}
		
	}
	
	public void setDate(String date){
		dateSelector = webdriver.findElement(By.id("ui-datepicker-div"));
		noOfCols = dateSelector.findElements(By.tagName("td"));
		
		for(WebElement cell : noOfCols){
	
			if(cell.getText().equals(date)){
				cell.findElement(By.linkText(date)).click();
				break;
			}
		}
	}
	
	
	
	
	
	

}

	