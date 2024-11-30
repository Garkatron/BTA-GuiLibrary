package deus.guilib.element.domsystem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import deus.guilib.element.Root;
import deus.guilib.element.elements.representation.Text;
import deus.guilib.element.elements.semantic.*;

import org.w3c.dom.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;

import deus.guilib.element.Node;
import deus.guilib.interfaces.element.INode;


public class XMLProcessor {

	private static final Map<String, Class<?>> classNames = Map.of(
		Root.class.getSimpleName(), Root.class,
		Body.class.getSimpleName(), Body.class,
		Div.class.getSimpleName(), Div.class,
		Node.class.getSimpleName(), Node.class,
		Span.class.getSimpleName(), Span.class,
		Text.class.getSimpleName(), Text.class
	);


	public static INode parseXML(String path) {
		try {
			// Reading xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			document.getDocumentElement().normalize();

			// Obtain root node
			Element root = document.getDocumentElement();

			// Parsing tags to Elements
			Node rootNode = new Node();
			parseChildren(root, rootNode);


			return rootNode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void parseChildren(Element root, INode parentNode) {
		NodeList nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node node = nodes.item(i);

			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element elem = (Element) node;

				// Obtener el nombre del nodo sin espacio de nombres (si lo hay)
				String nodeName = node.getLocalName() != null ? node.getLocalName() : node.getNodeName();

				// Capitalizar la primera letra del nombre del nodo
				String capitalized = nodeName.substring(0, 1).toUpperCase() + nodeName.substring(1);

				// Crear un nuevo nodo
				INode newNode = createNodeByClassSimpleName(capitalized);

				// Agregar el nuevo nodo al nodo padre
				parentNode.addChild(newNode);

				// Llamada recursiva para procesar los hijos de este nodo
				parseChildren(elem, newNode);
			}
		}
	}


	private static INode createNodeByClassSimpleName(String name) {
		try {
			Constructor<?> constructor = classNames.getOrDefault(name, Root.class).getConstructor();

			Object instance = constructor.newInstance();

			return (INode) instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void printChildNodes(INode node, String prefix, int lvl) {
		System.out.println(prefix.repeat(lvl) + node.getClass().getSimpleName());

		if (!node.getChildren().isEmpty()) {
			for (INode childNode : node.getChildren()) {
				printChildNodes(childNode, prefix, lvl + 1);
			}
		}
	}

}
