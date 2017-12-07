package ESKit;

public class EKQuery {
	
	private String query;
	
	public EKQuery(String query) {
		this.query = query;
	}
	
	public void setParam(Object param) {
		StringBuilder sb = new StringBuilder(query);
		int index = query.indexOf( '?' );
		sb.deleteCharAt( index );
		sb.insert( index, param.toString() );
		query = sb.toString();
	}
	
	public String getQuery() {
		return this.query;
	}

}
