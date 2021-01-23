package chat.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import chat.core.listeners.RequestDialogListener;
import chat.entities.Origin;

public class RequestDialog extends Thread {
	
	private ArrayList<RequestDialogListener> listeners;
	private Origin origin;
	
	private PrintWriter pw;
	private BufferedReader br;
	
	private Socket socket;
	
	public RequestDialog(Socket s) {
		listeners = new ArrayList<RequestDialogListener>();
		origin = new Origin("", s.getInetAddress());
		socket = s;
	}
	
	public void addListener(RequestDialogListener listener) {
		listeners.add(listener);
	}
	
	public void fireNewMessage(String message) {
		for (RequestDialogListener listener : listeners)
			listener.newMessage(origin, message);
	}
	
	public void fireDisconnection() {
		for (RequestDialogListener listener : listeners)
			listener.disconnect(this);
	}
	
	@Override
	public void run() {
		try {
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			pw.println("Your username : ");
			pw.flush();
			
			origin.setUser(br.readLine());
			fireNewMessage("s'est connecté au server !");
			
			pw.println("Connected !");
			pw.flush();
			
			String message;
			while ( !( (message = br.readLine() ).equalsIgnoreCase("quit") ) )
				fireNewMessage(message);
			
			fireDisconnection();
			
			pw.close();
			br.close();
			socket.close();
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void sendMessage(String origin, String message) {
		pw.println(origin + ": " + message);
		pw.flush();
	}
	
	public Origin getOrigin() {
		return origin;
	}
	
}
