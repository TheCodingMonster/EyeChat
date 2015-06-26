package com.eyeball.chat.server;

import java.io.IOException;

public class BroadcastThread extends Thread {

	private ClientConnectionThread sender;
	private String message;

	private String clientName;

	public BroadcastThread(ClientConnectionThread sender, String message,
			String clientName) {
		System.out.println("Broadcasting message " + message + " from "
				+ clientName);
		this.sender = sender;
		this.message = message;
		this.clientName = clientName;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		System.out.println("Broadcasting...");
		for (ClientConnectionThread c : Main.clients.values()) {
			if (c == null)
				continue;
			if (c.clientOut == null)
				continue;
			if (c != sender) {
				System.out.println("Sending message to client " + c.clientName);
				try {
					if (clientName.equals("UniKitty") | clientName.equals("Eyeballcode")) {
						c.clientOut.write(clientName.replaceAll("\n", "") + ";"
								+ message.replaceAll("\n", "") + "\n");
						c.clientOut.flush();
					} else {
					c.clientOut.write(clientName.replaceAll("\n", "") + ";"
							+ message.replaceAll("\n", "") + "\n");
					c.clientOut.flush();}
					// Example: "Eyeball;Hello World!"
					// Sends a message "Hello World!" from client "Eyeball"
					System.out.println("Done!");
				} catch (IOException e) {
				}
			}

		}
		this.stop();
	}
}
