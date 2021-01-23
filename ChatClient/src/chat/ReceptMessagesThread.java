package chat;

import java.io.BufferedReader;
import java.net.Socket;

public class ReceptMessagesThread extends Thread {
	
	private Socket socket;
	private BufferedReader reader;
	
	public ReceptMessagesThread(Socket s, BufferedReader reader) {
		this.socket = s;
		this.reader = reader;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("start - reception");
			while (!socket.isClosed()) {
				String readed = reader.readLine();
				System.out.println(readed);
			}
			
			reader.close();
			System.out.println("ended");
		
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
