package de.compart.common.command;

import de.compart.common.observer.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Task extends Observable<Command> {

	private static final Logger LOG = LoggerFactory.getLogger( Task.class );

	private static final int PRIME = 31;
	private static final int INT_SHIFT_BIT_LENGTH = 32;

	private static final AtomicLong TASK_UNIQUE_NUMBER = new AtomicLong( 0 );

	private boolean finished;

	private boolean successful;

	/*
	 * i've choosen long because a very large number of task
	 * can be expected... int could possible break. However,
	 * more bytes are needed, and we speak only about an identifier
	 */
	private final long taskNumber;
	private Command command;

	public Task( final Command inputCommand ) {
		this.finished = false;
		this.successful = false;
		this.taskNumber = TASK_UNIQUE_NUMBER.incrementAndGet();
		this.command = inputCommand;
		LOG.info( String.format( "Initializing [%s_%d]", Task.class.getSimpleName(), this.taskNumber ) );
	}

	protected Task( final Task inputTask ) {
		this.finished = inputTask.finished;
		this.successful = inputTask.successful;
		this.taskNumber = inputTask.taskNumber;
		this.command = inputTask.command;
		LOG.info( String.format( "Initializing a copy of [%s_%d]", Task.class.getSimpleName(), this.taskNumber ) );
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
			LOG.info( String.format( "[%s_%d]: executing command.", Task.class.getSimpleName(), this.taskNumber ) );
			this.command.execute();
			this.successful = true;
		} catch ( final RuntimeException ex ) {
			throw new Command.ExecutionException( String.format( "[%s_%d]: failed command execution.", Task.class.getSimpleName(), this.taskNumber ), ex );
		} finally {
			this.finished = true;
		}

	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		} else if ( o == null ) {
			return false;
		} else if ( !( o instanceof Task ) ) {
			return false;
		} else {
			final Task task = ( Task ) o;
			return finished == task.finished && successful == task.successful && ( command == null ? task.command == null : command.equals( task.command ) ) && taskNumber == task.taskNumber;
		}
	}

	@Override
	public int hashCode() {
		int result = ( finished ? 1 : 0 );
		result = PRIME * result + ( successful ? 1 : 0 );
		result = PRIME * result + ( int ) ( taskNumber ^ ( taskNumber >>> INT_SHIFT_BIT_LENGTH ) );
		result = PRIME * result + ( command != null ? command.hashCode() : 0 );
		return result;
	}

	public boolean successful() {
		return this.successful;
	}

	public boolean finished() {
		return this.finished;
	}

	protected void setCommand( final Command inputCommand ) {
		this.command = inputCommand;
	}

}