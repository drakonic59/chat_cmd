package chat.core.listeners;

import chat.core.RequestDialog;
import chat.entities.Origin;

public interface RequestDialogListener {
	
	void newMessage(Origin origin, String message);
	void disconnect(RequestDialog dialog);
	
	
}
