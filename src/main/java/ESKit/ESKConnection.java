package ESKit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESKConnection {
	
	private String clusterName = "elasticsearch", hostName = "localhost";
	private int port = 9300;
	private Client client;
	private TransportClient transportClient;
	
	public ESKConnection() {}
	
	public ESKConnection(String clusterName) {
		setClusterName(clusterName);
	}

	public ESKConnection(String clusterName, int port) {
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
	 * @return the client once the connection is established.
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
	
	/**
	 * @return the host name.
	 */
	public String getHostName() {
		return this.hostName;
	}
	
	/**
	 * Disconnects the client from the cluster.
	 */
	public void disconnect() {
		this.client.close();
	}

	// ----------- END OF PUBLIC METHODS -------------

	/**
	 * Changes the cluster name.
	 * 
	 * @param clusterName to set.
	 */
	private void setClusterName( String clusterName ) {
		this.clusterName = clusterName;
	}
	
	/**
	 * Changes the port number.
	 * 
	 * @param port to set.
	 */
	private void setPort( int port ) {
		this.port = port;
	}
	
}
