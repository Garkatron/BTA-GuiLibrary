package deus.builib.interfaces;

import deus.builib.interfaces.nodes.INode;

@FunctionalInterface
public interface IChildLambda {
	void apply(INode element);
}
