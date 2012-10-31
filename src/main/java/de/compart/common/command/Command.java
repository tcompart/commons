package de.compart.common.command;

public interface Command {

	void execute();

	class ExecutionException extends RuntimeException {

		public ExecutionException( String inputMessage, Throwable inputEx ) {
			super( inputMessage, inputEx );
		}

		private static final long serialVersionUID = 1L;
	}

}
