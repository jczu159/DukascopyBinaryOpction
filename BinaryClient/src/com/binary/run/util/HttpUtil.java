package com.binary.run.util;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {

	public static void sendPost(JSONObject json) throws IOException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost("http://yoururl");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			System.out.println(httpClient.execute(request));
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.close();
		}
	}
}
