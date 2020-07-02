package com.binary.run;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Servers {
	// 將接收到的socket變成一個集合
	protected static List<Socket> sockets = new Vector<>();

	public static void main(String[] args) throws IOException {
		// 建立服務端
		ServerSocket server = new ServerSocket(9877);
		boolean flag = true;
		// 接受客戶端請求
		while (flag) {
			try {
				// 阻塞等待客戶端的連線
				Socket accept = server.accept();
				synchronized (sockets) {
					sockets.add(accept);
				}
				// 多個伺服器執行緒進行對客戶端的響應
				Thread thread = new Thread(new ServerThead(accept));
				thread.start();
				// 捕獲異常。
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}
		// 關閉伺服器
		server.close();
	}

}