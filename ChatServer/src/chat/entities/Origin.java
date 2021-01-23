package chat.entities;

import java.net.InetAddress;

public class Origin {
	
	private String user;
	private InetAddress address;
	
	public Origin() {
		
	}
	
	public Origin(String user, InetAddress address) {
		setUser(user);
		setAddress(address);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}
	
}
