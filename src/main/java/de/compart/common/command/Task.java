package de.compart.common.command;

import de.compart.common.observer.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Task extends Observable<Command> {

	private static final Logger LOG = LoggerFactory.getLogger( Task.class );

	private static final AtomicLong TASK_UNIQUE_NUMBER = new AtomicLong( 0 );

	private boolean finished;
	private boolean successful;

	/*
	 * i've choosen long because a very large number of task
	 * can be expected... int could possible break. However,
	 * more bytes are needed, and we speak only about an identifier
	 */
	private final long taskNumber;

	private final Command command;

	public Task( final Command inputCommand ) {
		this.finished = false;
		this.successful = false;
		this.taskNumber = TASK_UNIQUE_NUMBER.incrementAndGet();
		this.command = inputCommand;
		LOG.info( String.format( "Initializing [%s_%d]", Task.class.getSimpleName(), this.taskNumber ) );
	}

	public String getUniqueIdentifier() {
		return String.valueOf( this.taskNumber );
	}

	public void doTask() {

		if ( this.command == null ) {
			LOG.error( String.format( "[%s_%d]: not correctly initialized without an command to be executed.",
											Task.class.getSimpleName(), this.taskNumber ) );
			throw new IllegalStateException();
		}

		try {
			if ( LOG.isInfoEnabled() ) {
				LOG.info( String.format( "[%s_%d]: executing command.", Task.class.getSimpleName(), this.taskNumber ) );
			}
			this.command.execute();
			this.successful = true;
		} catch ( Command.ExecutionException ex ) {
			throw new Command.ExecutionException( String.format( "[%s_%d]: failed command execution.", Task.class.getSimpleName(), this.taskNumber ), ex );
		}

		this.finished = true;

	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) return true;
		if ( o == null || getClass() != o.getClass() ) return false;

		final Task task = ( Task ) o;
		return finished == task.finished && successful == task.successful && taskNumber == task.taskNumber && !( command != null ? !command.equals( task.command ) : task.command != null );
	}

	@Override
	public int hashCode() {
		int result = ( finished ? 1 : 0 );
		result = 31 * result + ( successful ? 1 : 0 );
		result = 31 * result + ( int ) ( taskNumber ^ ( taskNumber >>> 32 ) );
		result = 31 * result + ( command != null ? command.hashCode() : 0 );
		return result;
	}

	public boolean successful() {
		return this.successful;
	}

	public boolean finished() {
		return this.finished;
	}

}