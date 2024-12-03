package deus.guilib.interfaces.nodes;

import java.util.List;

public interface IRootNode {
	INode getNodeById(String id);
	List<INode> getNodeByGroup(String group);
	List<INode> getNodeByClass(String className);
}
