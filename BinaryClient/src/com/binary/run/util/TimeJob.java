package com.binary.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSONObject;

public class TimeJob extends TimerTask {

	public static  WebDriver webObj;

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

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

				String dataTime = (String) val.get("betTime");
				try {
					Date dataDate = sdf.parse(dataTime);
					Date nowDate = new Date();

					if (dataDate.compareTo(nowDate) > 0) {
						System.out.println("Date1 is after Date2");
					} else if (dataDate.compareTo(nowDate) < 0) {
						System.out.println("Date1 is before Date2");
					}

					if (val.get("BetType").equals("CALL")) {

					} else if (val.get("BetType").equals("PUT")) {

					}
				} catch (Exception e) {
					System.out.println("系統發生不可預期錯誤:" + e);
				}
				// 時間大於特定區間

			});
		}

		System.out.println("Execute again after 60 seconds");

	}

	/**
	 * @author IMI-JAVA-Ryan 找尋價格並判斷是否勝利
	 * @return
	 */
	public static boolean findPriceAndJudgeOutcome() {

		return false;

	}

}
