package com.binary.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSONObject;
import com.binary.run.Dukascopy;
import com.mysql.fabric.xmlrpc.base.Data;

public class TimeJob extends TimerTask {

	public static WebDriver webObj;

	public TimeJob(WebDriver driver) {
		super();
		this.webObj = driver;
	}

	@Override
	public void run() {
		HashMap<String, JSONObject> ObjData = TemporaryOrder.get();
		if (ObjData != null) {
			ObjData.forEach((key, val) -> {

				System.out.println("取得等待判斷的資料:" + val.toJSONString());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

				String dataTime = (String) val.get("betTime");
				try {
					Date dataDate = sdf.parse(dataTime);
					Date nowData = new Date();
					if (nowData.getTime() >= dataDate.getTime()) {
						System.out.println("新排程缺換商品");
						while (true) {
							boolean checkSymbolDocument = Dukascopy.isJudgingElement(webObj,
									By.xpath("//*[@id='bp-instrumentfield-1085-inputEl']"));

							if (checkSymbolDocument) {
								// 如果存在則進行 賦予值
								// 設定下單
								Dukascopy.clickChangeSymbol(webObj, val.get("Symbol").toString());
								Thread.sleep(1000);
								break;
							}
						}
						Thread.sleep(2000);
						if (val.get("BetType").equals("CALL")) {
							Object betPric = Dukascopy.getBetPrice(webObj, "CALL");
							System.out.println("新排程取得價格" + betPric.toString());

						} else if (val.get("BetType").equals("PUT")) {
							Object betPric = Dukascopy.getBetPrice(webObj, "PUT");
							System.out.println("新排程取得價格" + betPric.toString());
						}

					} else if (nowData.getTime() <= dataDate.getTime()) { 
						System.out.println("該週期時間還尚未到達");
					}

				} catch (Exception e) {
					System.out.println("每分鐘排程-系統發生不可預期錯誤:" + e);
				}
				// 時間大於特定區間

			});
		}

	}

	/**
	 * @author IMI-JAVA-Ryan 找尋價格並判斷是否勝利喔
	 * @return
	 */
	public static boolean findPriceAndJudgeOutcome() {

		return false;

	}

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		long time = 60 * 60 * 1000;// 一小時之後時間
		Date afterDate = new Date(now.getTime() + time);// 一小時之後的時間
		JSONObject postData = new JSONObject();
		postData.put("Symbol", "GBP/USD");
		postData.put("BetType", "CALL");
		postData.put("amountListInt", 1);
		postData.put("betPrice", "1.22304");
		postData.put("betTime", sdf.format(afterDate));

		TemporaryOrder.put(postData);

		Timer timer = new Timer();
		long delay1 = 1000;
		long period1 = 60 * 1000;
		// 從現在開始 1 秒鐘之後，每隔 1 秒鐘執行一次 job1
		timer.schedule(new TimeJob(webObj), delay1, period1);
	}

}
