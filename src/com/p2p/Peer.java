package com.p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Peer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter username and port number for this peer: ");
		String[] setupValues = bufferedReader.readLine().split(" ");
		ServerThread serverThread = new ServerThread(setupValues[1]);
		serverThread.start();
		new Peer().updateListenToPeers(bufferedReader, setupValues[0], serverThread);
		
	}

	private void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">> enter (space separated) hostname: port#");
		System.out.println("peers to recive message from (s to skip) : ");
		String input = bufferedReader.readLine();
		String[] inputValues = input.split(" ");
		if(!input.equals("s")) {
			for(int i=0; i < inputValues.length; i++) {
				String[] address = inputValues[i].split(":");
				Socket socket = null;
				try {
					socket = new Socket(address[0], Integer.valueOf(address[1]));
					new PeerThread(socket).start();
				}catch(Exception e) {
					if(socket != null) socket.close();
					else System.out.println("invalid input skipping to next step.");
				}
			}
		}
		communicate (bufferedReader, username, serverThread);
	}

	private void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
		// TODO Auto-generated method stub
		try {
			System.out.println("You can now communicate (e to exit, c to change)");
			while(true) {
				String message = bufferedReader.readLine();
				if(message.equals("e")) {
					break;
				}else if(message.equals("c")) {
					updateListenToPeers(bufferedReader, username, serverThread);
				}else {
//					StringWriter sw = new StringWriter();
//					Json.createWriter(sw).writeObject(Json.CreateObjectBuilder()
//							.add("username", username)
//							.add("message", message)
//							.build());
//					serverThread.sendMessage(sw.toString());
					String sw = "["+username+"]: "+message; 
					serverThread.sendMessage(sw.toString());
				}
			}
			System.exit(0);
		} catch (Exception e) {}
	}

}
