package chat.core.listeners;

import java.net.Socket;

public interface ReceptRequestListener {
	
	void newRequest(Socket socket);
	
}
