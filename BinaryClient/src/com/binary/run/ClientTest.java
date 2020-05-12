package com.binary.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.DriverFactory;
import com.binary.run.util.TemporaryOrder;
import com.google.gson.JsonObject;

public class ClientTest {
	public int port = 9877;
	Socket socket = null;
	static WebDriver webObj = null;
	static String chromePath = "";

	public static void main(String[] args) {
//		new ClientTest("", "", "C:/Users/admin/Desktop/chromedriver.exe", null); // 開始執行
	}

	public ClientTest(String account, String password, String chromePath, String[] amountlist) {
		try {

			webObj = DriverFactory.getDriver(chromePath);
			webObj.get("https://demo-login.dukascopy.com/binary/");
			Dukascopy.Login(webObj, account, password);

			System.out.println("開始建立Socket 連線通訊 ．．．．．");
			socket = new Socket("45.32.49.87", port);
			new Cthread().start();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg1;

			while ((msg1 = br.readLine()) != null) {

				if (!msg1.equals(" ") && !msg1.isEmpty() && msg1.length() != 0) {

//					String amountlist[] = { "10", "23", "46", "92", "184", "368", "640" };
					System.out.println("取得json字串:" + msg1);

					boolean isJson = isJSONValid(msg1);
					boolean validateJson = isJSON2(msg1);
//					System.out.println("是不是JSON :" + isJson + " validateJson:" + validateJson);

					if (isJson == true && validateJson == true) {
						JSONObject jsOBj = new JSONObject();
						jsOBj = (JSONObject) jsOBj.parse(msg1);

						if (jsOBj != null && !jsOBj.isEmpty()) {
							Object object = jsOBj.get("result");
							char[] strChar = object.toString().substring(0, 1).toCharArray();
							char firstChar = strChar[0];

							if (firstChar == '{') {
								jsOBj = (JSONObject) jsOBj.get("result");
								System.out.println("這是一般的JSON OBJ方法");
								if ("binaryOption_A".equals(jsOBj.get("strategy"))) {
									String Symbol = jsOBj.getString("symbol");
									StringBuffer sb = new StringBuffer(Symbol);
									Symbol = sb.insert(3, "/").toString();
									System.out.println("取得此次下單商品" + Symbol);
									Integer amountListInt = Integer.valueOf(jsOBj.getString("martingale"));
									System.out.println("取得此次加碼次數" + amountListInt);
									String Amount = amountlist[amountListInt];
									System.out.println("取得此次下單金額" + Amount);
									String BetHour = jsOBj.getString("expireHourTime");
									System.out.println("取得此次下單小時" + BetHour);
									String BetMinute = jsOBj.getString("expireMinuteTime");
									System.out.println("取得此次下單分鐘" + BetMinute);
									String BetType = jsOBj.getString("direction");
									System.out.println("取得此次下單方向" + BetType);
									// 如果金額不等於0才進入下單，可以讓使用者自行控制下單的方向
									if (!Amount.equals("0")) {
										Dukascopy.dukascopyBinaryOpction(webObj, Symbol, Amount, BetHour, BetMinute,
												BetType);
									}
								}
							} else if (firstChar == '[') {
								System.out.println("this signs is json array");
								JSONArray jsonAry = new JSONArray();
								jsonAry = (JSONArray) jsOBj.get("result");
								for (Object obar : jsonAry) {
									jsOBj = jsOBj.parseObject(obar.toString());
									if ("binaryOption_B".equals(jsOBj.get("strategy"))) {
										String Symbol = jsOBj.getString("symbol");
										StringBuffer sb = new StringBuffer(Symbol);
										Symbol = sb.insert(3, "/").toString();
										System.out.println("取得此次下單商品" + Symbol);
										Integer amountListInt = Integer.valueOf(jsOBj.getString("martingale"));
										System.out.println("取得此次加碼次數" + amountListInt);
										String Amount = amountlist[amountListInt];
										System.out.println("取得此次下單金額" + Amount);
										String BetHour = jsOBj.getString("expireHourTime");
										System.out.println("取得此次下單小時" + BetHour);
										String BetMinute = jsOBj.getString("expireMinuteTime");
										System.out.println("取得此次下單分鐘" + BetMinute);
										String BetType = jsOBj.getString("direction");
										System.out.println("取得此次下單方向" + BetType);
										// 如果金額不等於0才進入下單，可以讓使用者自行控制下單的方向
										if (!Amount.equals("0")) {
											Dukascopy.dukascopyBinaryOpction(webObj, Symbol, Amount, BetHour, BetMinute,
													BetType);
										}
										Object betPrice = Dukascopy.getBetPrice(webObj, BetType);
										System.out.println("取得下單價格" + betPrice);

										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										Date now = new Date();

										JSONObject postData = new JSONObject();

										long time = 60 * 60 * 1000;// 一小時之後時間
										Date afterDate = new Date(now.getTime() + time);// 一小時之後的時間

										System.out.println("取得系統當前時間:" + sdf.format(now));
										System.out.println("取得系統一小時之後時間:" + sdf.format(afterDate));

										postData.put("Symbol", Symbol);
										postData.put("BetType", BetType);
										postData.put("amountListInt", 1);
										postData.put("betPrice", betPrice);
										postData.put("betTime", sdf.format(afterDate));

										boolean checkPut = TemporaryOrder.put(postData);
										if (checkPut) {
											System.out.println("Put the temporarily stored information on the map :"
													+ postData.toString());
										} else {
											System.out.println("ERROR !! : Failed to put in cache");
										}
									}

								}
								System.out.println(jsonAry);
							}

						}
					}
				}

			}
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