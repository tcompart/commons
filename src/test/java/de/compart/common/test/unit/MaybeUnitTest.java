package de.compart.common.test.unit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import de.compart.common.Maybe;
import org.junit.Test;

/**
 *
 * User: torsten
 * Date: 2012/08
 * Time: 23:07
 *
 */
public class MaybeUnitTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( MaybeUnitTest.class );

	public MaybeUnitTest() {
		log.debug( "Initialized default instance: %s", MaybeUnitTest.class.getSimpleName() );
	}

	@Test
	public void create() {
		assertThat( Maybe.just( null ), is( Maybe.nothing() ) );
		assertThat( Maybe.just( null ).isJust(), is( false ) );
		assertThat( Maybe.just( new Object() ).isJust(), is( true ) );
		assertThat( Maybe.just( null ).isNothing(), is( true ) );
		assertThat( Maybe.just( new Object() ).isNothing(), is( false ) );
	}

}
