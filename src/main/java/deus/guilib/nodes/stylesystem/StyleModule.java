package deus.guilib.nodes.stylesystem;

import deus.guilib.interfaces.nodes.INode;

import java.util.HashMap;
import java.util.Map;

public class StyleModule {
	private final Map<String, Runnable> stateActions = new HashMap<>();



	public void addStateAction(String state, Runnable action) {
		stateActions.put(state, action);
	}

	public void applyStateAction(String state) {
		Runnable action = stateActions.get(state);
		if (action != null) {
			action.run();
		}
	}


}
