package com.eyeball.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static String name, server;

	public static int connectionPort = 0;

	public static Socket chatSocket;

	public static void main(String[] args) throws InterruptedException {
		try {
			System.out.print("Username: ");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			name = reader.readLine();
			System.out.print("Host: ");
			server = reader.readLine();
		} catch (IOException e) {
			System.err.println("Failed to get details, aborting");
		}
		try {
			Thread.sleep(1000);
			Socket authSock = new Socket(server, 3000);
			BufferedReader portReader = new BufferedReader(
					new InputStreamReader(authSock.getInputStream()));
			BufferedWriter authWriter = new BufferedWriter(
					new OutputStreamWriter(authSock.getOutputStream()));
			System.out.println("Attempting to login...");
			System.out.println("  Getting connection port...");
			connectionPort = Integer.parseInt(portReader.readLine());
			System.out.println("  Connection port is " + connectionPort);
			
			System.out.println("  Sending username...");
			name += "\n";
			
			authWriter.write(name);
			authWriter.flush();
			
			System.out.println("  Waiting for response...");
			String result = portReader.readLine();
			if (result.equals("UserName empty")) {
				System.err.println("Cannot auth, username empty!");
				System.exit(5);
			} else if (result.equals("UserName used")) {
				System.err.println("Cannot login, username used!");
				System.exit(6);
			}
			authWriter.write("true\n");
			authWriter.flush();
			System.out.println("Login sucessful! Using port " + connectionPort);
			portReader.close();
			authSock.close();
		} catch (UnknownHostException e) {
			System.err.println("java.net.UnknownHostException: Unknown host "
					+ server);
			System.exit(3);
		} catch (IOException e) {
			
			System.err.println("Could not connect to server! Stacktrace: ");
			e.printStackTrace();
			System.exit(2);
		}
		Thread.sleep(3000);
		try {
			chatSocket = new Socket(server, connectionPort);
			new MessageReaderThread(new BufferedReader(new InputStreamReader(
					chatSocket.getInputStream()))).start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					chatSocket.getOutputStream()));
			System.out.println("Sucessfully connected to chat!");
			System.out.println("Now waiting for messages...");
			name = name.replaceAll("\n", "");
			System.out.print(name + ": ");
			for (String message = in.readLine(); message != null; message = in
					.readLine()) {
				out.write(message + "\n");
				out.flush();
				System.out.print(name + ": ");
			}
		} catch (UnknownHostException e) {
			System.err.print("java.net.UnknownHostException: Unknown host "
					+ server);
			System.exit(3);
		} catch (IOException e) {
			System.err.print("java.io.IOException: " + e.getMessage());
			e.printStackTrace();
			System.exit(2);
		}
		System.out.println("Disconnected from server! Check your internet connection and try again.");
		System.out.println("Or it could be that the server was shut down.");
	}
}
