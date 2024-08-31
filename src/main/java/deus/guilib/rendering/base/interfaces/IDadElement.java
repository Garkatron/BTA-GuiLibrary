package deus.guilib.rendering.base.interfaces;

import deus.guilib.rendering.base.Element;

import java.util.List;

public interface IDadElement {
	List<Element> getChildren();
	Element setChildren(Element...children);
	Element addChildren(Element...children);

}
