package com.binary.run;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OlymptradeSelenium {

	public static boolean Login(WebDriver webObj, String account, String password) throws InterruptedException {
		boolean isOk = false;
		// 先行點選登入
		Thread.sleep(5000);
		webObj.findElement(By.xpath("//*[@id='auth-inline-form-container']/div/div[1]/header/div/div[1]/span")).click();
		Thread.sleep(5000);
		webObj.findElement(By.xpath("//*[@id='user-entry-sing-in-form']/div[1]/input")).sendKeys(account);
		Thread.sleep(3000);
		webObj.findElement(By.xpath("//*[@id='user-entry-sing-in-form']/div[2]/input")).sendKeys(password);
		Thread.sleep(3000);

		Scanner scanner = new Scanner(System.in);
		System.out.println("請輸入三個數字(以空白鍵分開)：");
		String ok = scanner.nextLine();

		return isOk;

	}

	public static void searchSymbol(WebDriver webObj, String Symbol) throws InterruptedException {
		// 點選搜尋框
		
		
		while (true) {
			boolean checkSymbolDocument = isJudgingElement(webObj,
					By.xpath("//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[1]/div[2]/ul/li[1]/button"));
			if (checkSymbolDocument) {
				webObj.findElement(By.xpath(
						"//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[1]/div[2]/ul/li[1]/button"))
						.click();
				Thread.sleep(1000);
				break;
			}
		}
	

		Thread.sleep(2000);
		webObj.findElement(By.xpath("//*[@id='pair-assets-search-input']")).sendKeys(Symbol);

		Thread.sleep(2000);
		webObj.findElement(By.xpath(
				"//*[@id='pair-dashboard-container']/div[2]/div/div/div[3]/div/div[1]/div/div/div[1]/div/div[2]/div/button"))
				.click();

	}

	public static void setTimeAndAmount(WebDriver webObj, String hour, String minutes, String Amount) throws InterruptedException {
		
		Thread.sleep(1000);
		
		//打開 下單框
		webObj.findElement(By.xpath("//*[@id=\"page-container\"]/div[3]/main/div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div/div[1]"))
				.click();
		
		Thread.sleep(2000);
		//我的時間框
		webObj.findElement(By.xpath("//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div[1]/div/div[1]/div/div[2]/button[6]"))
		.click();
		
		Thread.sleep(1000);
		//分鐘 span
		webObj.findElement(By.xpath("//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div[1]/div/div[1]/div/div[2]/div/div/span/span[2]"))
		.click();
		
		
		Thread.sleep(1000);
		//分鐘框
		webObj.findElement(By.xpath("//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div[1]/div/div[1]/div/div[2]/div/div/span/span[2]/input"))
		.sendKeys("0");
		
		
		Thread.sleep(1000);
		webObj.findElement(By.xpath("//*[@id='page-container']/div[3]/main/div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div[1]/div/div[1]/div/div[2]/div/div/span/span[2]/input"))
		.sendKeys(minutes);
		
		
	

	}

	public static boolean isJudgingElement(WebDriver webDriver, By by) throws InterruptedException {
		try {
			webDriver.findElement(by);
			return true;
		} catch (Exception e) {
			System.out.println("warning!! 系統將等候畫面初始化完成 原因:不存在元素:" + by);
			Thread.sleep(2000);
			return false;
		}
	}
}
