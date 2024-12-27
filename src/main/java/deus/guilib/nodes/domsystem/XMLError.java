package deus.guilib.nodes.domsystem;

public enum XMLError {

	// * NULL/EMPTY VALUES
	EMPTY_MOD_ID("[XML] The 'modId' cannot be null or empty."),
	EMPTY_MOD_NODE_NAME("[XML] The 'nodeName' cannot be null or empty."),
	NULL_NODE("[XML] The 'node' cannot be null."),

	// * LINK NODE
	LINK_NODE_NOT_ROOT_LEVEL("[XML] 'link' can only be processed at the Root level."),
	LINK_NODE_SRC_ATTR_EMPTY("[XML] 'link' element is missing the 'src' attribute.")

	;

	private final String message;

	XMLError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
