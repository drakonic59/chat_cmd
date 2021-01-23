package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.listeners.CommandListener;

public class Main implements CommandListener {

	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Connexion au server ...");
//		System.out.println("Quel port utiliser ? ");
//		
//		int port = Integer.parseInt(sc.nextLine());
		System.out.print("Tentative...");
		
		try {
			
			Socket s = new Socket(InetAddress.getByName("82.165.75.208"), 8080);
			System.out.println(" Réussie !");
			
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
			System.out.println(br.readLine());
			
			String user = sc.nextLine();
			pw.println(user);
			pw.flush();
			
			System.out.println(br.readLine());
			
			SendMessagesThread sends = new SendMessagesThread(s, pw, sc);
			ReceptMessagesThread messages = new ReceptMessagesThread(s, br);

			sends.start();
			messages.start();
			
		} catch (Exception e) {e.printStackTrace();}
		
	}

	@Override
	public void newCommand(String command) {
		
	}

}
