package com.binary.run;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.binary.run.util.DriverFactory;

public class Dukascopy {
	// static WebDriver webObj = null;
	//
	// static String chromePath = "";

	public static void main(String[] args) throws InterruptedException {

		// chromePath = "C:/Users/admin/Desktop/chromedriver.exe";
		// webObj = DriverFactory.getDriver(chromePath);
		// webObj.get("https://demo-login.dukascopy.com/binary/");
		// dukascopyBinaryOpction(webObj, "USD/CHF", "45", "0", "11", "PUT");
	}

	public static void dukascopyBinaryOpction(WebDriver webObj, String Symbol, String Amount, String BetHour,
			String BetMinute, String BetType) throws InterruptedException {
		// Login(webObj);
		// *[@id="button-1267"]
		System.out.println("請稍後，我們即將為您下單");
		System.out.println("初始化模組中.....");
		// 檢查品種物件是否存在。

		selectOrderList(webObj, Symbol);
		Thread.sleep(500);
		// 處理下單金額
		setBetAmount(webObj, Amount);
		Thread.sleep(500);
		// 設定小時
		setBetHour(webObj, BetHour);
		Thread.sleep(500);
		// 設定分鐘
		seMminutes(webObj, BetMinute);
		Thread.sleep(500);
		// 設定下單方向
		setBetType(webObj, BetType);

	}
	
	
	public static void dukascopyBinaryOpctionPreselection(WebDriver webObj, String Symbol, String Amount, String BetHour,
			String BetMinute, String BetType) throws InterruptedException {
		// Login(webObj);
		// *[@id="button-1267"]
		System.out.println("請稍後，我們即將為您下單");
		System.out.println("初始化模組中.....");
		// 檢查品種物件是否存在。

		selectOrderList(webObj, Symbol);
		Thread.sleep(500);
		// 處理下單金額
		setBetAmount(webObj, Amount);
		Thread.sleep(500);
		// 設定小時
		setBetHour(webObj, BetHour);
		Thread.sleep(500);
		// 設定分鐘
		seMminutes(webObj, BetMinute);
		Thread.sleep(500);
	}
	
	
	public static void dukascopyBinaryOpctionMandatoryBetting(WebDriver webObj, String BetType) throws InterruptedException {
//		// 設定下單方向
		setBetType(webObj, BetType);
	}

