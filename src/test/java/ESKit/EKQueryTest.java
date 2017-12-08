package ESKit;

import static org.junit.Assert.*;

import org.junit.Test;

public class EKQueryTest {

	@Test
	public void test() {
		ESKQuery statement = new ESKQuery( "select ? from ? where ? = cod.coche and ? = ?;" );
		System.out.println( statement.asString() );
		statement.setParam( "*" )
			.setParam( "T.Empleados" )
			.setParam( "cod.coche" )
			.setParam( "nom.empleado" )
			.setParam( "'carlos'" );
		System.out.println( statement.asString() );
		
		assertEquals( "select * from T.Empleados where cod.coche = cod.coche and nom.empleado = 'carlos';", statement.asString() );
	}

}
