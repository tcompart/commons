package de.compart.common.command;

public interface Command {

	void execute() throws ExecutionException;

	class ExecutionException extends RuntimeException {

		public ExecutionException( Throwable inputEx ) {
			super( inputEx );
		}

		public ExecutionException( String inputMessage, Throwable inputEx ) {
			super( inputMessage, inputEx );
		}

		public ExecutionException( final String inputMessage ) {
			super( inputMessage );
		}

		private static final long serialVersionUID = 1L;
	}

}
