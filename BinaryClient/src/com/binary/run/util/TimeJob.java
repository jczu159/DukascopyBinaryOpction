package com.binary.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSONObject;
import com.binary.run.ClientTest;
import com.binary.run.Dukascopy;
import com.mysql.fabric.xmlrpc.base.Data;

public class TimeJob extends TimerTask {

	public static WebDriver webObj;
	public static String[] amountlist;

	public TimeJob(WebDriver driver, String[] amountlist) {
		super();
		this.webObj = driver;
		this.amountlist = amountlist;
	}

	// postData.put("Symbol", Symbol);
//	postData.put("BetType", BetType);
//	postData.put("amountListInt", 1);
//	postData.put("betPrice", betPrice);
//	postData.put("betTime", sdf.format(afterDate));
	@Override
	public void run() {
		ConcurrentHashMap<String, JSONObject> ObjData = TemporaryOrder.get();
		if (ObjData != null) {
			ObjData.forEach((key, jsonVal) -> {
				while (ClientTest.lockThread) {
					try {
						System.out.println("主程式正在運行當中 ，請稍後60秒");
						Thread.sleep(60000);

						if (ClientTest.lockThread == false) {
							break;
						}
					} catch (InterruptedException e) {
						System.out.println("等待主程序發生錯誤!!" + e);
					}

				}
				System.out.println("取得等待判斷的資料:" + jsonVal.toJSONString());

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
				String dataTime = (String) jsonVal.get("betTime");
				try {
					int amountListInt = Integer.valueOf(jsonVal.get("amountListInt").toString());
					Date dataDate = sdf.parse(dataTime);
					Date nowData = new Date();
					if (nowData.getTime() >= dataDate.getTime()) {
						System.out.println("排程開始切換商品，並取得目前價位，請稍後");
						Dukascopy.selectOrderList(webObj, jsonVal.get("Symbol").toString());
						String betAmount = amountlist[amountListInt];
						Dukascopy.setBetAmount(webObj, betAmount);
						Thread.sleep(5000);

						// 判斷是否成功 如不成功 返回false
						boolean isWin = findPriceAndJudgeOutcome(webObj, jsonVal);
						if (isWin) {
							boolean romevekey = TemporaryOrder.reomve(key);
							if (!romevekey) {
								throw new Exception("刪除定時器 MAP中資料 發生錯誤");
							}
						} else {

							// 如果這次近來是第三次也不用再新增map狀態了
							if (jsonVal.get("amountListInt").toString().equals("3")) {
								boolean romevekey = TemporaryOrder.reomve(key);
								if (!romevekey) {
									throw new Exception("刪除定時器 MAP中資料 發生錯誤");
								}
							} else {
								amountListInt = amountListInt + 1;
								JSONObject postData = ClientTest.getCatchJsonObj(jsonVal.get("Symbol").toString(),
										jsonVal.get("BetType").toString(), jsonVal.get("betPrice").toString(),
										amountListInt);
								TemporaryOrder.putCover(key, postData);

							}

							// 這裡必須要進行map資料的重新組合

						}

					}

				} catch (Exception e) {
					System.out.println("每分鐘排程發生不可預期錯誤:" + e);
				}

			});
		}

	}

	/**
	 * @author IMI-JAVA-Ryan 找尋價格並判斷是否勝利喔
	 * @param jsonVal
	 * @param webObj
	 * @return
	 */
	public static boolean findPriceAndJudgeOutcome(WebDriver webObj, JSONObject jsonVal) {

		boolean isWin = false;

		try {

			if (jsonVal.get("BetType").equals("CALL")) {
				Object betPric = Dukascopy.getBetPrice(webObj, "CALL");
				System.out.println("新排程取得價格" + betPric.toString() + ": 接下來進行價格比對的動作");

				double nowPrice = Double.parseDouble(betPric.toString());
				double oldPrice = Double.parseDouble(jsonVal.get("betPrice").toString());

				if (nowPrice > oldPrice) {
					System.out.println("恭喜獲得 : win");
					isWin = true;
				} else {
					String co = jsonVal.getString("amountListInt");
					String betAmount = amountlist[Integer.valueOf(co)];
					Dukascopy.dukascopyBinaryOpction(webObj, jsonVal.getString("Symbol"),betAmount , "1", "0", "CALL");
					System.out.println("失敗 : failure");
					
					
				}

			} else if (jsonVal.get("BetType").equals("PUT")) {
				Object betPric = Dukascopy.getBetPrice(webObj, "PUT");
				System.out.println("新排程取得價格" + betPric.toString() + ": 接下來進行價格比對的動作");

				double nowPrice = Double.parseDouble(betPric.toString());
				double oldPrice = Double.parseDouble(jsonVal.get("betPrice").toString());

				if (nowPrice < oldPrice) {
					isWin = true;
					System.out.println("恭喜獲得 : win");
				} else {
					String co = jsonVal.getString("amountListInt");
					String betAmount = amountlist[Integer.valueOf(co)];
					Dukascopy.dukascopyBinaryOpction(webObj, jsonVal.getString("Symbol"), betAmount, "1", "0", "PUT");
					System.out.println("失敗 : failure");
				}

			}
		} catch (Exception e) {
			System.out.println("比對價格發生錯誤:" + e);
		}
		return isWin;
	}

	public static void main(String[] args) {

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = new Date();
//		long time = 60 * 60 * 1000;// 一小時之後時間
//		Date afterDate = new Date(now.getTime() + time);// 一小時之後的時間
//		JSONObject postData = new JSONObject();
//		postData.put("Symbol", "GBP/USD");
//		postData.put("BetType", "CALL");
//		postData.put("amountListInt", 1);
//		postData.put("betPrice", "1.22304");
//		postData.put("betTime", sdf.format(afterDate));
//
//		TemporaryOrder.put(postData);
//
//		Timer timer = new Timer();
//		long delay1 = 1000;
//		long period1 = 60 * 1000;
//		// 從現在開始 1 秒鐘之後，每隔 1 秒鐘執行一次 job1
//		timer.schedule(new TimeJob(webObj), delay1, period1);
	}

}
