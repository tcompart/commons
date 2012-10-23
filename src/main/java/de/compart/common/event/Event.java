package de.compart.common.event;

/**
 *
 * User: torsten
 * Date: 2012/09
 * Time: 20:46
 *
 */
public interface Event {

	public enum EventType {
		/**
		 * an information
		 */
		INFO,
		/**
		 * an information, which should be handled like a message call (indirect
		 * call)
		 */
		CALL,
		/**
		 * an error occurred, which should be distributed to all listeners
		 */
		ERROR,
		/**
		 * this message informs about a time event.
		 */
		TIME,
		/**
		 * a message, which signals the last message or event, and should turn
		 * off every listener
		 */
		EXIT
	}

	public EventType getType();

	public Object getSource();

	public String getMessage();

}
