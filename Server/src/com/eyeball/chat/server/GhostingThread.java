package com.eyeball.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GhostingThread extends Thread {

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			for (String message = in.readLine(); message != null; message = in
					.readLine()) {
				System.out.println("Message: " + message);
				for (ClientConnectionThread clientConnectionThread : Main.clients
						.values()) {
					if (clientConnectionThread == null)
						continue;
					if (clientConnectionThread.clientOut == null)
						continue;
					clientConnectionThread.clientOut
							.write(clientConnectionThread.eyeChatUsername + ";"
									+ message + "\n");
					clientConnectionThread.clientOut.flush();
					clientConnectionThread.clientOut.flush();
					System.out.println("Broadcast " + clientConnectionThread.eyeChatUsername + ";"
							+ message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
