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
		// get a first glimpse of a possible type argument
		final Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		final Type type = getTypeArgument( baseClass, childClass, resolvedTypes );

		List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
		// resolve types by chasing down type variables.
		for ( Type baseType : Types.getType( type ).getActualTypes( type ) ) {
			while ( resolvedTypes.containsKey( baseType ) ) {
				baseType = resolvedTypes.get( baseType );
			}
			typeArgumentsAsClasses.add( getClass( baseType ) );
		}
		return typeArgumentsAsClasses;
	}

	/**
	 *
	 * @param baseClass
	 * @param type
	 * @param resolvedTypes
	 * @return
	 */
	public static Type getTypeArgument( final Class<?> baseClass, final Type type, final Map<Type, Type> resolvedTypes ) {
		switch ( Types.getType( type ) ) {
			case CLASS:
				// start walking up the inheritance hierarchy until we hit baseClass
				if ( !type.equals( baseClass ) ) {
					return getTypeArgument( baseClass, ( ( Class ) type ).getGenericSuperclass(), resolvedTypes );
				}
				break;
			case PARAMETRIZED:
				ParameterizedType parameterizedType = ( ParameterizedType ) type;
				Class<?> rawType = ( Class ) parameterizedType.getRawType();

				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for ( int i = 0; i < actualTypeArguments.length; i++ ) {
					resolvedTypes.put( typeParameters[ i ], actualTypeArguments[ i ] );
				}

				if ( !rawType.equals( baseClass ) ) {
					// if you find a raw type, return this
					return rawType.getGenericSuperclass();
				}
			case GENERIC_ARRAY:
			case OTHER:
				break;
		}
		// otherwise return the assigned type
		return type;
	}


	public static Class<?> getClass( Type type ) {
		return Types.getType( type ).getClass( type );
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

	/**
	 * This enum helps in identifying the currently resolvable types, like
	 * <ul>
	 *  <li>{@link #CLASS}</li>
	 * 	<li>{@link #PARAMETRIZED}</li>
	 * 	<li>{@link #GENERIC_ARRAY}</li>
	 * 	<li>{@link #OTHER}</li>
	 * </ul>
	 */
	private enum Types {
		/**
		 * represents a class type
		 */
		CLASS {
			@Override
			public Class<?> getClass( final Type type ) {
				return ( Class ) type;
			}

			@Override
			public Type[] getActualTypes( final Type type ) {
				return ( ( Class ) type ).getTypeParameters();
			}
		},
		/**
		 * represents a class which is parametrized
		 */
		PARAMETRIZED {
			@Override
			public Class<?> getClass( final Type type ) {
				return CLASS.getClass( ( ( ParameterizedType ) type ).getRawType() );
			}

			@Override
			public Type[] getActualTypes( final Type type ) {
				return ( ( ParameterizedType ) type ).getActualTypeArguments();
			}
		},
		/**
		 * represents an array of generics
		 */
		GENERIC_ARRAY {
			@Override
			public Class<?> getClass( final Type type ) {
				final Type componentType = ( ( GenericArrayType ) type ).getGenericComponentType();
				final Class<?> componentClass = CLASS.getClass( componentType );
				if ( componentClass != null ) {
					return Array.newInstance( componentClass, 0 ).getClass();
				} else {
					return OTHER.getClass( type );
				}
			}

			@Override
			public Type[] getActualTypes( final Type type ) {
				return new Type[ 0 ];
			}
		},
		/**
		 * everything other than the previous defined types
		 */
		OTHER {
			@Override
			public Class<?> getClass( final Type type ) {
				return null;
			}

			@Override
			public Type[] getActualTypes( final Type type ) {
				return new Type[ 0 ];
			}
		};

		/**
		 * Get the underlying class for a type, or null if the type is a variable type.
		 * @param type the type
		 * @return the underlying class
		 */
		abstract Class<?> getClass( final Type type );

		/**
		 * finally, for each actual type argument provided to baseClass, determine (if possible)
		 * the raw class for that type argument.
		 * @param type - the type, which is probably just a glimpse on the real type, which will be saved in an array
		 * @return the actual type arguments an array of {@link Type Types}
		 */
		abstract Type[] getActualTypes( final Type type );

		public static Types getType( final Type type ) {
			if ( type instanceof GenericArrayType ) {
				return GENERIC_ARRAY;
			} else if ( type instanceof ParameterizedType ) {
				return PARAMETRIZED;
			} else if ( type instanceof Class ) {
				return CLASS;
			} else {
				return OTHER;
			}
		}
	}

}