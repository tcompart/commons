package de.compart.common.event;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 20:46
 *
 */
public class TimeEvent implements Event<Long> {
	//============================== CLASS VARIABLES ================================//
	//===============================  VARIABLES ====================================//
	private final Object source;
	private final long startTime;

	//==============================  CONSTRUCTORS ==================================//
	public TimeEvent( final Object inputSource ) {
		this( inputSource, System.nanoTime() );
	}

	public TimeEvent( final Object inputSource, final long startTime ) {
		this.source = inputSource;
		this.startTime = startTime;
	}

	//=============================  PUBLIC METHODS =================================//
	@Override
	public EventType getType() {
		return EventType.TIME;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public Long getMessage() {
		return System.nanoTime() - startTime;
	}

	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//
}
