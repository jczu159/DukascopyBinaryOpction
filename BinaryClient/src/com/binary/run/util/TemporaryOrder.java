package com.binary.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

public class TemporaryOrder {

	static HashMap<String, JSONObject> hashMap = new HashMap<>();

	
	public static boolean put(JSONObject json) {
		boolean MapNotNull = false;
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		Date date = new Date();// 获取当前时间
		hashMap.put(sdf.format(date), json);

		if (hashMap.get(sdf.format(date)) != null) {
			MapNotNull = true;
		}
		return MapNotNull;
	}

}