package ESKit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EKDatabase {

	private EKConnection con;
	private String indexName;

	public EKDatabase() {
		con = new EKConnection();
	}

	public StringBuffer executeQuery( EKQuery query ) throws IOException {
		URL url = new URL( "http://" + con.getHostName() + ":" + con.getPort() + "/" + indexName
				+ "/_search?pretty" );
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod( "POST" );
		con.setRequestProperty( "Content-Type", "application/json" );
		con.setDoOutput( true );
		DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
		wr.writeBytes( query.getQuery() );
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

		return response;
	}

}
