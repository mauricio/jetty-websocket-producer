package com.officedrop.websocket.producer;

public class Producer implements Runnable {

	public Producer( ProducerWebSocket socket ) {
		this.socket = socket;
	}
	
	private ProducerWebSocket socket;


	public void run() {

		for (int x = 0; x < 1000; x++) {
			try {
				this.socket.getConnection().sendMessage(
						String.format("ping.%d@%d", x,
								System.currentTimeMillis()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

}
