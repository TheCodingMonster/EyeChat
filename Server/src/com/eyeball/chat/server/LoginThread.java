package com.eyeball.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class LoginThread extends Thread {

	private int port;
	private Socket clientSocket;
	
	private String clientName;

	public LoginThread(int port, Socket clientSocket) {
		this.port = port;
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			BufferedWriter clientOut = new BufferedWriter(
					new OutputStreamWriter(clientSocket.getOutputStream()));
			System.out.println("  Sending port...");
			clientOut.write(port + "\n");
			clientOut.flush();
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			System.out.println("  Reading username...");
			String name = clientIn.readLine();
			System.out.println("  Username " + name);
			if (name == null) {
				Main.CLIENT_HANDLER.info("  Client disconnected!");
				return;
			}
			name = name.trim();
			if (Main.usedClientNames.contains(name.replaceAll("\n", ""))) {
				clientOut.write("UserName used\n");
				clientOut.newLine();
				clientOut.close();
				clientIn.close();
				clientSocket.close();
				return;
			}
			if (name.isEmpty()) {
				Main.CLIENT_HANDLER
						.info("Client attempted to connect with empty username. IP: "
								+ clientSocket.getInetAddress()
										.getHostAddress());
				clientOut.write("UserName empty\n");
				clientOut.newLine();
				clientOut.close();
				clientIn.close();
				clientSocket.close();
				return;
			}
			clientOut.write("OK\n");
			clientOut.flush();
			boolean typeText = true;
			try {
				typeText = Boolean.parseBoolean(clientIn.readLine());
			} catch (Exception e) {
			}
			clientOut.close();
			clientIn.close();
			clientSocket.close();

			clientName = name;
			
			ClientConnectionThread connectionThread = new ClientConnectionThread(
					clientSocket, port, name, typeText);
			connectionThread.start();
			Main.clients.put(port, connectionThread);
		} catch (IOException e) {
			e.printStackTrace();
			Main.usedClientNames.remove(clientName);
		}
	}

}
