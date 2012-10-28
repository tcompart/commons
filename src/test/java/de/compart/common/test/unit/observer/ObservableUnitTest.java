package de.compart.common.test.unit.observer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import de.compart.common.observer.Observable;
import de.compart.common.observer.Observer;
import org.junit.Test;

/**
 *
 * User: torsten
 * Date: 2012/10
 * Time: 22:21
 *
 */
public class ObservableUnitTest {
	//============================== CLASS VARIABLES ================================//
	//=============================== CLASS METHODS =================================//
	//===============================  VARIABLES ====================================//
	//==============================  CONSTRUCTORS ==================================//
	//=============================  PUBLIC METHODS =================================//

	@Test
	public void create() {
		assertThat( new Observable(), notNullValue());
	}

	@Test
	public void addObserver() {
		final MockObservable<String> observable = new MockObservable<String>();
		final MockObserver<String> observer = new MockObserver<String>();
		final String message = "My Secret Message for all observers....";

		observable.addObserver(observer);
		observable.notifiyListener(message);
		assertThat(observer.getNotification(), is(message));
	}

	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//

	static class MockObserver<T> implements Observer<T> {

		private T string;

		@Override
		public void notify( final T obj ) {
			this.string = obj;
		}

		T getNotification() {
			return this.string;
		}
	}

	static class MockObservable<T> extends Observable<T> {

		public void notifiyListener(final T t) {
			this.notifyObservers(t);
		}

	}

}