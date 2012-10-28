package de.compart.common.test.unit.event;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import de.compart.common.event.Event;
import de.compart.common.event.TimeEvent;
import org.junit.Test;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 22:17
 *
 */
public class TimeEventUnitTest {


	//============================== CLASS VARIABLES ================================//
	//===============================  VARIABLES ====================================//
	//==============================  CONSTRUCTORS ==================================//
	//=============================  PUBLIC METHODS =================================//

	@Test
	public void correctEventType() {
		final Event event = new TimeEvent( this );
		assertThat( event.getType(), is( Event.EventType.TIME ) );
	}

	@Test
	public void nonNullEventMessage() {
		final Event event = new TimeEvent( this );
		assertThat( event.getMessage(), notNullValue() );
	}

	@Test
	public void correctEventSource() {
		final Event event = new TimeEvent( this );
		assertThat( event.getSource(), is( ( Object ) this ) );
	}


	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

}
