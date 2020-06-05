package com.binary.run;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.binary.run.util.KeyMap;

public class OptionField {

	public static void main(String[] args) {
		System.out.println("開始運行程式");
		OptionField("GBPUSD", "10", "5", "PUT");
	}

	public static void OptionField(String symbol, String betAmount, String time, String type) {
		// 建立 Robot 例項

		try {
			Robot robot = new Robot();
			// 執行完一個事件後再執行下一個
			robot.setAutoWaitForIdle(true);
			System.out.println("移動到時間列表");
			// 移動滑鼠到指定螢幕座標
			LeftClickItem(robot, 960, 387);
			for (String c : "BTCUSD ".split("")) {
				if (Character.isUpperCase(c.charAt(0))) {
					robot.keyPress(KeyMap.keyMap.get(c.toUpperCase()));
				}
			}

			robot.delay(1000);

			LeftClickItem(robot, 960, 387);
			for (String c : symbol.split("")) {
				if (Character.isUpperCase(c.charAt(0))) {
					robot.keyPress(KeyMap.keyMap.get(c.toUpperCase()));
				}
			}
			System.out.println("確認框");
			robot.keyPress(KeyEvent.VK_ENTER);

			robot.delay(500);
			// 移動到金額框
			LeftClickItem(robot, 1012, 416);
			robot.delay(300);
			for (String c : betAmount.split("")) {
				robot.keyPress(KeyMap.keyMap.get(c));
			}

			LeftClickItem(robot, 965, 451);
			robot.delay(500);
			setExpireTime(robot, time);

			if (type.equals("CALL")) {
				LeftClickItem(robot, 888, 630);
				robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);		// 模擬按下滑鼠左鍵		
				robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);	// 模擬釋放滑鼠左鍵
			} else if (type.equals("PUT")) {
				LeftClickItem(robot, 1017, 632);
				robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);		// 模擬按下滑鼠左鍵		
				robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);	// 模擬釋放滑鼠左鍵
			}
		} catch (Exception e) {
			System.out.println("發生錯誤" + e);
		}

	}

	public static void LeftClickItem(Robot robot, int x, int y) {
		System.out.println("移動到時間框");
		// 移動滑鼠到指定螢幕座標
		robot.mouseMove(x, y);

		System.out.println("點選滑鼠左鍵");
		// 按下滑鼠左鍵
		robot.mousePress(InputEvent.BUTTON2_MASK);
		// 延時100毫秒gbpusd
		robot.delay(1000);
		// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
		robot.mouseRelease(InputEvent.BUTTON2_MASK);
	}

	public static void setExpireTime(Robot robot, String time) {

		switch (time) {
		case "1":

			break;
		case "2":
			robot.keyPress(KeyEvent.VK_DOWN);

			break;
		case "3":
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);

			break;
		case "4":
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);

			break;
		case "5":
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_DOWN);

			break;

		default:
			break;
		}
	}

	public static void setSymbol() {

	}

	/**
	 * @author admin 買進框
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public static void cllButton(int xCoordinate, int yCoordinate) {
		Robot confirm;
		try {
			confirm = new Robot();

			confirm.delay(1000);
			System.out.println("移動下單列表");
			// 移動滑鼠到指定螢幕座標
			confirm.mouseMove(xCoordinate, yCoordinate);
			System.out.println("點選滑鼠左鍵 Cll 買進輸入框");
			// 按下滑鼠左鍵
			confirm.mousePress(InputEvent.BUTTON1_MASK);
			// 延時100毫秒
			confirm.delay(100);
			// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
			confirm.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void putButton(int xCoordinate, int yCoordinate) {
		Robot confirm;
		try {
			confirm = new Robot();

			confirm.delay(1000);
			System.out.println("移動下單列表");
			// 移動滑鼠到指定螢幕座標
			confirm.mouseMove(xCoordinate, yCoordinate);
			System.out.println("點選滑鼠左鍵 Cll 買進輸入框");
			// 按下滑鼠左鍵
			confirm.mousePress(InputEvent.BUTTON1_MASK);
			// 延時100毫秒
			confirm.delay(100);
			// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
			confirm.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author admin 金額進來之後進行下注的動作
	 * @param robot
	 * @param betAmo
	 * @return
	 */
	public static boolean betAmount(Robot robot, String betAmo) {
		System.out.println("收到下單金額為:" + betAmo);
		List<String> betList = conversionAmount(betAmo);
		for (String betstr : betList) {
			System.out.println("即將下單的金額拆分:" + betstr);
			robot.delay(300);
			switch (betstr) {
			case "1":
				robot.keyPress(KeyEvent.VK_NUMPAD1);
				break;
			case "2":
				robot.keyPress(KeyEvent.VK_NUMPAD2);
				break;
			case "3":
				robot.keyPress(KeyEvent.VK_NUMPAD3);
				break;
			case "4":
				robot.keyPress(KeyEvent.VK_NUMPAD4);
				break;
			case "5":
				robot.keyPress(KeyEvent.VK_NUMPAD5);
				break;
			case "6":
				robot.keyPress(KeyEvent.VK_NUMPAD6);
				break;
			case "7":
				robot.keyPress(KeyEvent.VK_NUMPAD7);
				break;
			case "8":
				robot.keyPress(KeyEvent.VK_NUMPAD8);
				break;
			case "9":
				robot.keyPress(KeyEvent.VK_NUMPAD9);
				break;
			case "0":
				robot.keyPress(KeyEvent.VK_NUMPAD0);
				break;

			}
		}
		return false;

	}

	/**
	 * @author admin 傳入金額 轉換成 一個一個 例如 10 轉換成 1 0 LIST陣列
	 * @param amount
	 * @return
	 */
	public static List<String> conversionAmount(String amount) {

		List<String> list = Stream.iterate(0, n -> ++n).limit(amount.length()).map(n -> "" + amount.charAt(n))
				.collect(Collectors.toList());

		if (list.size() != 0) {
			for (String str : list) {
				System.out.println("獲取到的金額組合為:" + str);
			}
			return list;
		} else {
			list = null;
			System.out.println("發生錯誤! 沒有金額傳入!!!");
		}

		return list;

	}

}
