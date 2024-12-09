package deus.guilib.nodes.domsystem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import deus.guilib.GuiLib;
import deus.guilib.interfaces.nodes.ITextContent;
import deus.guilib.nodes.Root;
import deus.guilib.nodes.types.containers.Bar;
import deus.guilib.nodes.types.containers.Panel;
import deus.guilib.nodes.types.interaction.DraggableElement;
import deus.guilib.nodes.types.interaction.ScrollBar;
import deus.guilib.nodes.types.interaction.TextArea;
import deus.guilib.nodes.types.interaction.TextField;
import deus.guilib.nodes.types.inventory.CraftingTable;
import deus.guilib.nodes.types.inventory.PlayerInventory;
import deus.guilib.nodes.types.inventory.Slot;
import deus.guilib.nodes.types.representation.Image;
import deus.guilib.nodes.types.representation.Label;
import deus.guilib.nodes.types.representation.ProgressBar;
import deus.guilib.nodes.types.semantic.*;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;

public class XMLProcessor {

	private static Map<String, Class<?>> classNames = new HashMap<>();

	static {
		classNames.put(deus.guilib.nodes.Root.class.getSimpleName().toLowerCase(), deus.guilib.nodes.Root.class);
		classNames.put(Body.class.getSimpleName().toLowerCase(), Body.class);
		classNames.put(Div.class.getSimpleName().toLowerCase(), Div.class);
		classNames.put(Node.class.getSimpleName().toLowerCase(), Node.class);
		classNames.put(Span.class.getSimpleName().toLowerCase(), Span.class);
		classNames.put(Label.class.getSimpleName().toLowerCase(), Label.class);
		classNames.put(Bar.class.getSimpleName().toLowerCase(), Bar.class);
		classNames.put(Slot.class.getSimpleName().toLowerCase(), Slot.class);
		classNames.put(Panel.class.getSimpleName().toLowerCase(), Panel.class);
		classNames.put(Image.class.getSimpleName().toLowerCase(), Image.class);
		classNames.put(TextArea.class.getSimpleName().toLowerCase(), TextArea.class);
		classNames.put(TextField.class.getSimpleName().toLowerCase(), TextField.class);
		classNames.put(ScrollBar.class.getSimpleName().toLowerCase(), ScrollBar.class);
		classNames.put(DraggableElement.class.getSimpleName().toLowerCase(), DraggableElement.class);
		classNames.put(PlayerInventory.class.getSimpleName().toLowerCase(), PlayerInventory.class);
		classNames.put(CraftingTable.class.getSimpleName().toLowerCase(), CraftingTable.class);
		classNames.put(ProgressBar.class.getSimpleName().toLowerCase(), ProgressBar.class);
	}

	public static void registerNode(@NotNull String modId, @NotNull String nodeName, @NotNull Class<?> node) {
		if (modId == null || modId.isEmpty()) {
			throw new IllegalArgumentException("The 'modId' cannot be null or empty.");
		}

		if (nodeName == null || nodeName.isEmpty()) {
			throw new IllegalArgumentException("The 'nodeName' cannot be null or empty.");
		}

		if (node == null) {
			throw new IllegalArgumentException("The 'node' cannot be null.");
		}

		try {
			Constructor<?> constructor = node.getConstructor(Map.class);
			classNames.put(modId + "_" + nodeName.toLowerCase(), node);
			GuiLib.LOGGER.info("Registered Node with name: {}_{}", modId, nodeName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("The node class does not have a constructor that accepts a Map parameter.", e);
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred while registering the node: " + e.getMessage(), e);
		}
	}

	/**
	 * Parses an XML file and returns the root node of the XML as an {@link INode}.
	 * The root can either be a {@link Root} or {@link Node} based on the `withRoot` parameter.
	 *
	 * @param path     The path of the XML file to parse.
	 * @param withRoot If true, the root node will be a {@link Root}; otherwise, a {@link Node}.
	 * @return The parsed root node.
	 */
	public static INode parseXML(String path, boolean withRoot) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			Root rootNode = new Root();
			if (!withRoot) {
				rootNode = new Node();
			}

			parseChildren(root, rootNode);

			return rootNode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Parses an XML file and returns a {@link Root} node.
	 *
	 * @param path The path of the XML file to parse.
	 * @return The parsed root node.
	 */
	public static INode parseXML(String path) {
		return parseXML(path, true);
	}

	/**
	 * Recursively parses child nodes of the given XML element and adds them as children to the parent node.
	 *
	 * @param root      The XML element whose children are to be parsed.
	 * @param parentNode The parent {@link INode} to which parsed children will be added.
	 */
	private static void parseChildren(Element root, INode parentNode) {
		NodeList nodes = root.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node node = nodes.item(i);

			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				String nodeName = node.getLocalName() != null ? node.getLocalName() : node.getNodeName();

				Map<String, String> attributes = getAttributesAsMap(elem);
				INode newNode = createNodeByClassSimpleName(nodeName.toLowerCase(), attributes, elem);

				parentNode.addChild(newNode);

				parseChildren(elem, newNode);
			}
		}
	}

	/**
	 * Creates a node instance based on its class name and attributes, using reflection.
	 *
	 * @param name       The simple name of the class for the node.
	 * @param attributes A map of attributes to initialize the node.
	 * @param element    The XML element containing additional information like text content.
	 * @return An instance of {@link INode}.
	 */
	private static INode createNodeByClassSimpleName(String name, Map<String, String> attributes, Element element) {
		try {
			Class<?> clazz = classNames.getOrDefault(name, deus.guilib.nodes.Node.class);

			Constructor<?> constructor = clazz.getConstructor(Map.class);
			Object instance = constructor.newInstance(attributes);

			if (instance instanceof ITextContent) {
				((ITextContent) instance).setTextContent(element.getTextContent().trim());
			}

			return (INode) instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts the attributes of an XML element to a map of attribute names and values.
	 *
	 * @param elem The XML element whose attributes are to be converted.
	 * @return A map containing attribute names as keys and their values as values.
	 */
	private static Map<String, String> getAttributesAsMap(Element elem) {
		Map<String, String> attributesMap = new HashMap<>();

		if (elem.hasAttributes()) {
			NamedNodeMap attributeNodes = elem.getAttributes();

			for (int i = 0; i < attributeNodes.getLength(); i++) {
				org.w3c.dom.Node attribute = attributeNodes.item(i);
				attributesMap.put(attribute.getNodeName(), attribute.getNodeValue());
			}
		}

		return attributesMap;
	}

	/**
	 * Prints the child nodes of a given {@link INode} in a tree-like format.
	 *
	 * @param node   The parent node whose children will be printed.
	 * @param prefix The prefix used for formatting the tree structure.
	 * @param lvl    The current depth level in the tree.
	 */
	public static void printChildNodes(INode node, String prefix, int lvl) {
		System.out.println(prefix.repeat(lvl) + node.getClass().getSimpleName() + " |GPOS: " + node.getGx() + ":" + node.getGx());

		if (!node.getChildren().isEmpty()) {
			for (INode childNode : node.getChildren()) {
				printChildNodes(childNode, prefix, lvl + 1);
			}
		}
	}
}
