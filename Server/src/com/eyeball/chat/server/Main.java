package com.eyeball.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.eyeball.utils.logging.Logger;

public class Main {

	public static Logger CLIENT_HANDLER = new Logger(
			"Client-Server-Connection-Handler");
	public static Logger EYECHAT_MAIN = new Logger("EyeChat-Server");
	public static ServerSocket CONNECTION_TMP_SOCKET;

	public static ArrayList<String> usedClientNames = new ArrayList<String>();

	public static HashMap<Integer, ClientConnectionThread> clients = new HashMap<Integer, ClientConnectionThread>();

	public static int lastPort = 3001;

	public static void main(String[] args) {
		EYECHAT_MAIN.info("EyeChat-Server started...");
		EYECHAT_MAIN.info("Attempting to bind connection to port 3000...");
		try {
			CONNECTION_TMP_SOCKET = new ServerSocket(3000);
		} catch (IOException e) {
			EYECHAT_MAIN.error("Could not bind to port 3000!");
			EYECHAT_MAIN
					.error("This is an error that cannot be fixed. Please open the port and try again.");
			System.exit(1);
		}
		EYECHAT_MAIN.info("Sucessfully connected binded to port 3000!");
		new GhostingThread().start();
		EYECHAT_MAIN.info("Waiting for client to connect...");
		while (true) {
			int port = lastPort++;
			Socket clientSocket = null;
			try {
				clientSocket = CONNECTION_TMP_SOCKET.accept();
			} catch (IOException e) {
				EYECHAT_MAIN.error("Error in client connection!");
				e.printStackTrace();
				lastPort--;
			}
			if (clientSocket == null) {
				EYECHAT_MAIN.warn("Client Connection is null!");
				lastPort--;
				continue;
			}
			EYECHAT_MAIN.info("Client connected!");
			new LoginThread(port, clientSocket).start();
		}
	}

}
