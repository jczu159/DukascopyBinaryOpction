package com.binary.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 設定該客戶端的端點地址
			socketName = socket.getRemoteSocketAddress().toString();
			System.out.println("[初始化]" + socketName + "已加入連線");
			boolean flag = true;
			while (flag) {
				// 阻塞，等待該客戶端的輸出流
				String line = reader.readLine();
				// 若客戶端退出，則退出連線。
				if (line == null) {
					flag = false;
					continue;
				}
				String msg = "[Get Message-Form]" + socketName + ": Message Body : " + line;
				System.out.println(msg);
				// 向線上客戶端輸出資訊
				print(line);
			}

			closeConnect();
		} catch (IOException e) {
			try {
				closeConnect();
			} catch (IOException e1) {
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

		// 移除沒連線上的客戶端
		synchronized (sockets) {
			sockets.remove(socket);
		}
		socket.close();
	}
}