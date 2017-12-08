package ESKit;

public class ESQuery {
	
	private String _predicate;
	
	// ----------- END OF FIELDS -------------
	
	public ESQuery(String query) {
		this._predicate = query;
	}
	
	// ----------- END OF CONSTRUCTORS -------------
	
	/**
	 * Sets the parameters of the query.
	 * 
	 * @param param to set.
	 */
	public ESQuery setParam(Object param) {
		StringBuilder sb = new StringBuilder(_predicate);
		int index = _predicate.indexOf( '?' );
		if(index == -1) {
			throw new IllegalArgumentException( "There's no empty parameter to set in the query: " +  _predicate);
		}
		sb.deleteCharAt( index );
		sb.insert( index, param.toString() );
		_predicate = sb.toString();
		return this;
	}
	
	public String asString() {
		return this._predicate;
	}
}
