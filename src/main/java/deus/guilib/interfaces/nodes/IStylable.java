package deus.guilib.interfaces.nodes;

import java.util.Map;

public interface IStylable {
	void applyStyle(Map<String, Object> styles);
	Map<String, Object> getStyle();
	void deleteStylesRecursive();
	void deleteStyles();
}
