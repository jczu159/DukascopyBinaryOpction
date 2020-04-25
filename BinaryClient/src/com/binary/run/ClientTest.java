package com.binary.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.DriverFactory;

public class ClientTest {
	public int port = 9877;
	Socket socket = null;
	static WebDriver webObj = null;
	static String chromePath = "";

	public static void main(String[] args) {
//		new ClientTest(); // 開始執行
	}

	public ClientTest(String account, String password, String chromePath, String[] amountlist) {
		try {
			chromePath = "C:/Users/admin/Desktop/chromedriver.exe";
			webObj = DriverFactory.getDriver(chromePath);
			webObj.get("https://demo-login.dukascopy.com/binary/");
			Dukascopy.Login(webObj, account, password);

			socket = new Socket("45.32.49.87", port);
			new Cthread().start();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg1;

			while ((msg1 = br.readLine()) != null) {
				System.out.println("接收出雜訊:" + msg1);

				if (!msg1.equals(" ") && !msg1.isEmpty() && msg1.length() != 0) {

//					String amountlist[] = { "10", "23", "46", "92", "184", "368", "640" };
					JSONObject json = new JSONObject();
					System.out.println("取得json字串:" + msg1);

					boolean isJson = isJSONValid(msg1);
					boolean validateJson = isJSON2(msg1);
					System.out.println("是不是JSON :" + isJson + " validateJson:" + validateJson);

					if (isJson == true && validateJson == true) {

						JSONObject jsOBj = (JSONObject) json.parse(msg1);
						String Symbol = jsOBj.getString("symbol");
						StringBuffer sb = new StringBuffer(Symbol);
						Symbol = sb.insert(3, "/").toString();
						System.out.println("取得商品" + Symbol);
						Integer amountListInt = Integer.valueOf(jsOBj.getString("martingale"));
						System.out.println("取得加碼次數" + amountListInt);
						String Amount = amountlist[amountListInt];
						System.out.println("取得金額" + Amount);
						String BetHour = jsOBj.getString("expireHourTime");
						System.out.println("取得小時" + BetHour);
						String BetMinute = jsOBj.getString("expireMinuteTime");
						System.out.println("取得分鐘" + BetMinute);
						String BetType = jsOBj.getString("direction");
						System.out.println("取得方向" + BetType);
						System.out.println("或取得的JSON結果" + BetType);
						Dukascopy.dukascopyBinaryOpction(webObj, Symbol, Amount, BetHour, BetMinute, BetType);
					}
				}

			}
		} catch (Exception e) {

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