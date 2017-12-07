package ESKit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESKDatabase {

	private ESKProperties _props;
	
	// ----------- END OF FIELDS -------------

	public ESKDatabase() {
		_props = new ESKProperties();
	}

	public ESKDatabase( ESKProperties properties ) {
		_props = properties;
	}
	
	// ----------- END OF CONSTRUCTORS -------------

	public ESKProperties getProperties() {
		return this._props;
	}

	public void setProperties( ESKProperties connection ) {
		this._props = connection;
	}

	/**
	 * Executes the given query and returns the result as a StringBuffer.
	 * 
	 * @param query to execute.
	 * @return the result. Null if any error.
	 * @throws IOException if error.
	 */
	public Optional<StringBuffer> execute( ESKQuery query ) {
		try {
			URL url = new URL(
					"http://" + _props.getHostName() + ":" + _props.getPort() + "/"
							+ _props.getIndexName()
							+ "/_search?pretty" );
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod( "POST" );
			con.setRequestProperty( "Content-Type", "application/json" );
			con.setDoOutput( true );
			DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
			wr.writeBytes( query.asString() );
			wr.flush();
			wr.close();

			BufferedReader iny = new BufferedReader(
					new InputStreamReader( con.getInputStream() ) );
			String output;
			StringBuffer response = new StringBuffer();

			while (( output = iny.readLine() ) != null) {
				response.append( output );
			}
			iny.close();
			return Optional.of( response );
		} catch (IOException e) {
			return Optional.empty();
		}
	}
	
	// ----------- END OF PUBLIC METHODS -------------

	// ----------- END OF PROTECTED METHODS -------------

	/**
	 * 
	 * @return the client once the connection is established.
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unused")
	private Client connect() throws UnknownHostException {
		_props.setTransportClient( new PreBuiltTransportClient( Settings.builder()
				.put( "cluster.name", _props.getClusterName() )
				.put( "client.transport.sniff", false )
				.put( "client.transport.ping_timeout", 20, TimeUnit.SECONDS )
				.build() ) );

		_props.getTransportClient().addTransportAddress( new TransportAddress(
				InetAddress.getByName( _props.getHostName() ), _props.getPort() ) );

		_props.setClient( _props.getTransportClient() );
		return _props.getClient();
	}

	/**
	 * Disconnects the client from the cluster.
	 */
	@SuppressWarnings("unused")
	private void disconnect() {
		_props.getClient().close();
	}
}
