package deus.guilib.interfaces;

import deus.guilib.interfaces.element.IElement;

import java.util.List;

@FunctionalInterface
public interface IChildrenLambda {
	void apply(List<IElement> elements);
}
