package de.compart.common.test.unit.event;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

import de.compart.common.event.Event;
import de.compart.common.event.EventListener;
import de.compart.common.event.EventManager;
import org.junit.Test;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 21:53
 *
 */
public class EventManagerUnitTest {


	//============================== CLASS VARIABLES ================================//
	//===============================  VARIABLES ====================================//
	//==============================  CONSTRUCTORS ==================================//
	//=============================  PUBLIC METHODS =================================//

	@Test( expected = IllegalArgumentException.class )
	public void assertNPE() {
		new EventManager( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void assertNPEForListener() {
		new EventManager( this ).registerListener( null );
	}

	@Test
	public void observableAddable() {
		assertThat( EventManager.registerObservable( this ), is( true ) );
	}

	@Test
	public void observerAddable() {
		EventManager eventManager = new EventManager( this );
		EventListener listener = new EventListener() {
			@Override
			public void listen( final Event event ) {
			 	// nothing to do here
			}
		};
		eventManager.registerListener( listener );
		assertThat( eventManager.getListener(), hasItems( listener ) );
	}

	@Test
	public void ifNoListenerWasAssignedNotifiyingReturnsFalse() {
		EventManager eventManager = new EventManager(this);
		assertThat(eventManager.notifyListener(null), is(false));
	}

	@Test
	public void observerShouldNotListenToNullEvents() {
		EventManager eventManager = new EventManager(this);
		EventListener listener = new EventListener() {
			@Override
			public void listen( final Event event ) {
				assertThat(event, nullValue());
			}
		};
		eventManager.registerListener(listener);
		assertThat(eventManager.notifyListener( null ), is(true));
	}

	@Test
	public void observerListens() {
		EventManager eventManager = new EventManager( this );
		final Event[] event = new Event[ 1 ];
		EventListener listener = new EventListener() {
			@Override
			public void listen( final Event inputEvent ) {
				event[ 0 ] = inputEvent;
			}
		};
		eventManager.registerListener( listener );

		final Event myEvent = new Event() {
			@Override
			public EventType getType() {
				return EventType.INFO;
			}

			@Override
			public Object getSource() {
				return this;
			}

			@Override
			public String getMessage() {
				return "my Message";
			}
		};

		eventManager.notifyListener( myEvent );

		assertThat( event[ 0 ], is( myEvent ) );
	}

	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

}
