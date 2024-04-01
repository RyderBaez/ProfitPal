package ProfitPalPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class WebConnector {
	Vector<UserThread> serverThreads;
	public WebConnector(int port) {
		try {
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<UserThread>();
			while(true) {		
				// to do --> accept socket connections
				Socket s = ss.accept();
				// to do --> add new ServerThread(s)
				serverThreads.add(new UserThread(s, this));
			}
		} catch (IOException ioe) {
			System.out.println("ioe in Server constructor: " + ioe.getMessage());
		}		
	}
	public static void main(String [] args) {
		
	}
}
