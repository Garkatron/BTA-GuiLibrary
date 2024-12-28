package deus.builib.interfaces;

import deus.builib.interfaces.nodes.INode;

import java.util.List;

@FunctionalInterface
public interface IChildrenLambda {
	void apply(List<INode> elements);
}
