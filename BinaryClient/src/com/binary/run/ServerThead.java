package com.binary.run;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;
import com.binary.run.util.LineNotification;

/**
 * 伺服器執行緒，主要來處理多個客戶端的請求
 */
public class ServerThead extends Servers implements Runnable {

	Socket socket;
	String socketName;

	public ServerThead(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			socketName = socket.getRemoteSocketAddress().toString();
			System.out.println("[初始化]" + socketName + "已加入連線");
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {

				String data = new String(buffer, 0, len);

				String msg = "[Get Message-Form]" + socketName + ": Message Body : " + data;
				System.out.println(msg);

				print(data);

			}

			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(socket.getInputStream()));
			// String s;
			// // 設定該客戶端的端點地址
			// socketName = socket.getRemoteSocketAddress().toString();
			// System.out.println("[初始化]" + socketName + "已加入連線");

			// socket
			// boolean flag = true;
			// while ((s = reader.readLine()) != null) {
			// // 阻塞，等待該客戶端的輸出流
			// String line = reader.readLine();
			// // 若客戶端退出，則退出連線。
			// if (line == null) {
			// flag = false;
			// continue;
			// }
			// // 用於回覆當前 socket io ping pon機制的 method
			// JSONObject jsonObj = new JSONObject();
			// if (line.contains("keepAlive-ping")) {
			// jsonObj = (JSONObject) jsonObj.parse(line);
			// String account = jsonObj.get("account") == null ? "" :
			// jsonObj.get("account").toString();
			// jsonObj.clear();
			// jsonObj.put("cmd", "keepAlive-pong");
			// jsonObj.put("account", account);
			// line = jsonObj.toString();
			// } else {
			// String msg = "[Get Message-Form]" + socketName + ": Message Body
			// : " + line;
			// System.out.println(msg);
			// }
			// // 向線上客戶端輸出資訊
			// print(line);
			// }

			closeConnect();
		} catch (IOException e) {
			System.out.println("伺服器發生錯誤:" + e);
			LineNotification.callEvent("1IT95jitr3oq1U6LD1dgV2gVXe8m4uoR0Hvjhq6mgFq",
					"伺服器發生IOException錯誤:" + e.toString());
			try {
				System.out.println("關閉伺服器");
				closeConnect();
			} catch (IOException e1) {
				LineNotification.callEvent("1IT95jitr3oq1U6LD1dgV2gVXe8m4uoR0Hvjhq6mgFq",
						"伺服器發生 IOException 錯誤:" + e1.toString());
				System.out.println("伺服器發生錯誤:" + e1);
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 向所有線上客戶端socket轉發訊息
	 * 
	 * @param msg
	 * @throws IOException
	 */
	private void print(String msg) throws IOException {
		PrintWriter out = null;
		synchronized (sockets) {
			for (Socket sc : sockets) {
				out = new PrintWriter(sc.getOutputStream());
				out.println(msg);
				out.flush();
			}
		}
	}

	/**
	 * 關閉該socket的連線
	 * 
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		System.out.println("[Socket protected]" + socketName + "已斷連線");
		// LineNotification.callEvent("1IT95jitr3oq1U6LD1dgV2gVXe8m4uoR0Hvjhq6mgFq",
		// "[Socket protected]" + socketName + "已斷連線");
		// // 移除沒連線上的客戶端
		synchronized (sockets) {
			sockets.remove(socket);
		}
		socket.close();
	}
}