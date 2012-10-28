package de.compart.common.reflect;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * User: torsten
 * Date: 2012/10
 * Time: 13:35
 *
 */
public class ReflectionUtil {


	//============================== CLASS VARIABLES ================================//
	//=============================== CLASS METHODS =================================//

	/**
	 * Get the actual type arguments a child class has used to extend a generic base class.
	 *
	 * @param baseClass the base class
	 * @param childClass the child class
	 * @return a list of the raw classes for the actual type arguments.
	 */
	public static <T> List<Class<?>> getTypeArguments( Class<T> baseClass, Class<? extends T> childClass ) {
		Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		Type type = childClass;
		// start walking up the inheritance hierarchy until we hit baseClass
		while ( getClass( type ) != null && !getClass( type ).equals( baseClass ) ) {
			if ( type instanceof Class ) {
				// there is no useful information for us in raw types, so just keep going.
				type = ( ( Class ) type ).getGenericSuperclass();
			} else if ( type != null ) {
				ParameterizedType parameterizedType = ( ParameterizedType ) type;
				Class<?> rawType = ( Class ) parameterizedType.getRawType();

				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for ( int i = 0; i < actualTypeArguments.length; i++ ) {
					resolvedTypes.put( typeParameters[ i ], actualTypeArguments[ i ] );
				}

				if ( !rawType.equals( baseClass ) ) {
					type = rawType.getGenericSuperclass();
				}
			} else {
				break;
			}
		}

		// finally, for each actual type argument provided to baseClass, determine (if possible)
		// the raw class for that type argument.
		final Type[] actualTypeArguments;
		if ( type instanceof Class ) {
			actualTypeArguments = ( ( Class ) type ).getTypeParameters();
		} else if ( type != null ) {
			actualTypeArguments = ( ( ParameterizedType ) type ).getActualTypeArguments();
		} else {
			actualTypeArguments = new Type[ 0 ];
		}
		List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
		// resolve types by chasing down type variables.
		for ( Type baseType : actualTypeArguments ) {
			while ( resolvedTypes.containsKey( baseType ) ) {
				baseType = resolvedTypes.get( baseType );
			}
			typeArgumentsAsClasses.add( getClass( baseType ) );
		}
		return typeArgumentsAsClasses;
	}


	/**
	 * Get the underlying class for a type, or null if the type is a variable type.
	 * @param type the type
	 * @return the underlying class
	 */
	public static Class<?> getClass( Type type ) {
		if ( type instanceof Class ) {
			return ( Class ) type;
		} else if ( type instanceof ParameterizedType ) {
			return getClass( ( ( ParameterizedType ) type ).getRawType() );
		} else if ( type instanceof GenericArrayType ) {
			Type componentType = ( ( GenericArrayType ) type ).getGenericComponentType();
			Class<?> componentClass = getClass( componentType );
			if ( componentClass != null ) {
				return Array.newInstance( componentClass, 0 ).getClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}


	//===============================  VARIABLES ====================================//
	//==============================  CONSTRUCTORS ==================================//
	protected ReflectionUtil() {
		throw new IllegalStateException( "This utility class should not be instantiated, or you have a good reason for it." );
	}
	//=============================  PUBLIC METHODS =================================//
	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

}
