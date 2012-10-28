package de.compart.common.test.unit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import de.compart.common.Maybe;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
	public void testNullValueIsExpectedNothing() {
		assertThat( Maybe.just( null ), is( Maybe.nothing() ) );
	}

	@Test
	public void testNullValueIsExpectedIsJustFalse() {
		assertThat( Maybe.just( null ).isJust(), is( false ) );
	}

	@Test
	public void testNonNullValueIsExpectedIsJustTrue() {
		assertThat( Maybe.just( new Object() ).isJust(), is( true ) );
	}

	@Test
	public void testNullValueIsExpectedIsNothingTrue() {
		assertThat( Maybe.just( null ).isNothing(), is( true ) );
	}

	@Test
	public void testNonNullValueIsExpectedIsNothingFalse() {
		assertThat( Maybe.just( new Object() ).isNothing(), is( false ) );
	}

	@Test
	public void testAssignedNonNullValueIsNonNullValue() {
		final Object object = new Object();
		assertThat( Maybe.just( object ).get(), is( object ) );
	}

	@Test
	public void testAssignedNullValueIsNullValue() {
		final Object object = null;
		assertThat( Maybe.just( object ).get(), nullValue() );
	}


	// ----------------------- equals // hashcode ------------------------------------ //

	@Test
	public void twoMaybeAreEqualsBecauseOfSameObject() {
		final Object obj = new Object();
		assertThat( Maybe.just( obj ).equals( Maybe.just( obj ) ), is( true ) );
	}

	@Test
	public void twoMaybesWithDifferentValuesAreNotEquals() {
		final Maybe<Object> maybe1 = Maybe.just( new Object() );
		final Maybe<Object> maybe2 = Maybe.just( new Object() );
		assertThat( maybe1.equals( maybe2 ), is( false ) );
	}

	@Test
	public void maybeWithValueIsNotEqualsToMaybeWithNullValue() {
		final Maybe<Object> maybe1 = Maybe.just( new Object() );
		final Maybe<Object> maybe2 = Maybe.just( null );
		assertThat( maybe1.equals( maybe2 ), is( false ) );
	}

	@Test
	public void maybeWithValueIsNotEqualsToOtherValue() {
		final Maybe<Object> maybe = Maybe.just( new Object() );
		assertThat( maybe.equals( "some value" ), is( false ) );
	}


	@SuppressWarnings( "ObjectEqualsNull" )
	@Test
	public void maybeWithValueIsNotEqualsToNullValue() {
		assertThat( Maybe.just( new Object() ).equals( null ), is( false ) );
	}

	@Test
	public void maybeWithValueIsEqualsToTheSameMaybeWithValue() {
		final Maybe<Object> maybe = Maybe.just( new Object() );
		assertThat( maybe.equals( maybe ), is( true ) );
	}

	@Test
	public void maybeWithValueIsEqualsToTheSameValue() {
		final Object obj1 = new Object();
		assertThat( Maybe.just( obj1 ).equals( obj1 ), is( true ) );
	}

	@Test
	public void maybeWithNullValueIsEqualsToNullValue() {
		assertThat( Maybe.just( null ).equals( Maybe.just( null ) ), is( true ) );
	}

	@Test
	public void maybeWithNullValueIsNotEqualsToNonNullValue() {
		assertThat( Maybe.just( null ).equals( new Object() ), is( false ) );
	}

	@Test
	public void assertHashCodeToBeZeroWithNullValue() {
		assertThat( Maybe.just( null ).hashCode(), is( 0 ) );
	}

	@Test
	public void assertHashCodeToBeOfObjects() {
		final Object obj = new Object();
		final int hashCodeObject = obj.hashCode();
		assertThat( Maybe.just( obj ).hashCode(), is( hashCodeObject ) );
	}

	@Test
	public void sameMaybesAreJustOnceInASet() {
		final Set<Maybe<Object>> set = new HashSet<Maybe<Object>>();
		final Object obj = new Object();

		set.add( Maybe.just( obj ) );
		set.add( Maybe.just( obj ) );

		assertThat( set.size(), is( 1 ) );
	}

}
