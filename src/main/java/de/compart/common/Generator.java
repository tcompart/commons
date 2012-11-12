package de.compart.common;

/**
 * This interface marks a generator.
 * Generator means, that an implementation should generate unique values
 * of a defined generic type.
 *
 * User: torsten
 * Date: 2012/11
 * Time: 22:58
 *
 */
public interface Generator<T> {
	//===============================  VARIABLES ====================================//
	//=============================  PUBLIC METHODS =================================//

	/**
	 * With this method you can generate a new instance of the generic type, which should
	 * be depending on the implementation generate a unique value on every call.
	 *
	 * @return a unique value
	 */
	T generate();

	//=============================  INNER CLASSES ==================================//
}