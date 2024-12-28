package deus.builib.util;

import java.util.Arrays;

public class Utils {

	/**
	 * Marks a method or section of code as not implemented yet.
	 * Throws an UnsupportedOperationException to indicate that this method is a placeholder.
	 */
	public static void todo() {
		throw new UnsupportedOperationException("This method is not yet implemented.");
	}

	/**
	 * Prints the class name and line number of the caller.
	 */
	public static void printClassInfo() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		// Index 2 provides the caller's information (index 0 is getStackTrace, index 1 is printStaticInfo)
		if (stackTrace.length > 2) {
			StackTraceElement caller = stackTrace[2];
			String className = caller.getClassName();
			int lineNumber = caller.getLineNumber();
			System.out.printf("Class: %s, Line: %d%n", className, lineNumber);
		} else {
			System.out.println("Unable to retrieve caller information.");
		}
	}

	/**
	 * Prints the class name, line number of the caller, and a custom message.
	 *
	 * @param message The custom message to print.
	 */
	public static void printClassInfo(String ...message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		// Index 2 provides the caller's information (index 0 is getStackTrace, index 1 is printStaticInfo)
		if (stackTrace.length > 2) {
			StackTraceElement caller = stackTrace[2];
			String className = caller.getClassName();
			int lineNumber = caller.getLineNumber();
			System.out.printf("Class: %s, Line: %d, Message: %s%n", className, lineNumber, Arrays.toString(message));
		} else {
			System.out.println("Unable to retrieve caller information.");
		}
	}

	/**
	 * Prints the provided arguments with a custom message.
	 *
	 * @param message The custom message to include before the values.
	 * @param values  The values to print.
	 */
	public static void print(String message, Object... values) {
		// Construct the message with the provided values
		String formattedValues = Arrays.stream(values)
			.map(String::valueOf) // Convert each value to a String
			.reduce((a, b) -> a + ", " + b) // Join all values with a comma
			.orElse(""); // Fallback to an empty string if no values are provided

		// Print the message and values
		System.out.println(message + ": [" + formattedValues + "]");
	}
}
