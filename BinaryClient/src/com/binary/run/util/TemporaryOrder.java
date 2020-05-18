package com.binary.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;

import com.alibaba.fastjson.JSONObject;

public class TemporaryOrder {

	static ConcurrentHashMap <String, JSONObject> hashMap = new ConcurrentHashMap <>();

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

	public static boolean putCover(String mapKey, JSONObject json) {
		boolean MapNotNull = false;
		hashMap.put(mapKey, json);

		if (hashMap.get(mapKey) != null) {
			MapNotNull = true;
		}
		return MapNotNull;

	}

	public static ConcurrentHashMap  get() {

		return hashMap;

	}

	public static boolean reomve(String key) {
		boolean success = false;
		hashMap.remove(key);
		if (hashMap.get(key) == null) {
			success = true;
		}
		return success;

	}

}