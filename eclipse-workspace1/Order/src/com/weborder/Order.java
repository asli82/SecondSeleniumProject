package com.weborder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {
	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	final java.util.Random rand = new java.util.Random();

	// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
	final Set<String> identifiers = new HashSet<String>();

	public String randomIdentifier() {
	    StringBuilder builder = new StringBuilder();
	    while(builder.toString().length() == 0) {
	        int length = rand.nextInt(5)+5;
	        for(int i = 0; i < length; i++) {
	            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
	        }
	        if(identifiers.contains(builder.toString())) {
	            builder = new StringBuilder();
	        }
	    }
	    return builder.toString();
	}
	public static void main(String[] args) {

		Order a = new Order();
		String random = a.randomIdentifier();
		System.setProperty("webdriver.chrome.driver", 
				"C:\\Users\\a_tur\\Documents\\selenium dependencies\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		
		driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
		driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
		
		//driver.findElement(By.name("ctl00$MainContent$login_button")).click();
		driver.findElement(By.cssSelector("input[type='submit']")).click();;
		
		driver.get("http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/Process.aspx");
		
		Random hey = new Random();
		int low = 0;
		int high = 100;
		Integer result = hey.nextInt(high-low);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(result.toString());
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys("John"+" "+ random +" "+"Smith");	
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys("123 Any st");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys("Anytown");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys("Virginia");


		Integer result1 = hey.nextInt(100000);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(result1.toString());
		int card = hey.nextInt(3);

			if(card == 0) {
				driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")).click();
				//Integer cardNo1 = hey.nextInt(Math.pow(10, 15));
				
				
				
			}else if (card == 1) {
				driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1")).click();
				
			}else if (card == 2) {
				driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_2")).click();
				
			}

		String cardNumber;
		if(card==0) {
			cardNumber="4";
			for(int i=1; i<16; i++)
				cardNumber+=hey.nextInt(10);
			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNumber);
		}
		else if(card==1) {
			cardNumber="5";
			for(int i=1; i<16; i++)
				cardNumber+=hey.nextInt(10);
			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNumber);
		}else {
			cardNumber="3";
			for(int i=1; i<15; i++)
				cardNumber+=hey.nextInt(10);
			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNumber);
		}
		
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(String.valueOf(cardNumber));
        
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys("04/20");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

	}
}
