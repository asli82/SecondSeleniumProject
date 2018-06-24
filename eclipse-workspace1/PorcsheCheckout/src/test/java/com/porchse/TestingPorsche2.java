package com.porchse;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestingPorsche2 {

	public static Integer isDigit(String letter) {

		String num = "";

		for (int a = 0; a < letter.length(); a++) {
			if (letter.charAt(a) == '.') {
				break;
			}
			if (Character.isDigit(letter.charAt(a))) {
				num += letter.charAt(a);
			}
		}
		return Integer.parseInt(num);
	}

	public static String compareTotal(int num, int... nums) {
		int sum = 0;
		String result = "";
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}
		if (num == sum) {
			result = "PASS: They are equal to each other";
		} else {
			result = "FAIL: They are NOT equal to each other";
		}
		return result;
	}

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// 2.Go to url “https://www.porsche.com/usa/modelstart/”

		String url = "https://www.porsche.com/usa/modelstart/";

		driver.get(url);

		// getting parent window session id.
		String parent = driver.getWindowHandle(); // this one takes one window session
		System.out.println("Parent window : " + parent);

		// 3.Click model 718
		driver.findElement(By.xpath("/html/body/div[2]/div[4]/div/div[2]/a[1]/div/div[2]/div/span")).click();

		// 4.Remember the price of 718Cayman.
		int firstPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[1]/div[2]/div[2]")).getText());
		System.out.println(" TASK 4: First price is : " + firstPrice);

		// 5.Click on Build & Price under 718Cayman
		driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[2]/div/a/span")).click(); //

		// we are assigning all the opened windows by method getWindowhandles() to the
		// set.
		Set<String> allWindows = driver.getWindowHandles();// this one collects all open windows sessions
		// it shows the size of how many window is opening
		int countWindow = allWindows.size();
		System.out.println("Total count " + countWindow);
		// we are iterating each window session ids
		for (String child : allWindows) {
			// if parent window is not equal to child window
			if (!parent.equalsIgnoreCase(child)) {
				// then switch to child window
				driver.switchTo().window(child);
				// print the child window
				System.out.println("Child window " + child);

			}
		}

		// 6.Verify that Base price displayed on the page is same as the price from step
		int basePrice = isDigit(driver
				.findElement(By.cssSelector("#s_price > div.ccaTable > div:nth-child(1) > div.ccaPrice")).getText());
		System.out.println("Base Price is " + basePrice);

		if (firstPrice == basePrice) {
			System.out.println("TASK 6: PASS : First Price and Base Price are equal");
		} else {
			System.out.println("TASK 6: FAIL : First Price and Base Price are NOT equal");
		}

		// 7.Verify that Price for Equipment is

		int equipmentPrice = isDigit(
				driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());// 0

		if (equipmentPrice == 0) {

			System.out.println("TASK 7: PASS: Equipment price verification passed, no equipment equals to $0");

		} else {

			System.out.println("TASK 7: FAIL: Equipment price verification failed, no equipment does NOT equal to $0");
		}

		// 8.Verify that total price is the sum of base price + Delivery, Processing
		// andHandling Fee

		int deliveryFee = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText());

		System.out.println("Delivery fee: " + deliveryFee);

		int totalPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText());// total
																														// price
																														// $57,950
		System.out.println("Total Price:" + totalPrice);

		System.out.println("TASK 8: SUM Test for totalPrice, deliveryFee, basePrice -->  "
				+ compareTotal(totalPrice, deliveryFee, basePrice));

		// task number 9

		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_FJ5\"]/span")).click();// miami blue color

		// 10.Verify that Price for Equipment is Equal to Miami Blue price

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		int colorEquipmentPrice = isDigit(
				driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IAF\"]/div[2]/div[1]/div/div[2]")).getText()); // $2,580

		System.out.println(
				"TASK 10: Equal Test for Miami Blue Price -->  " + compareTotal(equipmentPrice, colorEquipmentPrice));

		// 12.Select 20" Carrera Sport Wheels

		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();

		Thread.sleep(1000);

		// click on Wheels link

		driver.findElement(By.partialLinkText("Wheels")).click();

		Thread.sleep(1000);

		// click on Exterior Color link to hide colors

		driver.findElement(By.id("IAF_subHdl")).click();

		Thread.sleep(1000);

		// click on wheel you want

		driver.findElement(By.xpath("//li[@id='s_exterieur_x_MXRD']")).click();
		////*[@id="s_exterieur_x_MXRD"]/span/span

		// driver.findElement(By.cssSelector("s_exterieur_x_MXRD")).click();

		// 13.Verify that Price for Equipment is the sum of Miami Blue price + 20"
		// Carrera Sport Wheels

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		int wheelEquipmentPrice = isDigit(
				driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText()); // SAME
																													// LINE
																													// 125,
																													// DYNAMIC

		System.out.println("TASK 13: SUM Test for equipmentPrice, colorEquipmentPrice, wheelEquipmentPricee -->  "
				+ compareTotal(equipmentPrice, colorEquipmentPrice, wheelEquipmentPrice));

		// 14.Verify that total price is the sum of base price + Price for Equipment +
		// Delivery, Processing and Handling Fee

		totalPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText());

		basePrice = isDigit(driver
				.findElement(By.cssSelector("#s_price > div.ccaTable > div:nth-child(1) > div.ccaPrice")).getText());

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		deliveryFee = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText());

		System.out.println("TASK 14: SUM Test for totalPrice, basePrice, equipmentPrice, deliveryFe -->  "
				+ compareTotal(totalPrice, basePrice, equipmentPrice, deliveryFee));

		// 15.Select seats ‘Power Sport Seats (14-way) with Memory Package’
		// click on Overview button

		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();

		Thread.sleep(1000);

		// click on Interior Colors and Seats link

		driver.findElement(By.id("submenu_interieur_x_AI_submenu_x_submenu_parent")).click();

		Thread.sleep(1000);

		// click on Seats link

		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Seats']")).click();

		Thread.sleep(1000);

		// click on that seat

		driver.findElement(By.xpath("(//div[@class='seat'])[2]")).click();

		// 16. Verify that Price for Equipment is the sum of Miami Blue price
		// + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		int seatEquipmentPrice = isDigit(
				driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div")).getText());

		System.out.println(
				"TASK 16: SUM Test for equipmentPrice, colorEquipmentPrice, wheelEquipmentPrice, seatEquipmentPrice -->  "
						+ compareTotal(equipmentPrice, colorEquipmentPrice, wheelEquipmentPrice, seatEquipmentPrice));

		// 17.Verify that total price is the sum of base price + Price for Equipment +
		// Delivery, Processing and Handling Fee

		System.out.println("" + totalPrice + " " + basePrice + " " + equipmentPrice + " " + deliveryFee);

		totalPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText());

		System.out.println("TASK 17: SUM Test for totalPrice = basePrice+ equipmentPrice + deliveryFee-->  "
				+ compareTotal(totalPrice, basePrice, equipmentPrice, deliveryFee));

		// 18.Click on Interior Carbon Fiber

		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();

		driver.findElement(
				By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_submenu_parent\"]/span")).click();

		Thread.sleep(3000);

		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_IIC\"]/a")).click();

		// Thread.sleep(1000);

		// 19.Select Interior Trim in Carbon Fiber i.c.w. Standard Interior

		//driver.findElement(By.cssSelector("#vs_table_IIC_x_PEKH_x_c01_PEKH")).click();

		driver.findElement(By.xpath("//span[@id='vs_table_IIC_x_PEKH_x_c01_PEKH'][@class='checkbox']")).click();

		// driver.findElement(By.xpath("(//span[@class='checkbox'])[112]")).click();

		// Thread.sleep(1000);

		// 20.Verify that Price for Equipment is the sum of Miami Blue price +
		// 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package +
		// Interior Trim in Carbon Fiber i.c.w. Standard Interior

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		int carbonEquipmentPrice = isDigit(driver
				.findElement(By.xpath("//div[@id='vs_table_IIC_x_PEKH']//div[@class='box']//div[@class='pBox']/div"))
				.getText());

		System.out.println(
				"TASK 20: SUM Test for equipmentPrice = colorEquipmentPrice+ wheelEquipmentPrice + seatEquipmentPrice + carbonEquipmentPrice-->  "
						+ compareTotal(equipmentPrice, colorEquipmentPrice, wheelEquipmentPrice, seatEquipmentPrice,
								carbonEquipmentPrice));

		// 21.Verify that total price is the sum of base price + Price for Equipment +
		// Delivery, Processing and Handling Fee

		totalPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText());

		System.out.println("TASK 21: SUM Test for totalPrice = basePrice+ equipmentPrice + deliveryFee-->  "
				+ compareTotal(totalPrice, basePrice, equipmentPrice, deliveryFee));

		// 22.Click on Performance

		// overview
		driver.findElement(
				By.xpath("//section[@id='s_conf_submenu'][@class='flyout jsflag_noscroll evt-initial-conf-done']"))
				.click();
		// Thread.sleep(1000);

		// options
		// driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_submenu_parent")).click();
		// Thread.sleep(1000);

		driver.findElement(By.cssSelector("#submenu_individualization_x_individual_submenu_x_submenu_parent")).click();

		// performance
		driver.findElement(By.xpath("//div[@id=\"submenu_individualization_x_individual_submenu_x_IMG\"]/a")).click();

		// 23.Select 7-speed Porsche Doppelkupplung (PDK)

		// driver.findElement(By.id("vs_table_IMG_x_M250_x_c11_M250")).click();

		driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M250']")).click();

		// driver.findElement(By.xpath("//div[@data-link-id='M250']")).click();

		// driver.findElement(By.cssSelector("#vs_table_IMG_x_M250_x_c11_M250")).click();

		// driver.findElement(By.cssSelector("#vs_table_IMG_x_M250_x_c11_M250")).click();

		// driver.findElement(By.xpath("(//div[@class='opt exclusive'])[79]")).click();

		// 24.Select Porsche Ceramic Composite Brakes (PCCB)

		driver.findElement(
				By.xpath("//section[@id='s_conf_submenu'][@class='flyout jsflag_noscroll evt-initial-conf-done']"))
				.click();

		driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_submenu_parent")).click();

		driver.findElement(By.xpath(" //div[@id=\"submenu_individualization_x_individual_submenu_x_IMG\"]/a")).click();

		driver.findElement(By.id("vs_table_IMG_x_M450_x_c91_M450")).click();

		// 25.Verify that Price for Equipment is the sum of Miami Blue pric
		// + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		// + Interior Trim in Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche
		// Doppelkupplung (PDK)
		// + Porsche Ceramic Composite Brakes (PCCB)

		int pdkEquipment = isDigit(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M250']")).getText());

		int pccbEquipment = isDigit(driver.findElement(By.id("vs_table_IMG_x_M450_x_c91_M450")).getText());

		equipmentPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText());

		System.out.println(
				"TASK 25: SUM Test for equipmentPrice = colorEquipmentPrice+ wheelEquipmentPrice + seatEquipmentPrice + "
						+ "carbonEquipmentPrice + pdkEquipment + pccbEquipment -->  "
						+ compareTotal(equipmentPrice, colorEquipmentPrice, wheelEquipmentPrice, seatEquipmentPrice,
								carbonEquipmentPrice, pdkEquipment, pccbEquipment));

		// 26.Verify that total price is the sum of base price + Price for Equipment +
		// Delivery, Processing and Handling Fee

		totalPrice = isDigit(driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText());

		System.out.println("TASK 26: SUM Test for totalPrice = basePrice+ equipmentPrice + deliveryFee-->  "
				+ compareTotal(totalPrice, basePrice, equipmentPrice, deliveryFee));

		/*
		 <span id="vs_table_IIC_x_PEKH_x_c01_PEKH" class="checkbox"></span> 
		 <div id="vs_table_IIC_x_PEKH" class="opt exclusive" data-link-id="PEKH"> 
		 <section id="vs_table_IIC" class="options"> <div class="content"> 
		 <div id="s_individual_x_IIC" class="config-category accordion-open">
		 <div class="content"> 
		 <section id="s_individual">
		 <section id="s_conf" class="fL">
		
	*/

		Thread.sleep(5000);

		// driver.quit();

	}
}
