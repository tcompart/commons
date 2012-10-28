package de.compart.common.test.unit.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
		task = new Task(command);
		assertThat(task, notNullValue());
	}

	@Test
	public void create() {
		assertThat(new Task(null), notNullValue());
	}

	@Test
	public void notStartedTaskIsNotFinished() {
		assertThat(task.finished(), is(false));
	}

	@Test
	public void notStartedTaskIsNotSuccessful() {
		assertThat(task.successful(), is(false));
	}

	@Test
	public void receivedUniqueIdentifierShouldBeInitialized() {
		assertThat(task.getUniqueIdentifier(), notNullValue());
	}

	@Test
	public void twoDifferentTaskAreTwoDifferentIdentifiers() {
		assertThat(new Task(command).getUniqueIdentifier(), not(new Task(command).getUniqueIdentifier()));
	}

	@Test
	public void executedCommandIsFinished() {
		task.doTask();
		assertThat(task.finished(), is(true));
	}

	@Test
	public void executedCommandFinishedCommand() {
		task.doTask();
		assertThat(command.isFinished(), is(true));
	}

	@Test
	public void executedCommandIsSuccessfulTask() {
		task.doTask();
		assertThat(task.successful(), is(true));
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
}