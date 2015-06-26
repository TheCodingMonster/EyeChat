package com.eyeball.chat.client;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReaderThread extends Thread {

	private BufferedReader in;

	public MessageReaderThread(BufferedReader in) {
		this.in = in;
	}

	@Override
	public void run() {

		System.out.println("Ready to recieve messages!");
		try {
			
			for (String message = in.readLine(); message != null; message = in
					.readLine()) {
				if (message.isEmpty()) {
					System.out.println();
					continue;
				}
				String[] parts = message.split(";");
				System.out.println(parts[0] + ": " + parts[1]);
			}
		} catch (IOException e) {
			System.exit(2);
		}
	}
}
