package de.compart.common.command;

public interface Command {

	public void execute() throws CommandExecutionException;

	public static class CommandExecutionException extends RuntimeException {

		public CommandExecutionException( Throwable inputEx ) {
			super( inputEx );
		}

		public CommandExecutionException( String inputMessage, Throwable inputEx ) {
			super( inputMessage, inputEx );
		}

		public CommandExecutionException( final String inputMessage ) {
			super( inputMessage );
		}

		private static final long serialVersionUID = 1L;
	}

}
