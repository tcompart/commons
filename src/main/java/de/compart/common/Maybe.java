package de.compart.common;

import org.slf4j.Logger;

/**
 *
 * User: torsten
 * Date: 2012/08
 * Time: 22:39
 *
 */
public class Maybe<T> {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger( Maybe.class );
	private final T obj;

	protected Maybe( final T inputObj ) {
		LOG.debug( "Initialized default instance: {} (Object: {})", Maybe.class.getSimpleName(), ( inputObj != null ? inputObj.toString() : "not initialized" ) );
		this.obj = inputObj;
	}

	public static <T> Maybe<T> just( T object ) {
		return new Maybe<T>( object );
	}

	public static <T> Maybe<T> nothing() {
		return new Maybe<T>( null );
	}

	public boolean isJust() {
		return this.obj != null;
	}

	public boolean isNothing() {
		return this.obj == null;
	}

	public T get() {
		return this.obj;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		} else if ( o == null || getClass() != o.getClass() ) {
			return false;
		} else {
			final Maybe maybe = ( Maybe ) o;
			return !( obj != null ? !obj.equals( maybe.obj ) : maybe.obj != null );
		}
	}

	@Override
	public int hashCode() {
		return obj != null ? obj.hashCode() : 0;
	}
}
