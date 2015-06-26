package com.eyeball.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.eyeball.utils.logging.Logger;

public class ClientConnectionThread extends Thread {

	public Logger MY_LOGGER = new Logger("Client-Logger", this);

	public BufferedReader clientIn;
	public BufferedWriter clientOut;

	public Socket clientSocket;
	public ServerSocket serverSocket;

	public String clientAddress = "";

	public String eyeChatUsername = "EyeChat";

	int port;

	public String clientName = "";

	public ClientConnectionThread(Socket client, int port, String clientName,
			boolean typeText) {
		clientAddress = client.getInetAddress().getHostAddress();
		setName("Client-Connection-" + clientAddress);
		this.port = port;
		this.clientName = clientName;
		if (!typeText)
			eyeChatUsername += "\ud83d\udd2b\ud83d\udc35";
	}

	@Override
	public void run() {
		try {
			try {
				MY_LOGGER.info("Waiting for client " + clientAddress
						+ " to reconnect on port " + port);
				serverSocket = new ServerSocket(port);
				serverSocket.setSoTimeout(30000); // 30 Seconds
				clientSocket = serverSocket.accept();
				MY_LOGGER.info("Client " + clientAddress
						+ " connected to chat sucessfully!");
				MY_LOGGER.info("Binding to port " + this.port);
				try {
					clientIn = new BufferedReader(new InputStreamReader(
							clientSocket.getInputStream()));
					clientOut = new BufferedWriter(new OutputStreamWriter(
							clientSocket.getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

			} catch (IOException e) {
				MY_LOGGER.error("Error in waiting for client to connect!");
				e.printStackTrace();
				clientOut.close();
				clientIn.close();
				serverSocket.close();
				return;
			}
			if (clientSocket.isClosed()) {
				MY_LOGGER.warn("Connection from client " + clientName
						+ " closed unexpectly!");
				clientOut.close();
				clientIn.close();
				serverSocket.close();
				return;
			}

			Main.usedClientNames.add(clientName);
			Main.clients.put(port, this);

			new BroadcastThread(this, clientName + " has joined the room",
					eyeChatUsername).start();

			while (true) {
				String message = clientIn.readLine();
				if (message == null)
					break;
				if (message.isEmpty())
					continue;
				if (message.equals("/ExitChat") | message.equals("/QuitChat"))
					break;
				if (message.equals("/Who")) {
					for (String user : Main.usedClientNames) {
						clientOut.write(eyeChatUsername + ";" + user + "\n");
					}
					clientOut.write(eyeChatUsername + ";EyeChat\n");
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().equals("@eyechat why no one around?")) {
					clientOut.write(eyeChatUsername
							+ ";Because its like that\n");
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().equals("/clear")) {
					for (int i = 1; i < 100; i++) {
						clientOut.write("\n");
					}
					clientOut.flush();
					continue;
				}

				MY_LOGGER.info("Recieved message " + message + " from client "
						+ clientName + "!");
				new BroadcastThread(this, message, clientName).start();
			}
			MY_LOGGER.info("Client " + clientName + " exited!");
			Main.usedClientNames.remove(clientName);
			Main.clients.remove(this);
			clientIn.close();
			clientOut.close();
			serverSocket.close();
			clientSocket.close();
		} catch (IOException e) {
			MY_LOGGER.error("Error in recieving message from client "
					+ clientName + " !");
			Main.usedClientNames.remove(clientName.replace("\n", ""));
			Main.clients.remove(port);
			new BroadcastThread(this, clientName + " left this room",
					eyeChatUsername).start();
			return;
		}
	}
}
