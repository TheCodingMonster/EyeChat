package com.eyeball.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import com.eyeball.utils.logging.Logger;

public class ClientConnectionThread extends Thread {

	public Logger MY_LOGGER;

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
		MY_LOGGER = new Logger("Client-Logger", this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		part: try {
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
					break part;
				}

			} catch (IOException e) {
				MY_LOGGER.error("Error in waiting for client to connect!");
				e.printStackTrace();
				clientOut.close();
				clientIn.close();
				serverSocket.close();
				break part;
			}
			if (clientSocket.isClosed()) {
				MY_LOGGER.warn("Connection from client " + clientName
						+ " closed unexpectly!");
				clientOut.close();
				clientIn.close();
				serverSocket.close();
				break part;
			}

			Main.usedClientNames.add(clientName);
			Main.clients.put(port, this);

			if (clientName.toLowerCase().contains("rebecca black"))
				new BroadcastThread(this,
						"(WARNING: POTENTIAL REBECCA BLACK FRIDAY FAN!) "
								+ clientName + " has joined the room",
						eyeChatUsername).start();
			else
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

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("hello")
						| message.toLowerCase().contains("hi ")) {
					// Prevent messages like "him" to trigger
					// Says hello to @EyeChat
					switch (new Random().nextInt(5)) {
					case 0:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " Hello.\n");
						break;
					case 1:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " Hi.\n");
						break;
					case 2:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " What up?\n");
						break;
					case 3:
						clientOut
								.write(eyeChatUsername
										+ ";@"
										+ clientName
										+ " Sorry "
										+ clientName
										+ ", I'm late for a meeting. Gotta go. talk to you later.\n");
						clientOut.write(eyeChatUsername + ";@"
								+ "EyeChat left this room.\n");
						break;
					default:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ "Sorry, I don't understand you");
					}
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("thank")) {
					// Thanks @EyeChat
					clientOut.write(eyeChatUsername + ";@" + clientName
							+ " Your'e welcome\n");
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("why")) {
					// Asking @EyeChat a question
					clientOut.write(eyeChatUsername + ";@" + clientName
							+ " Because its like that\n");
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("if")) {
					// Asking @EyeChat another question
					switch (new Random().nextInt(4)) {
					case 0:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " I will cry!\n");
						break;
					case 1:
						clientOut
								.write(eyeChatUsername
										+ ";@"
										+ clientName
										+ " I will call the police to report that you stole my brains\n");
						break;
					case 2:
						clientOut
								.write(eyeChatUsername
										+ ";@"
										+ clientName
										+ " I will ignore you as you keep bugging me!\n");
					default:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ "Sorry, I don't understand you");
					}
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("when")) {
					// Asking @EyeChat yet another question
					switch (new Random().nextInt(4)) {
					case 0:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " I will run away!\n");
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " (@EyeChat runs away...)\n");
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " EyeChat left this room.\n");
						break;
					case 1:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " I don't really care much about that.\n");
						break;
					case 2:
						clientOut
								.write(eyeChatUsername
										+ ";@"
										+ clientName
										+ " I will ignore you as you keep bugging me!\n");
						break;
					default:
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ "Sorry, I don't understand you");
					}
					clientOut.flush();
					continue;
				}

				if (message.toLowerCase().contains("@eyechat")
						&& message.toLowerCase().contains("are")) {
					// Asking @EyeChat yet another question
					if (new Random().nextInt(2) == 1)
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " No\n");
					else
						clientOut.write(eyeChatUsername + ";@" + clientName
								+ " Yes\n");
					clientOut.flush();
					continue;
				}

				if ((message.toLowerCase().contains("rebecca black") && message
						.toLowerCase().contains("friday"))
						| message.toLowerCase().contains("kfvsfosbjy0")) {
					// potential friday link
					new BroadcastThread(this, clientName,
							"(WARNING: POTENTIAL REBECCA BLACK FRIDAY MESSAGE): "
									+ message).start();
					continue;
				}

				if (message.toLowerCase().equals("/clear")) {
					for (int i = 1; i < 100; i++) {
						clientOut.write("\n");
					}
					clientOut.flush();
					continue;
				}

				// Normal message, no RB or EyeChat ai.
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
		}
		this.stop();
		return;
	}
}
