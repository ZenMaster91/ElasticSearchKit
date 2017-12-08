package ESKit;

import static org.junit.Assert.*;

import org.junit.Test;

public class EKQueryTest {

	private ESQuery statement;

	@Test public void setParamTest() {
		statement = new ESQuery( "select ? from ? where ? = cod.coche and ? = ?;" );
		statement.setParam( "*" )
				.setParam( "T.Empleados" )
				.setParam( "cod.coche" )
				.setParam( "nom.empleado" )
				.setParam( "'carlos'" );

		assertEquals(
				"select * from T.Empleados where cod.coche = cod.coche and nom.empleado = 'carlos';",
				statement.asString() );
	}

	@Test public void setParametersTest() {
		statement = new ESQuery( "select ? from ? where ? = cod.coche and ? = ?;" );
		statement.setPrameters( "*", "T.Empleados", "cod.coche", "nom.empleado", "'carlos'" );

		assertEquals(
				"select * from T.Empleados where cod.coche = cod.coche and nom.empleado = 'carlos';",
				statement.asString() );
	}

	@Test public void immutabilityTest() {

		// Establishing a property for the query.
		String query = "is pablo == pablo?";

		// Assigning the property as the value of the ESQuery.
		statement = new ESQuery( query );

		// Checking that the value in the ESQuery is correct.
		assertEquals( "is pablo == pablo?", statement.asString() );

		// Changing the value of the query string.
		query = "is pablo == carlos?";

		// Checking that the value of the ESQuery did not change. And therefore
		// the query is immutable.
		assertEquals( "is pablo == pablo?", statement.asString() );
	}

}
