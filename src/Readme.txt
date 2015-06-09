[NOTE : Code currently does not consider scenarios where meetings of the same name exist ]....]


README FILE :
----------
Instructions to execute the test script

Pre-req :
------
RemoteWebDriver is used, hence selenium standalone jar should be running on the system where the test
scripts are intended to be executed.

CMD :
C:\>java -jar selenium-server-standalone-2.46.0.jar -Dwebdriver.chrome.dri
ver="C:\deploy\chromedriver_win32\chromedriver.exe"

Chrome driver should be downloaded and location of "chromedriver.exe" should be passed while starting
selenium jar file.

INPUT required to execute script:
---------------------------------
Data input to drive automation is read from testng.xml.
XML file is bundled with the project. Please enter values for the parameters specified. 
The username and password parameters are left blank.

STEPS to execute script from cmd line:
-------------------------------------
1. Download testng.jar, selenium standalone.jar and selenium jar files in any location
2. Download the project jar file
3. Download the testng.xml file and modify the values for the paramters mentioned
4. Create CLASSPATH variable with the above values 
	
C:\>echo %CLASSPATH%
C:\deploy\testng\testng-6.8\testng-6.8\testng-6.8.jar;C:Users\amaniyan\workspace
\webinar\bin;C:\deploy\selenium-java-2.46.0\selenium-2.46.0\libs;C:\deploy\selen
ium-java-2.46.0\selenium-2.46.0\selenium-java-2.46.0.jar;C:\deploy\selenium-java
-2.46.0\selenium-2.46.0\selenium-java-2.46.0-srcs.jar;C:\deploy\selenium-server-
standalone-2.46.0.jar

4. Start testng test case from command line with the below command
C:\>java org.testng.TestNG <absolute path of testng.xml file>

