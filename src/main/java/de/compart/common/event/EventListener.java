package de.compart.common.event;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 22:01
 *
 */
public interface EventListener {

	/**
	 * implement this method to listen for incoming events
	 * @param event - a non value implementation of interface {@link Event}
	 */
	void listen( Event event );
}
