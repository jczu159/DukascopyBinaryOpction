package com.binary.run.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	public static Robot robot;
	public static ArrayList<String> tabs;

	public static WebDriver getDriver(String googleDriverPathStr) {

		// Driver設定
		if (driver == null) {

			System.out.println("呼叫到DriverSet()");
			System.setProperty("webdriver.chrome.driver", googleDriverPathStr);
			// System.setProperty("webdriver.chrome.driver","G:/chromedriver.exe");
			// 螢幕設定
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(false);
			// // 將設定黨匯入
			// driver = new ChromeDriver(options);
			driver = new ChromeDriver(options);

			driver.manage().window().maximize();

			// driver.manage().window().setSize(new Dimension(1024, 768));

			System.out.println(driver);
		}
		return driver;
	}

	public static WebDriverWait getWait() {
		if (wait == null) {
			System.out.println("呼叫到WebDriverWaitSet()");
			wait = new WebDriverWait(driver, 10);
		}
		return wait;

	}

	public static Actions getActions() {
		if (actions == null) {
			System.out.println("呼叫到ActionsSet()");
			actions = new Actions(driver);
		}
		return actions;

	}

	public static Robot getRobot() {
		if (robot == null) {
			System.out.println("呼叫到SetRobot()");
			try {
				robot = new Robot();
			} catch (AWTException e) {
				System.out.println("Robot無法使用");
			}
		}
		return robot;

	}

	public static ArrayList<String> getTabs() {
		if (tabs == null) {
			System.out.println("呼叫到SetTabs()");

			tabs = new ArrayList<String>(driver.getWindowHandles());

		}
		return tabs;

	}

	public static ArrayList<String> setTabsnull() {
		System.out.println("呼叫到Tabsnull()");
		tabs = null;

		return tabs;

	}

	public static WebDriver setAllNull() {

		System.out.println("setDriverNull");
		driver = null;
		wait = null;
		actions = null;
		robot = null;
		tabs = null;
		return driver;
	}

}
