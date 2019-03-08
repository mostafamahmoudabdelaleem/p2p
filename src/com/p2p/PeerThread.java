package com.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerThread extends Thread {
	private BufferedReader bufferedReader;
	public PeerThread(Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		boolean flag = true;
		while(flag) {
			try {
//				JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
//				if(jsonObject.cotainsKey("username")) 
//					System.out.println("["+jsonObject.getString("username")+"]: "+jsonObject.getString("message"));
				System.out.println(bufferedReader.readLine());
			} catch (Exception e) { 
				flag = false;
				interrupt();
			}
		}
	}

}
