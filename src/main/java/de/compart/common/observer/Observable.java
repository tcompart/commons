package de.compart.common.observer;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class Observable<T> {

	private final Set<Observer<T>> observers = Collections.newSetFromMap(new WeakHashMap<Observer<T>, Boolean>());

	protected void notifyObservers( T obj ) {
		for ( Observer<T> observer : observers ) {
			observer.notify( obj );
		}
	}

	public void addObserver( final Observer<T> observer ) {
		this.observers.add( observer );
	}

}
