package de.compart.common;

/**
 * If you want a builder, implement this interface.
 * It should create instances of the defined generic with a given configuration.
 * <p/>
 * The configuration can be given by chaining methods, simple methods or property files.
 * The implementation should give a solution for all the possibilities.
 * <p/>
 *
 * User: torsten
 * Date: 2012/11
 * Time: 23:27
 *
 */
public interface Builder<T> {
	//===============================  VARIABLES ====================================//
	//=============================  PUBLIC METHODS =================================//

	/**
	 * This method creates a completely new instance with the temporary configuration
	 *
	 * @return build an instance
	 */
	T build();

	//=============================  INNER CLASSES ==================================//
}