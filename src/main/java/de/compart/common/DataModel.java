package de.compart.common;

/**
 *
 * User: torsten
 * Date: 2012/08
 * Time: 22:40
 *
 */
public interface DataModel {

	Object get( final Object inputKey );

	<T> Maybe<T> get( final Object inputKey, Class<T> inputValueClazz );

	void set( final Object inputKey, final Object inputValue );


}
