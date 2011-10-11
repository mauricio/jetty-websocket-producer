package com.officedrop.websocket.producer;

import org.eclipse.jetty.websocket.WebSocket;

public class ProducerWebSocket implements WebSocket.OnTextMessage {

	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void onClose(int closeCode, String message) {	

	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void onMessage(String message) {
		
	}
	
}
