package com.binary.run.util;

import java.util.HashMap;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Data;

public class TimeJob extends TimerTask {

	@Override
	public void run() {
		HashMap<String, JSONObject> ObjData = TemporaryOrder.get();

		if (ObjData != null) {
			ObjData.forEach((key, val) -> {
				System.out.println("取得等待判斷的資料:" + val.toJSONString());
				Data time = (Data) val.get("betTime");

				// 時間大於特定區間
				if (val.get("BetType").equals("CALL")) {

				} else if (val.get("BetType").equals("PUT")) {

				}

			});
		}

		System.out.println("Execute again after 60 seconds");

	}

}
