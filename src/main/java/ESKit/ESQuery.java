package ESKit;

import java.io.Serializable;

public class ESQuery implements Serializable {

	private static final long serialVersionUID = 654093114530194459L;

	private String _predicate;

	// ----------- END OF FIELDS -------------

	public ESQuery( final String query ) {
		this._predicate = query;
	}

	// ----------- END OF CONSTRUCTORS -------------

	/**
	 * Sets the parameters of the query.
	 * 
	 * @param param to set.
	 */
	protected ESQuery setParam( Object param ) {
		StringBuilder sb = new StringBuilder( _predicate );
		int index = _predicate.indexOf( '?' );
		if (index == -1) {
			throw new IllegalArgumentException(
					"There's no empty parameter to set in the query: " + _predicate );
		}
		sb.deleteCharAt( index );
		sb.insert( index, param.toString() );
		_predicate = sb.toString();
		return this;
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	public ESQuery setPrameters( Object... params ) {
		for (Object o : params) {
			setParam( o );
		}
		return this;
	}

	public String asString() {
		return this._predicate;
	}
}
