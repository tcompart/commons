package de.compart.common.test.unit.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import de.compart.common.command.Command;
import de.compart.common.command.Task;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * User: torsten
 * Date: 2012/10
 * Time: 22:47
 *
 */
public class TaskUnitTest {
	//============================== CLASS VARIABLES ================================//
	//=============================== CLASS METHODS =================================//
	//===============================  VARIABLES ====================================//
	final MockCommand command = new MockCommand();
	private Task task;
	//==============================  CONSTRUCTORS ==================================//
	//=============================  PUBLIC METHODS =================================//

	@Before
	public void setUp() {
		task = new Task( command );
		assertThat( task, notNullValue() );
	}

	@Test
	public void create() {
		assertThat( new Task( null ), notNullValue() );
	}

	@Test
	public void notStartedTaskIsNotFinished() {
		assertThat( task.finished(), is( false ) );
	}

	@Test
	public void notStartedTaskIsNotSuccessful() {
		assertThat( task.successful(), is( false ) );
	}

	@Test
	public void receivedUniqueIdentifierShouldBeInitialized() {
		assertThat( task.getUniqueIdentifier(), notNullValue() );
	}

	@Test
	public void twoDifferentTaskAreTwoDifferentIdentifiers() {
		assertThat( new Task( command ).getUniqueIdentifier(), not( new Task( command ).getUniqueIdentifier() ) );
	}

	@Test
	public void executedCommandIsFinished() {
		task.doTask();
		assertThat( task.finished(), is( true ) );
	}

	@Test
	public void executedCommandFinishedCommand() {
		task.doTask();
		assertThat( command.isFinished(), is( true ) );
	}

	@Test
	public void executedCommandIsSuccessfulTask() {
		task.doTask();
		assertThat( task.successful(), is( true ) );
	}

	@Test( expected = IllegalStateException.class )
	public void nullCommandTriggersExceptionDuringExecution() {
		final Task task = new Task( null );
		task.doTask();
	}

	@Test( expected = Command.ExecutionException.class )
	public void executedCommandCanAlsoFail() {
		final Task task = new Task( new Command() {
			@Override
			public void execute() {
				throw new IllegalStateException( "This is an error" );
			}
		} );
		task.doTask();
	}

	// -------------- hashCode // equals ---------------------------------------- //

	@Test
	public void expectingSameWithTheSameCommand() {
		assertThat(task.equals(task), is(true));
	}

	@Test
	public void expectingNotTheSameWithNull() {
		assertThat(task.equals(null), is(false));
	}

	@Test
	public void expectingNotTheSameWithDifferentClass() {
		assertThat(task.equals("Some String"), is(false));
	}

	@Test
	public void expectingTheSameWithDifferentInstancesButSameUniqueIdentifierAndOtherValues() {
		final MockTask mockTask = new MockTask(task);
		assertThat(task.equals(mockTask), is(true));
	}

	@Test
	public void taskWithDifferentUniqueIdentifierIsDifferentFromOtherTask() {
		final Task differentTask = new Task(command);
		assertThat(differentTask.getUniqueIdentifier(), not(task.getUniqueIdentifier()));
		assertThat(task.equals(differentTask), is(false));
	}

	@Test
	public void taskWithNullValueIsNotTheSameAsTaskWithInitializedCommandValue() {
		final Task differentTask = new Task(null);
		assertThat(task.equals(differentTask), is(false));
	}

	@Test
	public void taskWithNullValueInvertedIsNotTheSameAsTaskWithInitializedCommandValue() {
		final Task differentTask = new Task(null);
		assertThat(differentTask.equals(task), is(false));
	}

	@Test
	public void taskWithNullValueInDifferentOrderIsNotTheSameAsTaskWithInitializedCommandValue() {
		final Task emptyTask = new Task(null);
		final MockTask mockTask = new MockTask(emptyTask);
		assertThat(emptyTask.equals(mockTask), is(true));
	}

	@Test
	public void taskNotSuccessfulIsDifferentFromSuccessfulTask() {
		final MockTask mockTask = new MockTask(task);
		mockTask.setCommand( new Command() {
			@Override
			public void execute() {
				throw new IllegalStateException("This makes the task not successful.");
			}
		} );
		task.doTask();
		try {
			mockTask.doTask();
			fail("This point of code should not be reached.");
		} catch (Command.ExecutionException ignore) {
			// ignore this exception, because it is expected
		}
		assertThat(task.equals(mockTask), is(false));
	}

	@Test
	public void taskSuccessFulIsDifferentFromNotStartedTask() {
		final MockTask mockTask = new MockTask(task);
		task.doTask();
		assertThat(task.equals(mockTask), is(false));
	}

	@Test
	public void expectingTheSameWithSameInstanceInADifferentState2() {
		final Task failingTask = new Task(new Command() {
			@Override
			public void execute() {
				throw new IllegalStateException("This is a test for another constructor argument.");
			}
		});

		try {
			failingTask.doTask();
		} catch (final Command.ExecutionException ex) {
			// ignore
		}
		assertThat(failingTask.equals(failingTask), is(true));
	}

	@Test
	public void expectingTheSameWithSameInstanceWithANullCommand() {
		final Task task = new Task(null);
		assertThat(task.equals(task), is(true));
	}

	@Test
	public void hashCodeExpectingSameOfSameInstance() {
		assertThat(task.hashCode(), is(task.hashCode()));
	}

	@Test
	public void hashCodeOfNullCommandIsLessThanWithCommand() {
		final Task localTask = new Task(null);
		assertThat(localTask.hashCode() < task.hashCode(), is(true));
	}

	@Test
	public void hashCodeOfFinishedTaskIsMoreThanNotFinishedCommand() {
		final Task localTask = new Task( new Command() {
			@Override
			public void execute() {
				throw new IllegalStateException("This brings errors to the command, and still the task will be finished.");
			}
		});

		final int hashCodeBefore = localTask.hashCode();

		try {
			localTask.doTask();
		} catch (Command.ExecutionException ex) {
			// ignore
		}

		final int hashCodeAfter = localTask.hashCode();
		assertThat(hashCodeBefore < hashCodeAfter, is(true));
	}

	@Test
	public void hashCodeOfFinishedAndSuccessFulTaskIsHigherThanNotFinishedOrNotSuccessFulCommand() {
		final int hashCodeBefore = task.hashCode();
		task.doTask();
		assertThat( task.hashCode() > hashCodeBefore, is( true ) );
	}



	//======================  PROTECTED/PACKAGE METHODS =============================//
	//============================  PRIVATE METHODS =================================//
	//=============================  INNER CLASSES ==================================//
	static class MockCommand implements Command {
		private boolean executed;

		@Override
		public void execute() throws ExecutionException {
			executed = true;
		}

		public boolean isFinished() {
			return this.executed;
		}
	}

	static class MockTask extends Task {
		public MockTask( final Task inputTask ) {
			super(inputTask);
		}

		public void setCommand(final Command command) {
			super.setCommand(command);
		}
	}
}