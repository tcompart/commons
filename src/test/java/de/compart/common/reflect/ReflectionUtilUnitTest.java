package de.compart.common.reflect;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

	@Test( expected = IllegalStateException.class )
	public void constructorShouldNotBeInstantiable() {
		new ReflectionUtil() {
			// constructor is protected
		};
	}

	@Test
	public void getTypeArgumentsWithNullValuesExpectsEmptyResult() {
		assertThat( ReflectionUtil.getTypeArguments( null, null ) ).isEmpty();
	}

	@Test
	public void getTypeArgumentsWithNotParametrizedClassesExpectsEmptyResult() {
		assertThat( ReflectionUtil.getTypeArguments( Object.class, String.class ) ).isEmpty();
	}

	@Test
	public void getTypeArgumentsWithParametrizedClassExpectsStringClassResult() throws ClassNotFoundException {
		assertThat( ReflectionUtil.getTypeArguments( AbstractUserType.class, ConcreteUserType.class ) ).hasSize( 1 ).containsExactly( String.class );
	}

	@Test
	public void getTypeArgumentsWithGenericArrayTypeImplClassExpectedStringClassResult() {
		final GenericArrayType genericArrayType = GenericArrayTypeImpl.make( ArrayList.class );
		assertThat( ReflectionUtil.getTypeArguments( ( Class<GenericArrayType> ) genericArrayType.getClass(), genericArrayType.getClass() ) ).isEmpty();
	}

	@Test
	public void getClassNullValueResultsInNullValue() throws Exception {
		assertThat( ReflectionUtil.getClass( null ) ).isNull();
	}

	@Test
	public void getClassWithClassValueResultsInTheSameClassValue() {
		assertThat( ReflectionUtil.getClass( Object.class ) ).isEqualTo( Object.class );
	}

	@Test
	public void getClassWithParametrizedClassValueResultsInSameParametrizedClassValue() {
		assertThat( ReflectionUtil.getClass( AbstractUserType.class ) ).isEqualTo( AbstractUserType.class );
	}

	@Test
	public void getClassWithGenericArrayWithComponentTypeClassValueResultsInSameGenericClassValue() {
		assertThat( ReflectionUtil.getClass( GenericArrayTypeImpl.make( StringArrayList.class ) ) ).isEqualTo( StringArrayList[].class );
	}

	@Test
	public void getClassWithGenericArrayClassWithoutComponentTypeExpectsNullValue() {
		assertThat( ReflectionUtil.getClass( GenericArrayTypeImpl.make( null ) ) ).isNull();
	}


	@Test
	public void getClassParametrizedTypeValueResultsClassValue() {
		ParameterizedType type = ParameterizedTypeImpl.make(String.class, new Type[]{}, Object.class);
		assertThat( ReflectionUtil.getClass( type ) ).isEqualTo( String.class );
	}

	static class ConcreteUserType extends AbstractUserType<String> {

		public ConcreteUserType( final String t ) {
			this.t = t;
		}

		@Override
		public String get() {
			return this.t;
		}
	}

	static abstract class AbstractUserType<T> implements UserType<T> {

		protected T t;

		public void set( T t ) {
			this.t = t;
		}

		abstract public T get();

	}

	// this is a test interface, therefore methods may be used or not, but the test case has to remain
	@SuppressWarnings( "UnusedDeclaration" )
	interface UserType<T> {

		void set( T t );

		T get();
	}

	static class StringArrayList extends ArrayList<String> {
	}

}