package chat.core;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import chat.core.listeners.ReceptRequestListener;

public class ReceptRequest extends Thread {
	
	private ArrayList<ReceptRequestListener> listeners;
	private ServerSocket server;
	
	private int port;
	private boolean continu = true;
	
	public ReceptRequest(int port) {
		listeners = new ArrayList<ReceptRequestListener>();
		this.port = port;
		setDaemon(true);
	}
	
	public void addListener(ReceptRequestListener listener) {
		listeners.add(listener);
	}
	
	public void fireNewRequest(Socket socket) {
		for (ReceptRequestListener listener : listeners)
			listener.newRequest(socket);
	}
	
	@Override
	public void run() {
		try {
			
			server = new ServerSocket(port);
			server.getInetAddress();
			System.out.println("Start listening on port : " + InetAddress.getLocalHost() + " : " + server.getLocalPort());
			
			while (continu) {
				System.out.println("waiting");
				Socket s = server.accept();
				System.out.println("sending");
				fireNewRequest(s);
				System.out.println("sended");
			}

			System.out.println("End listening on port : " + port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
