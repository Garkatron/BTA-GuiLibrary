package deus.guilib.interfaces.element;

import java.util.List;

public interface IRootNode {
	INode getNodeById(String id);
	INode getNodeByGroup(String group);
	List<INode> getNodeByClass(String className);
}