	public static boolean Login(WebDriver webObj, String account, String password) throws InterruptedException {
		// 存取google Chrome 的 exe路徑
		// *[@id="textfield-1020-inputEl"]
		webObj.findElement(By.xpath("//*[@id='textfield-1020-inputEl']")).sendKeys(account);
		Thread.sleep(3000);
		// *[@id="textfield-1021-inputEl"]
		webObj.findElement(By.xpath("//*[@id='textfield-1021-inputEl']")).sendKeys(password);
		Thread.sleep(3000);
		// *[@id="button-1034"]
		webObj.findElement(By.xpath("//*[@id='button-1034']")).click();

		//
		System.out.println("初始化頁面中...等待廣告業面載入完成並進行關閉");
		System.out.println("需等待兩秒");
		while (true) {
			// 死循環重複查詢
			// *[@id="bp-window-welcome-1195"]
			Thread.sleep(1000);
			boolean loginPageCloseDiv_1 = isJudgingElement(webObj, By.xpath("/html/body/div[17]"));

			boolean loginPageCloseDiv_2 = isJudgingElement(webObj, By.xpath("/html/body/div[18]"));

			if (loginPageCloseDiv_1 == true || loginPageCloseDiv_2 == true) {

				// 登入 check1288
				boolean buttonClose_1 = isJudgingElement(webObj,
						By.xpath("/html/body/div[17]/div[3]/div/div/a[3]/span/span/span[2]"));
				if (buttonClose_1) {
					webObj.findElement(By.xpath("/html/body/div[17]/div[3]/div/div/a[3]/span/span/span[2]")).click();
				}
				boolean buttonClose_2 = isJudgingElement(webObj,
						By.xpath("/html/body/div[18]/div[3]/div/div/a[3]/span/span/span[2]"));
				if (buttonClose_2) {
					webObj.findElement(By.xpath("/html/body/div[18]/div[3]/div/div/a[3]/span/span/span[2]")).click();
				}
				break;
			}

		}
		return true;
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

	/**
	 * @author Ryan 切換下單商品列
	 * @param webDriver
	 * @return
	 */
	public static boolean clickChangeSymbol(WebDriver webObj, String forexSymbol) {
		// is mnodel 模組化
		System.out.println("Run change Symbol Modularization 即將進行的商品列為:" + forexSymbol);
		try {
			// // 清除下單列
			// webObj.findElement(By.xpath("//*[@id='bp-instrumentfield-1085-inputEl']")).clear();
			// Thread.sleep(1000);

			if (webObj instanceof JavascriptExecutor) {
				((JavascriptExecutor) webObj).executeScript(
						"document.getElementById('bp-instrumentfield-1085-inputEl').value = '" + forexSymbol + "'");
			} else {
				throw new IllegalStateException("This driver does not support JavaScript!");
			}

			Thread.sleep(500);
			webObj.findElement(By.xpath("//*[@id='bp-instrumentfield-1085-trigger-picker']")).click();

			//
			// Thread.sleep(500);
			// webObj.findElement(By.xpath("/html/body/div[23]/div/ul/div[2]")).click();
			//

			//

			// String doubleCheckInputValue =
			// webObj.findElement(By.xpath("//*[@id='bp-instrumentfield-1085-inputEl']"))
			// .getText();
			// // 確任下單
			// if (doubleCheckInputValue.equals(forexSymbol)) {
			// // *[@id="bp-instrumentfield-1085-trigger-picker"] //Symbol
			// select 下拉框BAR
			// System.out.println("再次確認 - 點選確認框 double Check -- >> OK ");
			//// webObj.findElement(By.xpath("//*[@id='bp-instrumentfield-1085-trigger-picker']")).click();
			//
			// }
			// *[@id="bp-instrumentfield-1085-trigger-picker"]

		} catch (InterruptedException e) {
			System.out.println("切換下單列系統發生不可預期的錯誤:" + e);
		}

		return false;
	}

	/**
	 * @author admin 設定下單金額
	 * @param webDriver
	 * @param forexSymbol
	 * @return
	 */
	public static boolean setBetAmount(WebDriver webObj, String Amount) {

		webObj.findElement(By.xpath("//*[@id='bp-amountfield-1086-inputEl']")).clear();
		if (webObj instanceof JavascriptExecutor) {
			((JavascriptExecutor) webObj)
					.executeScript("document.getElementById('bp-amountfield-1086-inputEl').value = '" + Amount + "'");
		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		return false;

	}

	/**
	 * @author admin 設定訂單小時
	 * @param webObj
	 * @param BetHour
	 * @return
	 */
	public static boolean setBetHour(WebDriver webObj, String BetHour) {
		// *[@id="bp-numberfield-1088-inputEl"]

		// 設定小時 設定小時 input value --
		webObj.findElement(By.xpath("//*[@id='bp-numberfield-1089-inputEl']")).clear();
		if (webObj instanceof JavascriptExecutor) {
			// ((JavascriptExecutor) webObj)
			// .executeScript("document.getElementById('bp-numberfield-1089-inputEl').value
			// = '" + BetHour + "'");
			String returnJsBetHourValue = (String) ((JavascriptExecutor) webObj)
					.executeScript("return document.getElementById('bp-numberfield-1089-inputEl').value");

			while (!returnJsBetHourValue.equals(BetHour)) {
				((JavascriptExecutor) webObj).executeScript(
						"document.getElementById('bp-numberfield-1089-inputEl').value = '" + BetHour + "'");

				returnJsBetHourValue = (String) ((JavascriptExecutor) webObj)
						.executeScript("return document.getElementById('bp-numberfield-1089-inputEl').value");
			}

		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		return false;
	}

	/**
	 * @author admin 設定分鐘
	 * @param webObj
	 * @param BetHour
	 * @return
	 */
	public static boolean seMminutes(WebDriver webObj, String BetMinute) {
		webObj.findElement(By.xpath("//*[@id='bp-numberfield-1090-inputEl']")).clear();
		if (webObj instanceof JavascriptExecutor) {
			// ((JavascriptExecutor) webObj).executeScript(
			// "document.getElementById('bp-numberfield-1090-inputEl').value =
			// '" + BetMinute + "'");

			String returnJsBetMminutesValue = (String) ((JavascriptExecutor) webObj)
					.executeScript("return document.getElementById('bp-numberfield-1090-inputEl').value");
			while (!returnJsBetMminutesValue.equals(BetMinute)) {
				((JavascriptExecutor) webObj).executeScript(
						"document.getElementById('bp-numberfield-1090-inputEl').value = '" + BetMinute + "'");
				returnJsBetMminutesValue = (String) ((JavascriptExecutor) webObj)
						.executeScript("return document.getElementById('bp-numberfield-1090-inputEl').value");
			}

		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		return false;

	}

	// *[@id="bp-instrument-ticker-1082"]
	public static boolean setBetType(WebDriver webObj, String BetType) {
		if (webObj instanceof JavascriptExecutor) {
			if (BetType.equals("CALL")) {

				// *[@id="ext-element-54"]
				// webObj.findElement(By.id("ext-element-52")).click();
				((JavascriptExecutor) webObj).executeScript(
						"document.getElementById('bp-instrument-ticker-1082').childNodes[0].childNodes[0].childNodes[3].click()");
				// *[@id="button-1006"]
				try {
					Thread.sleep(500);
					webObj.findElement(By.xpath("//*[@id='button-1006']")).click();
				} catch (Exception e) {
					System.out.println("發生點選下單時錯誤:" + e);

				}

			} else if (BetType.equals("PUT")) {
				((JavascriptExecutor) webObj).executeScript(
						"document.getElementById('bp-instrument-ticker-1082').childNodes[0].childNodes[1].childNodes[3].click()");
				try {
					Thread.sleep(500);
					webObj.findElement(By.xpath("//*[@id='button-1006']")).click();
				} catch (Exception e) {
					System.out.println("發生點選下單時錯誤:" + e);
					// *[@id="button-1006-btnEl"]
				}

			}
		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		return false;

	}

	/**
	 * @author admin 取得下單時的價位
	 * @param webObj
	 * @param BetType
	 * @return
	 */
	public static Object getBetPrice(WebDriver webObj, String BetType) {
		Object betPrice = "";
		if (BetType.equals("CALL")) {
			betPrice = ((JavascriptExecutor) webObj).executeScript(
					"return document.getElementsByClassName('call x-unselectable')[0].childNodes[2].childNodes[1].innerText");
		} else {
			betPrice = (String) ((JavascriptExecutor) webObj).executeScript(
					"return document.getElementsByClassName('put x-unselectable')[0].childNodes[2].childNodes[1].innerText");
		}
		return betPrice;
	}

	public static boolean selectOrderList(WebDriver webObj, String Symbol) throws InterruptedException {

		while (true) {
			boolean checkSymbolDocument = isJudgingElement(webObj,
					By.xpath("//*[@id='bp-instrumentfield-1085-inputEl']"));
			if (checkSymbolDocument) {
				clickChangeSymbol(webObj, Symbol);
				Thread.sleep(1000);
				break;
			}
		}
		return true;

	}

}

// JdbcConnection.inserPeriodNumber(resultPeriod, winningNumbers,
// issue);
