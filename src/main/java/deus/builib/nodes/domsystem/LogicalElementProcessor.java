package deus.builib.nodes.domsystem;

import deus.builib.interfaces.nodes.INode;
import org.w3c.dom.Element;

import java.util.Map;

@FunctionalInterface
interface LogicalElementProcessor {
	void process(Map<String, String> attributes, Element element, INode parentNode);
}
