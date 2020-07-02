package com.binary.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.LineNotification;

class SocketClient {
	private Socket socket;
	private boolean tryToReconnect = true;
	private Thread heartbeatThread = null;
	private long heartbeatDelayMillis = 10000;
	private Integer reTryCount = 0;
	HashedMap lastTime = new HashedMap();
	PrintWriter out = null;

	public void Client(final String server, final int port) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cmd", "keepAlive-pong");
		jsonObj.put("account", "admin");

		connect(server, port);

		// LineNotification.callEvent("1pss4rEhlxqQztc5Erm2crEQkIHDMABIigZ8g6qZnVy",
		// "監控重新開始，開始監控Socket是否阻塞");

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		heartbeatThread = new Thread() {
			public void run() {
				while (tryToReconnect) {
					// send a test signal
					try {

						socket.setSoTimeout(30000);

						out = new PrintWriter(socket.getOutputStream());
						out.println(jsonObj.toJSONString() + "/n");
						out.flush();
						reTryCount += 1;

						if (reader != null) {
							Date date = new Date();// 获取当前时间
							System.out.println("取得資料最後時間：" + sdf.format(date) + "--> " + reader.readLine()); // 输出已经格式化的现在时间（24小时制）
							lastTime.put("lastTime", date);
							reTryCount -= 1;

							if (reader.readLine().contains("resterAll")) {
								reStart();
							}

						}

						System.out.println("偵測" + reTryCount + "次數");
						if (reTryCount >= 30) {
							System.out.println("警告已經斷線");
							LineNotification.callEvent("1pss4rEhlxqQztc5Erm2crEQkIHDMABIigZ8g6qZnVy",
									"伺服器發生阻塞事件，正在重新啟動伺服器中，請稍後");
						}

						sleep(heartbeatDelayMillis);

					} catch (SocketTimeoutException ste) {
						tryToReconnect = false;
						reTryCount = 0;
						reStart();
						LineNotification.callEvent("1pss4rEhlxqQztc5Erm2crEQkIHDMABIigZ8g6qZnVy", "伺服器發生time Out ");

					} catch (InterruptedException e) {
						// You may or may not want to stop the thread here
						tryToReconnect = false;
					} catch (IOException e) {

					}
				}
			};
		};
		heartbeatThread.start();
	}

	public static void reStart() {

		String lastPid = "";
		try {
			Runtime runtime = Runtime.getRuntime();
			String cmds[] = { "cmd", "/c", "jps -lv" };
			Process proc = runtime.exec(cmds);
			InputStream inputstream = proc.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line;
			while ((line = bufferedreader.readLine()) != null) {
				String pid = line.substring(0, line.indexOf(" ")).trim();
				System.out.println(pid);
				System.out.println(line);
				if (line.contains("SockerServer.jar")) {
					sendLine("開始執行關閉伺服器");

					Runtime closeServer = Runtime.getRuntime();
					String closeCmd[] = { "cmd", "/c", "taskkill /F /PID " + pid + "" };
					Process compro = closeServer.exec(closeCmd);
					sendLine("執行關閉中 請等候五秒");

					System.out.println("關閉中 .. 等待五秒");
					Thread.sleep(2000);

					sendLine("重新啟動伺服器");
					System.out.println("重新啟動伺服器");
					// C:\Users\Administrator\Desktop\Socket_server
					Process p = Runtime.getRuntime().exec("cmd /c start SocketServer.bat", null,
							new File("C:\\Users\\Administrator\\Desktop\\Socket_server"));

				} else if (line.contains("WebHook.jar")) {

					sendLine("關閉 webHook機器人程式");
					Runtime closeWebHook = Runtime.getRuntime();
					String closeHookCmd[] = { "cmd", "/c", "taskkill /F /PID " + pid + "" };
					Process compro2 = closeWebHook.exec(closeHookCmd);
					// C:\Users\Administrator\Desktop\WebHook\TgServer\ForexWebhook
					// C:\Users\Administrator\Desktop\WebHook\TgServer\ForexWebhook
					sendLine("重新啟動 webHook機器人程式");
					Process p = Runtime.getRuntime().exec("start cmd /c start WebHook.bat", null,
							new File("C:\\Users\\Administrator\\Desktop\\WebHook\\TgServer\\ForexWebhook"));

				} else if (line.contains("SockerClient.jar")) {
					sendLine("重新啟動 監控程式");
					lastPid = pid;
				}

				// if (line.contains("WebHook.jar")) {
				// Runtime closeWebHook = Runtime.getRuntime();
				// String closeHookCmd[] = { "cmd", "/c", "taskkill /F /PID " +
				// pid + "" };
				// Process compro2 = closeWebHook.exec(closeHookCmd);
				// }

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cannot query the tasklist for some reason.");
		}
		try {
			sendLine("重新啟動 監控程式");
			Runtime closeWebHook = Runtime.getRuntime();
			String closeHookCmd[] = { "cmd", "/c", "taskkill /F /PID " + lastPid + "" };
			Process compro2 = closeWebHook.exec(closeHookCmd);
			// C:\Users\Administrator\Desktop\Socket_server\SocketClient
			sendLine("重新啟動 監控程式 此動作需等待 30秒才完成生效");
			Process p = Runtime.getRuntime().exec("cmd /c start SocketClient.bat", null,
					new File("C:\\Users\\Administrator\\Desktop\\Socket_server\\SocketClient"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connect(String server, int port) {
		try {
			socket = new Socket(server, port);
		} catch (UnknownHostException e) {

		} catch (IOException e) {

		}
	}

	public void shutdown() {
		tryToReconnect = false;
	}

	public static void sendLine(String msg) {
		LineNotification.callEvent("1pss4rEhlxqQztc5Erm2crEQkIHDMABIigZ8g6qZnVy", msg);
	}

	public static void main(String[] args) throws IOException {

		//
		SocketClient ex = new SocketClient();
		ex.Client("45.32.49.87", 9877);
	}
}
