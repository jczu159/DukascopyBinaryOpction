package com.binary.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.DriverFactory;

public class Client {

	static WebDriver webObj = null;
	static String chromePath = "";

	public static void main(String[] args) throws UnknownHostException, IOException {
		WebSocetClient();
	}

	private static boolean isConnection = false;

	public static boolean WebSocetClient() throws UnknownHostException, IOException {
		JSONObject json = new JSONObject();

		chromePath = "C:/Users/admin/Desktop/chromedriver.exe";
		webObj = DriverFactory.getDriver(chromePath);
		webObj.get("https://demo-login.dukascopy.com/binary/");
		String amountlist[] = { "2", "5", "11", "25", "60", "140", "320" };
		// 建立連線指定Ip和埠的socket
		// 獲取系統標準輸入流
//
//		// 登入下單葉面
//		try {
////			Dukascopy.Login(webObj);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		Socket socket = new Socket("45.32.49.87", 9877);
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		// 建立一個執行緒用於讀取伺服器的資訊
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("收到訊息:" + in.readLine());
//						boolean isJson = isJSONValid(in.readLine());
//						if (isJson) {

							try {
								String jsonobj = in.readLine().toString();
								System.out.println("取得json字串-- OBJ" + jsonobj);
								JSONObject jsOBj = (JSONObject) json.parse(jsonobj);
								String Symbol = jsOBj.getString("symbol");
								Integer amountListInt = Integer.valueOf(jsOBj.getString("martingale"));
								String Amount = amountlist[amountListInt];
								String BetHour = jsOBj.getString("expireHourTime");
								String BetMinute = jsOBj.getString("expireMinuteTime");
								String BetType = jsOBj.getString("direction");
								Dukascopy.dukascopyBinaryOpction(webObj, Symbol, Amount, BetHour, BetMinute, BetType);

							} catch (Exception e) {
								System.out.println(e);
							}

//						}

					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		// 寫資訊給客戶端
		String line = reader.readLine();
		while (!"end".equalsIgnoreCase(line)) {
			// 將從鍵盤獲取的資訊給到伺服器

			// 顯示輸入的資訊
			line = reader.readLine();
			System.out.println("底下收到訊息" + line);
		}

		return isConnection;
	}

	public final static boolean isJSONValid(String readLineStr) {
		JSONObject json = new JSONObject();
		boolean isJson = false;
		try {
			JSONObject jsOBj = (JSONObject) json.parse(readLineStr);
			isJson = true;
		} catch (JSONException ex) {

		}
		return isJson;
	}

}