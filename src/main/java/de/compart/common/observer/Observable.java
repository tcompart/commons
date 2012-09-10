package de.compart.common.observer;

import java.util.HashSet;
import java.util.Set;

public class Observable<T> {

	private final Set<Observer<T>> observers = new HashSet<Observer<T>>();

	protected void notifyObservers( T obj ) {
		for ( Observer<T> observer : observers ) {
			observer.notify( obj );
		}
	}

	public void addObserver( final Observer<T> observer ) {
		this.observers.add( observer );
	}

}
