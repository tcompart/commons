package de.compart.common.event;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
		OBSERVABLE_MAP.put( observable, new LinkedHashSet<EventListener>() );
		return OBSERVABLE_MAP.containsKey( observable );
	}

	//============================== CLASS VARIABLES ================================//
	private static final Map<Object, Set<EventListener>> OBSERVABLE_MAP = new HashMap<Object, Set<EventListener>>();
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger( EventManager.class );

	//===============================  VARIABLES ====================================//
	private final Object target;

	//==============================  CONSTRUCTORS ==================================//
	public EventManager( final Object target ) {
		if ( target == null ) {
			// fix regarding the rule of sonar: avoiding NPEs
			throw new IllegalArgumentException( "The event manager event instance was initialized with a null value assigned." );
		}
		registerObservable( target );
		this.target = target;
	}

	//=============================  PUBLIC METHODS =================================//
	public boolean registerListener( final EventListener listener ) {
		if ( listener == null ) {
			throw new IllegalArgumentException( "The listener is not initalized, and therefore not usable for this context." );
		}
		LOG.info( "Adding listener '{}' to object '{}'.", listener, target );
		return OBSERVABLE_MAP.get( target ).add( listener );
	}

	public boolean notifyListener( final Event event ) {
		if ( event != null ) {
			LOG.info( "Notifying listener list about event '{}' from object '{}'", event, target );
		} else {
			LOG.warn( "Listener will receive a null value event from object '{}'", target );
		}
		for ( EventListener listener : OBSERVABLE_MAP.get( target ) ) {
			listener.listen( event );
		}
		return !OBSERVABLE_MAP.get( target ).isEmpty();
	}

	public Set<EventListener> getListener() {
		return OBSERVABLE_MAP.get( target );
	}

	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

}
