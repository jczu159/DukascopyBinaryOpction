package com.binary.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ClientTest {
	public int port = 9877;
	Socket socket = null;

	public static void main(String[] args) {
		new ClientTest(); // 開始執行
	}

	public ClientTest() {
		try {
			socket = new Socket("45.32.49.87", port);
			new Cthread().start();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg1;
			while ((msg1 = br.readLine()) != null) {
				System.out.println(msg1);
				String amountlist[] = { "2", "5", "11", "25", "60", "140", "320" };
				JSONObject json = new JSONObject();
				System.out.println("取得json字串:" + msg1);

				boolean isJson = isJSONValid(msg1);
				System.out.println("是不是JSON" + isJson);

				if (isJson == true) {
					
					JSONObject jsOBj = (JSONObject) json.parse(msg1);
					String Symbol = jsOBj.getString("symbol");
					Integer amountListInt = Integer.valueOf(jsOBj.getString("martingale"));
					String Amount = amountlist[amountListInt];
					String BetHour = jsOBj.getString("expireHourTime");
					String BetMinute = jsOBj.getString("expireMinuteTime");
					String BetType = jsOBj.getString("direction");
					System.out.println("或取道的JSON結果" + BetType);

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

}