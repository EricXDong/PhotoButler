package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	
	private ServerSocket socket;
	
	public Server (int port) {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run () {
		while (true) {
			try {
				System.out.println("Listening on port " + socket.getLocalPort());
				Socket req = socket.accept();
				System.out.println("Request from " + req.getRemoteSocketAddress());
				
				//	Read data
				BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
				for (String line = br.readLine(); line != null;) {
					System.out.println(line);
				}
				
				req.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
