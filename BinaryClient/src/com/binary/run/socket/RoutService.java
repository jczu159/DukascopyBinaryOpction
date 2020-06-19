package com.binary.run.socket;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

import com.binary.run.Servers;

public class RoutService extends Servers  implements Runnable {
	
	public static List<Socket> socketsObj = new Vector<>();
	private final ServerSocket serverSocket;
	private final ExecutorService pool;

	public RoutService(ExecutorService pool, ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
	}
	

	@Override
	public void run() {
		try {
			while (true) {
				
				Socket client = serverSocket.accept();
				socketsObj.add(client);
				pool.execute(new MessageService(serverSocket, client , socketsObj ));
				System.out.println("[初始化]" +   client.getInetAddress().toString()  + "已加入連線");
				
			}
		}
		catch (IOException e) {
			System.out.println("Server: IOException" + e);
		}
		finally {
			System.out.println("Server: Shutting down RoutService");
			pool.shutdown();
			try {
				pool.awaitTermination((long)4, TimeUnit.SECONDS);
				if(!serverSocket.isClosed()) {
					System.out.println("Server: Shutting down ServerSocket");
					serverSocket.close();
				}
			}
			catch (IOException e) {
				System.out.println("Server: IOException" + e);
			}
			catch (InterruptedException e) {
				System.out.println("Server: InterruptedException" + e);
			}
		}
	}
}
