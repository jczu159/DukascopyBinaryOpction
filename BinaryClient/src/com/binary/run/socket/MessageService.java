package com.binary.run.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class MessageService implements Runnable {

	private final Socket client;
	private final ServerSocket serverSocket;
	private static List<Socket> socketsObj;

	MessageService(ServerSocket serverSocket, Socket client, List<Socket> socketsObj) {
		this.client = client;
		this.serverSocket = serverSocket;
		this.socketsObj = socketsObj;
	}

	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		String clientName = client.getInetAddress().toString();
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(clientName + ": " + line);
				print(line);
//				out.println(line);
//				out.flush();
			}
		} catch (IOException e) {
			System.out.println("Server/MessageService: IOException");
		} finally {
			if (!client.isClosed()) {
				System.out.println("Server: Client disconnected");
				try {
					client.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void print(String msg) throws IOException {

		PrintWriter out = null;
		for (Socket sc : socketsObj) {
			out = new PrintWriter(sc.getOutputStream());
			out.println(msg);
			out.flush();
		}
	}
}
