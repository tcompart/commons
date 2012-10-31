package de.compart.common.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Experimental annotation marking a field as
 * a source of data providing the value class
 */
@Target( ElementType.FIELD)
@Retention( RetentionPolicy.RUNTIME)
public @interface DataProvider {

	Class<?> clazz() default Object.class;

}
