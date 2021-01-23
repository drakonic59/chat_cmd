package chat.core;

import java.net.Socket;
import java.util.ArrayList;

import chat.core.listeners.ReceptRequestListener;
import chat.core.listeners.RequestDialogListener;
import chat.entities.Origin;

public class ChatServer implements ReceptRequestListener, RequestDialogListener {
	
	private ReceptRequest reception;
	private ArrayList<RequestDialog> dialogs = new ArrayList<RequestDialog>(); 
	
	public ChatServer() {
		reception = new ReceptRequest(0);
		reception.addListener(this);
		reception.run();
	}

	@Override
	public void newRequest(Socket socket) {
		System.out.println("Réception requête : " + socket.getInetAddress());
		RequestDialog dialog = new RequestDialog(socket);
		dialog.addListener(this);
		dialog.start();
		dialogs.add(dialog);
	}

	@Override
	public void newMessage(Origin origin, String message) {
		System.out.println(origin.getUser() + ": " + message);
		
		if (!dialogs.isEmpty()) {
			for (RequestDialog dialog : dialogs) {
				if (dialog.getOrigin() != origin)
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
	
}
