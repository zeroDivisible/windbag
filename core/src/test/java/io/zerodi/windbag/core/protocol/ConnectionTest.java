package io.zerodi.windbag.core.protocol;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ConnectionTest {

	@BeforeMethod
	public void setUp() throws Exception {

	}

	@Test
	public void itShouldHaveDifferentIdForEachNewConnection() {
		// given
		Connection connection1 = ConnectionImpl.getInstance(null);
		Connection connection2 = ConnectionImpl.getInstance(null);

		// when
		long id1 = connection1.getId();
		long id2 = connection2.getId();

		// then
		assertThat(id1).isNotEqualTo(id2);
	}
}
