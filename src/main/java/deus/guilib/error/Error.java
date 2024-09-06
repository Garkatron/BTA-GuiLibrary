package deus.guilib.error;

/**
 * Enum to represent different types of errors that can occur in the GUI library.
 */
public enum Error {

	MISSING_MC("\n\n[GUI Lib] -> Missing Minecraft reference."),
	MISSING_GUI("\n\n[GUI Lib] -> Missing GUI reference."),
	SAME_ELEMENT_SID("\n\n[GUI Lib] -> A element with the same SID already exists.")

	;

	// The error message associated with each error type.
	private final String message;

	/**
	 * Constructor to initialize the enum with a specific error message.
	 *
	 * @param message The error message to associate with the error type.
	 */
	Error(String message) {
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
