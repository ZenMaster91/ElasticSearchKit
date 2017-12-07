package ESKit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EKConnection {
	
	private String clusterName = "elasticsearch", hostName = "localhost";
	private int port = 9300;
	private Client client;
	private TransportClient transportClient;
	
	public EKConnection() {}
	
	public EKConnection(String clusterName) {
		setClusterName(clusterName);
	}

	public EKConnection(String clusterName, int port) {
		this(clusterName);
		setPort(port);
	}

	/**
	 * @return the clusterName
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}
	
	/**
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public Client connect() throws UnknownHostException {
		transportClient = new PreBuiltTransportClient(Settings.builder()
				.put("cluster.name", getClusterName())
				.put("client.transport.sniff", false)
				.put("client.transport.ping_timeout", 20, TimeUnit.SECONDS)
				.build());

		transportClient.addTransportAddress(new TransportAddress(
				InetAddress.getByName(getHostName()), getPort()));

		this.client = transportClient;
		return getClient();
	}
	
	public String getHostName() {
		return this.hostName;
	}
	
	public void disconnect() {
		this.client.close();
	}

	// ----------- END OF PUBLIC METHODS -------------

	private void setClusterName( String clusterName ) {
		this.clusterName = clusterName;
	}
	
	private void setPort( int port ) {
		this.port = port;
	}
	
}
