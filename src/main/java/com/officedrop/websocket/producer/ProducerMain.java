package com.officedrop.websocket.producer;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

public class ProducerMain {

	private static final List<ProducerWebSocket> producers = new LinkedList<ProducerWebSocket>();
	private static final ExecutorService pool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws Exception {
		WebSocketClientFactory factory = new WebSocketClientFactory();
		factory.setBufferSize(4096);
		factory.start();

		for (int x = 0; x < 4; x++) {

			WebSocketClient client = factory.newWebSocketClient();
			client.setMaxIdleTime(30000);
			client.setProtocol("ping_producer." + x);

			ProducerWebSocket socket = new ProducerWebSocket();
			client.open(new URI("ws://localhost:8080/ping_producer"), socket,
					10, TimeUnit.SECONDS);

			producers.add(socket);

		}

		System.out.println("Connected to websocket server");

		for ( ProducerWebSocket socket : producers ) {
			Producer p = new Producer( socket );
			pool.submit(p);
		}

		pool.shutdown();
		
		while ( !pool.isTerminated() ) {
			System.out.println( "Waiting for pool termination" );
			Thread.sleep(5000);
		}
		
		for (ProducerWebSocket socket : producers) {
			socket.getConnection().disconnect();
		}

		factory.stop();
	}

}
