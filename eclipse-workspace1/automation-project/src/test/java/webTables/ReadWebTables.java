package webTables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadWebTables {

	String url = "file:///C:/Users/a_tur/eclipse-workspace1/automation-project/src/test/java/webTables/webtable.html";

	WebDriver driver;

	@BeforeClass
	public void setup() {
		System.out.println("setting");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test 
	 public void readScores() { 
	 driver.get(url); 
	 //read whole webtable data and print 
	 WebElement table = driver.findElement(By.tagName("table"));
	 System.out.println(table.getText());
	 
	 //find out how many rows in the table
	 List<WebElement> rows = driver.findElements(By.xpath("//table[@id='worldcup']/tbody/tr"));
	 System.out.println("Number of data rows : " + rows.size());
	 
	  //print all table header.one by one //get all headers into a list //use a loop to print out
	 
	 String headerPath = "//table[@id='worldcup']/thead/tr/th"; 
	 List<WebElement> header = driver.findElements(By.xpath(headerPath));
	 
	 List<String> expHeaders = Arrays.asList("Team1", "Score", "Team2");
	 List<String> actHeaders = new ArrayList<>();
	 
	 for(WebElement each : header) {
	 System.out.println(actHeaders.add(each.getText()));
	 //burada sadece each.getText() vardi, murodil boyle degidtir dedi. line 84 ile ilgili }
	 }
	  SoftAssert softAssert = new SoftAssert(); 
	  softAssert.assertEquals(actHeaders, expHeaders);
	  
	 //write xpath and findelement gettext -> needs to print Egypt 
	  String egptPath = "//table[@id = 'worldcup']/tbody/tr[3]/td[3]";
	  softAssert.assertEquals(driver.findElement(By.xpath(egptPath)).getText(), "Egypt");
	 
	 //loop it and print all data //get number of rows, columns, then nested loop
	 int rowsCount = driver.findElements(By.xpath("//table[@id ='worldcup']/tbody/tr")).size();
	 int colsCount = driver.findElements(By.xpath("//table[@id ='worldcup']/thead/tr/th")).size();
	 
	 System.out.println("====================================");
	  
	 
	  for(int row = 1; row <= rowsCount; row++) { 
		  for(int col = 1; col <= colsCount; col++) { 
			  String xpath = "//table[@id='worldcup']/tbody/tr["+row+"]/td["+col+"]"; 
			  String tdData = driver.findElement(By.xpath(xpath)).getText(); 
			  System.out.print(tdData +"  \t"); 
			  } 
		  System.out.println(); 
		  }
	  softAssert.assertAll();
	  
	  }

	// https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8

	@Test
	public void applicantsData() {
		driver.get("https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8");
		printTableData("reportTab");
	}

	public void printTableData(String id) {
		int rowsCount = driver.findElements(By.xpath("//table[@id='" + id + "']/tbody/tr")).size();
		int colsCount = driver.findElements(By.xpath("//table[@id='" + id + "']/thead/tr/th")).size();

		System.out.println("==============");

		for (int row = 1; row <= rowsCount; row++) {
			for (int col = 1; col <= colsCount; col++) {
				String xpath = "//table[@id='" + id + "']/tbody/tr[" + row + "]/td[" + col + "]";
				String tdData = driver.findElement(By.xpath(xpath)).getText();
				System.out.print(tdData + "--");
			}
			System.out.println();
		}

	}

	// 1) goto
	// https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8
	// 2) Create a HashMap
	// 3) change row number to 100, read all data on first page and put uniquID as a
	// KEY
	// and Applicant info as a Value to a map.
	//
	// applicants.put(29,"Amer, Sal-all@dsfdsf.com-554-434-4324-130000")
	//
	// 4) Click on next page , repeat step 3
	// 5) Repeat step 4 for all pages
	// 6) print count of items in a map. and assert it is matching
	// with a number at the buttom

	@Test
	public void test() throws InterruptedException {
		driver.get("https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8");
		new Select(driver.findElement(By.id("recPerPage"))).selectByIndex(3);
		Thread.sleep(2000);
		Map<String, String> map = new HashMap();

		System.out.println(Integer.parseInt(driver.findElement(By.cssSelector("#total")).getText()));
		while (map.size() < Integer.parseInt(driver.findElement(By.cssSelector("#total")).getText())) { //
			List<WebElement> pathList = driver.findElements(By.cssSelector("table#reportTab>tbody>tr"));
			System.out.println(pathList.size());
			for (WebElement each : pathList) {
				String key = each.getText().substring(0, each.getText().indexOf(" "));
				String value = each.getText().substring(each.getText().indexOf(" "));
				System.out.println(key + " " + value);
				map.put(key, value);
			}
			driver.findElement(By.cssSelector(".nxtArrow")).click();
			Thread.sleep(2000);
		}

		System.out.println(map.size());
		Assert.assertEquals(map.size(), Integer.parseInt(driver.findElement(By.cssSelector("#total")).getText()));
	}
	
//	@AfterMethod
//	public void tearDown() {
//		driver.close();
//	}

}
