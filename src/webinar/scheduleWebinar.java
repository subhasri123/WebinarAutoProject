package webinar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.xml.XmlTest;
/*
 * This is the test case for the given exercise. For now the test java file is also included
 * in the same package as the test libraries. Test case files and library files can be organized in separate
 * packages
 * 
 * For now all the fields displayed in schedule meeting page should be assigned values, else exception will be 
 * thrown. Appropriate validations should be added in the test script.
 * 
 * Test case steps:
 * Create a meeting schedule with the below properties,
 * 1. Meeting Title - input is read from testng.xml(based on the test scenario this can be modified to dynamic values)
 * 2. Meeting description - input is read from testng.xml
 * 3. One session - meeting is created
 * 4. Date input is considered as mentioned in the test exercise - 3 days from today
 * 5. Start and end time - input is read from testng.xml
 * 6. Timezone - one of the valid timezones is given as input from testng.xml
 * 7. Language - one of the valid timezones is given as input from testng.xml
 * 
 */

public class scheduleWebinar extends TestCase{
	public WebDriver driver;
	public GoToMeetingHome meetingHome;
	public meetingPage mPage;
	
	int expStartYear, expStartMonth;
	String expStartDate;
	
	int expEndYear, expEndMonth;
	String expEndDate;
	
	List<String> monthList = (List<String>) Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	List<String> weekList = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
	
  @Test
  public void scheduleMeetingOneSessionTest(XmlTest config) throws InterruptedException{
	  System.out.println("\n==================================");
	  System.out.println("Scheduling meeting test case ...");
	  System.out.println("==================================");
	  meetingHome.scheduleMeeting();
	  
	  HashMap<String,String> map = new HashMap<String,String>();
	  map.put("meetingName", config.getParameter("title"));
	  map.put("desc", config.getParameter("desc"));
	  map.put("type", "One Session");
	  map.put("dateStart", config.getParameter("startDate"));
	  map.put("timeStart", config.getParameter("startTime"));
	  map.put("timeEnd", config.getParameter("endTime"));
	  map.put("timeZone", config.getParameter("timeZone"));
	  map.put("lang", config.getParameter("lang"));
	  
	  	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 3);
		
		expStartMonth = (calendar.get(Calendar.MONTH) + 1);
		expStartYear = calendar.get(Calendar.YEAR);
		expStartDate = 	String.valueOf(calendar.get(Calendar.DATE));
		
		map.put("expStartYear" , String.valueOf(expStartYear));
		map.put("expStartMonth" , String.valueOf(expStartMonth));
		map.put("expStartDate" , expStartDate);
		
		String actualDate = weekList.get(calendar.get(Calendar.DAY_OF_WEEK)-1) + ", " + monthList.get(calendar.get(Calendar.MONTH)).trim() + " " + String.valueOf(calendar.get(Calendar.DATE)) + " ";
		
		mPage.schedule(map);
		System.out.println("\n==================================");
		System.out.println("Scheduling meeting test case ... Verfiying meeting scheduled");
		Assert.assertEquals(true, meetingHome.verifyValues(map, actualDate));
		System.out.println("==================================");
		
  }
  
  @BeforeClass
  public void beforeClass(XmlTest config) throws Exception{
	  meetingHome = PageFactory.initElements(getDriver(), GoToMeetingHome.class);
	  mPage = PageFactory.initElements(getDriver(), meetingPage.class);
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("\n==================================");
	  System.out.println("Test case end....");
	  System.out.println("==================================");
	  getDriver().quit();
	  
  }

}
