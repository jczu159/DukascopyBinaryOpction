package com.binary.run;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnclickPageBet{

	public static void main(String[] args) {
		System.out.println("開始運行程式");

//		try {
//		SockectConnection.socketClient();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		conversionAmount("15");

	}

	public void RunIqOpCtion(String betAmount , String type) {
		// 建立 Robot 例項

		try {
			Robot robot = new Robot();
			// 執行完一個事件後再執行下一個
			robot.setAutoWaitForIdle(true);
			System.out.println("移動到時間列表");
			// 移動滑鼠到指定螢幕座標
			robot.mouseMove(1850, 147);

			// 按下滑鼠左鍵
			robot.mousePress(InputEvent.BUTTON1_MASK);
			System.out.println("延遲1秒");
			// 延時100毫秒
			robot.delay(1000);

			// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			System.out.println("延遲1秒");
			// 延時100毫秒
			robot.delay(1000);
			System.out.println("移動到時間框");
			// 移動滑鼠到指定螢幕座標
			robot.mouseMove(1571, 361);
			System.out.println("點選滑鼠左鍵");
			// 按下滑鼠左鍵
			robot.mousePress(InputEvent.BUTTON2_MASK);

			// 延時100毫秒
			robot.delay(100);
			// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
			robot.mouseRelease(InputEvent.BUTTON2_MASK);

			// 延時100毫秒
			robot.delay(1000);
			System.out.println("移動到輸入價錢的框框");
			// 移動滑鼠到指定螢幕座標
			robot.mouseMove(1842, 222);

			System.out.println("開始點選滑鼠左鍵");
			// 按下滑鼠左鍵
			robot.mousePress(InputEvent.BUTTON3_MASK);
			robot.delay(500);
			/* 這裡是全選並且刪除全部的金額的動作 */
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.delay(100);
			// CTRL+Z is now pressed (receiving application should see a "key down" event.)
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			// CTRL+Z is now released (receiving application should now see a "key up" event
			// - as well as a "key pressed" event).
			// 刪除所有資料的動作
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_BACK_SPACE);

			// 開始進行下單-----
			betAmount(robot, betAmount);
			// 延時100毫秒
			robot.delay(100);
			// 釋放滑鼠左鍵（按下後必須要釋放, 一次點選操作包含了按下和釋放）
			robot.mouseRelease(InputEvent.BUTTON3_MASK);

			// 確認框
			if(type.equals("CALL")) {
				cllButton(1850, 443);				
			}else if(type.equals("PUT")) {
				putButton(1858, 570);
			} 
			

		} catch (Exception e) {
			System.out.println("發生錯誤" + e);
		}

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
