package com.binary.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.DriverFactory;

public class Olymptrade {
	public static WebDriver webObj;
	public int port = 9877;
	Socket socket = null;
	static String chromePath = "";
	public static boolean lockThread = false;

	public static void main(String[] args) {
		 new Olymptrade().OlymptradeRun("jackson15988@gmail.com", "536225ab", "C:/Users/admin/Desktop/chromedriver.exe",null); // 開始執行
	}

	public void OlymptradeRun(String account, String password, String chromePath, String[] amountlist) {
		try {

			webObj = DriverFactory.getDriver(chromePath);
			webObj.get("https://olymptrade.com/platform");
			OlymptradeSelenium.Login(webObj, account, password);
			
			
			OlymptradeSelenium.searchSymbol(webObj, "EURUSD");
			
		
			
			OlymptradeSelenium.setTimeAndAmount(webObj, "0", "31", "26");
		

			
		} catch (Exception e) {
			System.out.println("系統發生錯誤:" + e);

		}
	}

	class Cthread extends Thread {
		public void run() {
			try {
				BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				String msg2;
				while (true) {
					msg2 = re.readLine();
					pw.println(msg2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONObject getCatchJsonObj(String Symbol, String BetType, Object betPrice, int i) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();

		JSONObject postData = new JSONObject();

		long time = 60 * 60 * 1000;// 一小時之後時間
		Date afterDate = new Date(now.getTime() + time);// 一小時之後的時間

		System.out.println("取得系統當前時間:" + sdf.format(now));
		System.out.println("取得系統一小時之後時間:" + sdf.format(afterDate));

		postData.put("Symbol", Symbol);
		postData.put("BetType", BetType);
		postData.put("amountListInt", i);
		postData.put("betPrice", betPrice);
		postData.put("betTime", sdf.format(afterDate));
		return postData;

	}

	public final static boolean isJSONValid(String test) {
		try {
			JSONObject.parseObject(test);
		} catch (JSONException ex) {
			try {
				JSONObject.parseArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isJSON2(String str) {
		boolean result = false;
		try {
			Object obj = JSON.parse(str);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}
