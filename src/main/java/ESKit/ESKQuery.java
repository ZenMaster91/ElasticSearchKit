package ESKit;

public class ESKQuery {
	
	private String query;
	
	public ESKQuery(String query) {
		this.query = query;
	}
	
	/**
	 * Sets the parameters of the query.
	 * 
	 * @param param to set.
	 */
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
