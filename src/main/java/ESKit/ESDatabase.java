package ESKit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
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

/**
 * 
 * ESDatabase.java
 *
 * @author willy
 * @version
 * @since 2017"1"492017
 * @formatter Oviedo Computing Community
 */
public class ESDatabase implements Serializable {

	private static final long serialVersionUID = -5105605239381465569L;

	private ESSettings _settings;

	// ----------- END OF FIELDS -------------

	public ESDatabase() {
		_settings = new ESSettings();
	}

	public ESDatabase( ESSettings properties ) {
		_settings = properties;
	}

	// ----------- END OF CONSTRUCTORS -------------

	public ESSettings getProperties() {
		return this._settings.clone();
	}

	public void setProperties( ESSettings connection ) {
		this._settings = connection;
	}

	/**
	 * Executes the given query and returns the result as a StringBuffer.
	 * 
	 * @param query to execute.
	 * @return the result. Null if any error.
	 * @throws IOException if error.
	 */
	public Optional<StringBuffer> execute( ESQuery query ) {
		try {
			URL url = new URL(
					"http://" + _settings.getHostName() + ":" + _settings.getPort() + "/"
							+ _settings.getIndexName()
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
	@SuppressWarnings("unused") private Client connect() throws UnknownHostException {
		_settings.setTransportClient( new PreBuiltTransportClient( Settings.builder()
				.put( "cluster.name", _settings.getClusterName() )
				.put( "client.transport.sniff", false )
				.put( "client.transport.ping_timeout", 20, TimeUnit.SECONDS )
				.build() ) );

		_settings.getTransportClient().addTransportAddress( new TransportAddress(
				InetAddress.getByName( _settings.getHostName() ), _settings.getPort() ) );

		_settings.setClient( _settings.getTransportClient() );
		return _settings.getClient();
	}

	/**
	 * Disconnects the client from the cluster.
	 */
	@SuppressWarnings("unused") private void disconnect() {
		_settings.getClient().close();
	}
}
