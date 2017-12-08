package ESKit;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESSettings implements Cloneable, Serializable {

	private static final long serialVersionUID = -6527530632830053982L;

	private String _clusterName = "elasticsearch", _hostName = "localhost", _indexName;
	private int _port = 9300;
	private Client _client;
	private TransportClient _transportClient;

	// ----------- END OF FIELDS -------------

	public ESSettings() {}

	public ESSettings( String clusterName ) {
		setClusterName( clusterName );
	}

	public ESSettings( String clusterName, int port ) {
		this( clusterName );
		setPort( port );
	}

	public ESSettings( String clusterName, int port, String hostName ) {
		this( clusterName, port );
		this._hostName = hostName;
	}

	public ESSettings( String clusterName, int port, String hostName, String indexName ) {
		this( clusterName, port, hostName );
		this._indexName = indexName;
	}

	// ----------- END OF CONSTRUCTORS -------------

	public String getClusterName() {
		return _clusterName;
	}

	public ESSettings setClusterName( String clusterName ) {
		this._clusterName = clusterName;
		return this;
	}

	public String getHostName() {
		return _hostName;
	}

	public ESSettings setHostName( String hostName ) {
		this._hostName = hostName;
		return this;
	}

	public String getIndexName() {
		return _indexName;
	}

	public ESSettings setIndexName( String indexName ) {
		this._indexName = indexName;
		return this;
	}

	public int getPort() {
		return _port;
	}

	public ESSettings setPort( int port ) {
		this._port = port;
		return this;
	}

	public Client getClient() {
		return _client;
	}

	public ESSettings setClient( Client client ) {
		this._client = client;
		return this;
	}

	public TransportClient getTransportClient() {
		return _transportClient;
	}

	public ESSettings setTransportClient( TransportClient transportClient ) {
		this._transportClient = transportClient;
		return this;
	}

	@Override public ESSettings clone() {
		return new ESSettings( _clusterName, _port, _hostName, _indexName );
	}

	// ----------- END OF PUBLIC METHODS -------------

	// ----------- END OF PROTECTED METHODS -------------

	/**
	 * 
	 * @return the client once the connection is established.
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unused") private Client connect() throws UnknownHostException {
		_transportClient = new PreBuiltTransportClient( Settings.builder()
				.put( "cluster.name", getClusterName() )
				.put( "client.transport.sniff", false )
				.put( "client.transport.ping_timeout", 20, TimeUnit.SECONDS )
				.build() );

		_transportClient.addTransportAddress( new TransportAddress(
				InetAddress.getByName( getHostName() ), getPort() ) );

		this._client = _transportClient;
		return getClient();
	}

	/**
	 * Disconnects the client from the cluster.
	 */
	@SuppressWarnings("unused") private void disconnect() {
		this._client.close();
	}

}
