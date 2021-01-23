package chat;

import java.net.Socket;
import java.util.ArrayList;

import chat.core.ReceptRequest;
import chat.core.RequestDialog;
import chat.core.adapters.RequestDialogAdapter;
import chat.core.listeners.ReceptRequestListener;
import chat.entities.Origin;

public class Main {

	private static ReceptRequest reception;
	private static ArrayList<RequestDialog> dialogs = new ArrayList<RequestDialog>(); 
	
	public static void main(String[] args) {
		
		try {
			reception = new ReceptRequest(8080);
			reception.addListener(new ReceptRequestListener() {
				
				@Override
				public void newRequest(Socket socket) {
					System.out.println("Réception requête : " + socket.getInetAddress());
					RequestDialog dialog = new RequestDialog(socket);
					dialog.addListener(new RequestDialogAdapter() {
						
						@Override
						public void newMessage(Origin origin, String message) {
							System.out.println(origin.getUser() + ": " + message);
							
							if (!dialogs.isEmpty()) {
								for (RequestDialog dialog : dialogs) {
									if (!dialog.getOrigin().getUser().equals(origin.getUser()))
										dialog.sendMessage(origin.getUser(), message);
								}
							}
						}
						
						@Override
						public void disconnect(RequestDialog dialog) {
							String message = " s'est déconnecté du server !";
							System.out.println(dialog.getOrigin().getUser() + ": " + message);
							
							if (!dialogs.isEmpty()) {
								for (RequestDialog dial : dialogs) {
									if (dial.getOrigin() != dialog.getOrigin())
										dial.sendMessage(dialog.getOrigin().getUser(), message);
								}
							}
							
							dialogs.remove(dialog);
						}
					});
					dialog.start();
					dialogs.add(dialog);
				}
			});
			reception.run();
		} catch (Exception e) { e.printStackTrace(); }
		
	}

}
