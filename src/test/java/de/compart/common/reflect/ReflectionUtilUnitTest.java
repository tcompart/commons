package de.compart.common.reflect;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import de.compart.common.Maybe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * User: torsten
 * Date: 2012/10
 * Time: 21:57
 *
 */
public class ReflectionUtilUnitTest {
	//============================== CLASS VARIABLES ================================//
	//=============================== CLASS METHODS =================================//
	//===============================  VARIABLES ====================================//
	//==============================  CONSTRUCTORS ==================================//
	//=============================  PUBLIC METHODS =================================//
	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

	@Test
	public void testGetTypeArguments() throws Exception {
		assertThat(ReflectionUtil.getTypeArguments(Object.class, String.class), is(Collections.<Class<?>>emptyList()));
	}

	@Test
	public void testGetTypeArgumentsWithoutEmptyList() throws ClassNotFoundException {
		final ConcreteUserType user = new ConcreteUserType("This is a string");
		assertThat(ReflectionUtil.getTypeArguments(AbstractUserType.class, user.getClass()).get(0), is(String.class.getClass()));
	}


	@Test
	public void getClassNullValueResultsInNullValue() throws Exception {
	 	assertThat(ReflectionUtil.getClass(null), nullValue());
	}

	@Test
	public void getClassClassValueResultsClassValue() {
		assertThat(ReflectionUtil.getClass( AbstractUserType.class ) , is(AbstractUserType.class.getClass()));
	}

	@Test
	public void getClassParametrizedTypeValueResultsClassValue() {
		final ConcreteUserType user = new ConcreteUserType("some string");
		assertThat( ReflectionUtil.getClass( user.getClass() ), is(String.class.getClass()));
	}

	static class ConcreteUserType extends AbstractUserType<String> {

		public ConcreteUserType(final String t) {
			this.t = t;
		}

		@Override
		public String get() {
			return this.t;
		}
	}

	abstract static class AbstractUserType<T> implements UserType<T> {

		protected T t;

		public void set(T t) {
			this.t = t;
		}

		abstract public T get();

	}

	interface UserType<T> {

		void set(T t);
		T get();
	}

}