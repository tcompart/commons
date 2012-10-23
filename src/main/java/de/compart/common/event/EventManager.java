package de.compart.common.event;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 22:09
 *
 */
public class EventManager {
	//=============================== CLASS METHODS =================================//
	public static boolean registerObservable( final Object observable ) {
		observableMap.put( observable, new LinkedHashSet<EventListener>() );
		return observableMap.containsKey( observable );
	}

	//============================== CLASS VARIABLES ================================//
	private static final HashMap<Object, Set<EventListener>> observableMap = new HashMap<Object, Set<EventListener>>();
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( EventManager.class );

	//===============================  VARIABLES ====================================//
	private final Object target;

	//==============================  CONSTRUCTORS ==================================//
	public EventManager( final Object target ) {
		if ( target == null ) {
			throw new NullPointerException();
		}
		registerObservable( target );
		this.target = target;
	}

	//=============================  PUBLIC METHODS =================================//
	public boolean registerListener( final EventListener listener ) {
		if ( listener == null ) {
			throw new NullPointerException();
		}
		log.info( "Adding listener '{}' to object '{}'.", listener, target );
		return observableMap.get( target ).add( listener );
	}

	public boolean notifyListener( final Event event ) {
		log.info( "Notifying listener list about event '{}' of object '{}'", event, target );
		for ( EventListener listener : observableMap.get( target ) ) {
			listener.listen( event );
		}
		return !observableMap.get( target ).isEmpty();
	}

	public Set<EventListener> getListener() {
		return observableMap.get( target );
	}

	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

}
