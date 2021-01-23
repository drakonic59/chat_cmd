package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessagesThread extends Thread {
	
	private Socket socket;
	private PrintWriter pw;
	private Scanner sc;
	
	public SendMessagesThread(Socket s, PrintWriter pw, Scanner sc) {
		this.socket = s;
		this.pw = pw;
		this.sc = sc;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("start - sends");
			String message;
			while ( !( (message = sc.nextLine()).equalsIgnoreCase("quit") ) ) {
				pw.println(message);
				pw.flush();
			}
			
			pw.println("quit");
			pw.flush();
			
			pw.close();
			sc.close();
			socket.close();
			
			System.out.println("ended");
		
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
