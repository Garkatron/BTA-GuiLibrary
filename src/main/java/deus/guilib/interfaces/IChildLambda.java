package deus.guilib.interfaces;

import deus.guilib.interfaces.nodes.INode;

@FunctionalInterface
public interface IChildLambda {
	void apply(INode element);
}
