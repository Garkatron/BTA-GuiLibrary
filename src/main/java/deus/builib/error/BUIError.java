package deus.builib.error;

/**
 * Enum to represent different types of errors that can occur in the GUI library.
 */
public enum BUIError {

	MISSING_MC("Missing Minecraft reference."),;

	// The error message associated with each error type.
	private final String message;

	/**
	 * Constructor to initialize the enum with a specific error message.
	 *
	 * @param message The error message to associate with the error type.
	 */
	BUIError(String message) {
		this.message = message;
	}

	/**
	 * Retrieves the error message associated with the error.
	 *
	 * @return The error message as a string.
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return name() + ": " + message;
	}
}
