package deus.guilib.nodes.domsystem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import deus.guilib.GuiLib;
import deus.guilib.interfaces.nodes.ITextContent;
import deus.guilib.nodes.Root;
import deus.guilib.nodes.types.containers.Layout;
import deus.guilib.nodes.types.containers.ScrollableLayout;
import deus.guilib.nodes.types.eastereggs.Deus;
import deus.guilib.nodes.types.interaction.*;
import deus.guilib.nodes.types.inventory.CraftingTable;
import deus.guilib.nodes.types.inventory.PlayerInventory;
import deus.guilib.nodes.types.inventory.Slot;
import deus.guilib.nodes.types.representation.Image;
import deus.guilib.nodes.types.representation.Label;
import deus.guilib.nodes.types.representation.ProgressBar;
import deus.guilib.nodes.types.semantic.*;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.Sys;
import org.w3c.dom.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import org.xml.sax.SAXException;

public class XMLProcessor {

	private static Map<String, Class<?>> classNames = new HashMap<>();
	private static Map<String, Element> componentsMap = new HashMap<>();
	private static final Map<String, LogicalElementProcessor> logicalProcessors = new HashMap<>();


	static {
		// ? Common element to convert to nodes
		classNames.put(deus.guilib.nodes.Root.class.getSimpleName().toLowerCase(), deus.guilib.nodes.Root.class);
		classNames.put(Body.class.getSimpleName().toLowerCase(), Body.class);
		classNames.put(Div.class.getSimpleName().toLowerCase(), Div.class);
		classNames.put(Node.class.getSimpleName().toLowerCase(), Node.class);
		classNames.put(Span.class.getSimpleName().toLowerCase(), Span.class);
		classNames.put(Label.class.getSimpleName().toLowerCase(), Label.class);
		classNames.put(Layout.class.getSimpleName().toLowerCase(), Layout.class);
		classNames.put(Slot.class.getSimpleName().toLowerCase(), Slot.class);
		classNames.put(Image.class.getSimpleName().toLowerCase(), Image.class);
		classNames.put(TextArea.class.getSimpleName().toLowerCase(), TextArea.class);
		classNames.put(TextField.class.getSimpleName().toLowerCase(), TextField.class);
		classNames.put(Slider.class.getSimpleName().toLowerCase(), Slider.class);
		classNames.put(DraggableElement.class.getSimpleName().toLowerCase(), DraggableElement.class);
		classNames.put(PlayerInventory.class.getSimpleName().toLowerCase(), PlayerInventory.class);
		classNames.put(CraftingTable.class.getSimpleName().toLowerCase(), CraftingTable.class);
		classNames.put(ProgressBar.class.getSimpleName().toLowerCase(), ProgressBar.class);
		classNames.put(Button.class.getSimpleName().toLowerCase(), Button.class);
		classNames.put(Deus.class.getSimpleName().toLowerCase(), Deus.class);
		classNames.put(ScrollableLayout.class.getSimpleName().toLowerCase(), ScrollableLayout.class);
		classNames.put(Test.class.getSimpleName().toLowerCase(), Test.class);

		// ? Logical elements to process
		logicalProcessors.put("templates", XMLProcessor::processTemplates);
		logicalProcessors.put("module", (a,b,c)->{GuiLib.LOGGER.info("Module must be inside a 'templates' tag ");});
		logicalProcessors.put("component", (a,b,c)->{GuiLib.LOGGER.info("Component must be inside a module");});
		logicalProcessors.put("link", XMLProcessor::processLink);
	}

	// ? TOOL METHODS
	// * ------------------------------------------------------------------------------------------------------------------------------- * //

	/**
	 * Prints the child nodes of a given {@link INode} in a tree-like format.
	 *
	 * @param node   The parent node whose children will be printed.
	 * @param prefix The prefix used for formatting the tree structure.
	 * @param lvl    The current depth level in the tree.
	 */
	public static void printChildNodes(INode node, String prefix, int lvl) {
		System.out.println(prefix.repeat(lvl) + " | CLASS: " + node.getClass().getSimpleName() + " | ID: " + node.getId() + " | GROUP: " + node.getGroup());

		if (!node.getChildren().isEmpty()) {
			for (INode childNode : node.getChildren()) {
				printChildNodes(childNode, prefix, lvl + 1);
			}
		}
	}

	public static INode getTemplate(String key) {
		INode base = new Node();
		transformChildren(componentsMap.get(key), base);
		return base;
	}

	public static void registerNode(String id, String nodeName, Class<?> node) {
		if (id == null || id.isEmpty()) {
			GuiLib.LOGGER.error(XMLError.EMPTY_MOD_ID.getMessage());
			return;
		}

		if (nodeName == null || nodeName.isEmpty()) {
			GuiLib.LOGGER.error(XMLError.EMPTY_MOD_NODE_NAME.getMessage());
			return;
		}

		if (node == null) {
			GuiLib.LOGGER.error(XMLError.NULL_NODE.getMessage());
			return;
		}

		try {
			Constructor<?> constructor = node.getConstructor(Map.class);
			classNames.put(id + "_" + nodeName.toLowerCase(), node);
			GuiLib.LOGGER.info("Registered Node with name: {}_{}", id, nodeName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("The node class does not have a constructor that accepts a Map parameter.", e);
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred while registering the node: " + e.getMessage(), e);
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
	 * Creates a node instance based on its class name and attributes, using reflection.
	 *
	 * @param name       The simple name of the class for the node.
	 * @param attributes A map of attributes to initialize the node.
	 * @param element    The XML element containing additional information like text content.
	 * @return An instance of {@link INode}.
	 */
	private static INode createNodeByName(String name, Map<String, String> attributes, Element element) {
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

	// ? PARSING METHODS
	// * ------------------------------------------------------------------------------------------------------------------------------- * //

	public static Element parseXML(String path) {
		try {
			GuiLib.LOGGER.info("Parsing XML: {}", path);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			document.getDocumentElement().normalize();
			return document.getDocumentElement();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new RuntimeException(e);
		}

	}

	public static Element parseXML(InputStream inputStream) {
		try {
			GuiLib.LOGGER.info("Parsing XML");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			document.getDocumentElement().normalize();
			return document.getDocumentElement();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new RuntimeException(e);
		}

	}

	public static INode getNodeTree(String path, boolean withRoot) {
		return processDOM(parseXML(path), withRoot);
	}

	public static INode getNodeTree(InputStream inputStream, boolean withRoot) {
		return processDOM(parseXML(inputStream), withRoot);
	}

	public static INode processDOM(Element element, boolean withRoot) {
		try {
			GuiLib.LOGGER.info("Processing the document...");

			Element root = element;

			Root rootNode = new Root();
			if (!withRoot) {
				rootNode = new Node();
			}

			GuiLib.LOGGER.info("Transforming children...");
			transformChildren(root, rootNode);

			return rootNode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Recursively parses child nodes of the given XML element and adds them as children to the parent node.
	 *
	 * @param root      The XML element whose children are to be parsed.
	 * @param parentNode The parent {@link INode} to which parsed children will be added.
	 */
	private static void transformChildren(Element root, INode parentNode) {
		NodeList nodes = root.getChildNodes();

		List<Element> logicalNodes = new ArrayList<>();
		List<Element> commonNodes = new ArrayList<>();

		// Iterating all dom nodes
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node node = nodes.item(i);

			// Check element type
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

				Element elem = (Element) node;
				String nodeName = node.getLocalName() != null ? node.getLocalName() : node.getNodeName();

				if (logicalProcessors.containsKey(nodeName.toLowerCase())) {
					// If is logical
					logicalNodes.add(elem);
				} else {
					// If not
					commonNodes.add(elem);
				}
			}
		}

		// Process logical nodes
		for (Element elem : logicalNodes) {
			String nodeName = elem.getLocalName() != null ? elem.getLocalName() : elem.getNodeName();
			Map<String, String> attributes = getAttributesAsMap(elem);
			logicalProcessors.get(nodeName).process(attributes, elem, parentNode);
		}

		// Process common nodes
		for (Element elem : commonNodes) {

			String nodeName = elem.getLocalName() != null ? elem.getLocalName() : elem.getNodeName();
			Map<String, String> attributes = getAttributesAsMap(elem);

			INode newNode = null;

			if (nodeName.equals("call")) {
				// If is component
				String name = attributes.get("component");
				if (componentsMap.containsKey(name)) {
					newNode = getTemplate(name);
					attributes.put("group", name);
					attributes.remove("component");
					newNode.setAttributes(attributes);
				}
			} else {
				// If not
				newNode = createNodeByName(nodeName.toLowerCase(), attributes, elem);
			}

			if (newNode != null) {
				parentNode.addChildren(newNode);
				transformChildren(elem, newNode);
			}
		}
	}



	private static void processLink(Map<String, String> attributes, Element element, INode parentNode) {
		if (!(parentNode instanceof Root)) {
			GuiLib.LOGGER.error(XMLError.LINK_NODE_NOT_ROOT_LEVEL.getMessage());
			return;
		}

		String src = attributes.get("src");
		if (src != null && !src.isEmpty()) {

			Map<String, String> stringStringMap = new HashMap<>();


			if (parentNode.getAttributes().containsKey("yaml_path")) {
				stringStringMap.put("yaml_path", parentNode.getAttributes().get("yaml_path") + "ª" + src);
				parentNode.setAttributes(
					stringStringMap
				);
			} else {
				stringStringMap.put("yaml_path", src);
				parentNode.setAttributes(stringStringMap);
			}


			// GuiLib.LOGGER.info("Set 'yaml_path' attribute in Root: {}", src);
		} else {
			GuiLib.LOGGER.warn(XMLError.LINK_NODE_SRC_ATTR_EMPTY.getMessage());
		}
	}


	// ? PARSING TEMPLATES METHODS
	// * ------------------------------------------------------------------------------------------------------------------------------- * //
	private static void processTemplates(Map<String, String> attributes, Element element, INode parentNode) {
		// GuiLib.LOGGER.info("Processing the 'templates' tag...");

		String namespace = attributes.get("name");

		Element mainElement = element;

		if (attributes.containsKey("src")) {

			String src = attributes.get("src");
			if (!src.isEmpty()) {
				if (src.startsWith("/assets")) {
					InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(src);
					mainElement = (Element) parseXML(inputStream).getElementsByTagName("templates").item(0);
				} else {
					mainElement = (Element) parseXML(src).getElementsByTagName("templates").item(0);
				}
			}
		}

		NodeList modules = mainElement.getChildNodes();

		Map<String, NodeList> templates = processModules(namespace, modules);
		if (templates == null) {
			return;
		}

		// GuiLib.LOGGER.info("Processing components...");
		componentsMap = processComponents(templates);

		// GuiLib.LOGGER.info("Finished processing components.");
	}


	private static Map<String, NodeList> processModules(String nameSpace, NodeList modules) {
		for (int i = 0; i < modules.getLength(); i++) {
			org.w3c.dom.Node moduleNode = modules.item(i);
			if (moduleNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element elemModule = (Element) moduleNode;

				String moduleName = elemModule.getAttribute("name");
				NodeList templates = elemModule.getChildNodes();

				Map<String, NodeList> module = new HashMap<>();
				module.put(nameSpace + "." + moduleName, templates);

				return module;
			}
		}
		return null;
	}

	private static Map<String, Element> processComponents(Map<String, NodeList> module) {
		Map<String, org.w3c.dom.Element> componentsMap = new HashMap<>();

		module.forEach((modName, templates) -> {
			for (int i = 0; i < templates.getLength(); i++) {
				org.w3c.dom.Node templateNode = templates.item(i);
				if (templateNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element elemTemplate = (Element) templateNode;
					String templateName = elemTemplate.getAttribute("name");

					//Map<String, String> attr = getAttributesAsMap(elemTemplate);
					//attr.put("group", modName + "." + templateName);

					//Node templateContainer = new Node();
					//templateContainer.setAttributes(attr);


					//transformChildren(elemTemplate, templateContainer);

					//printChildNodes(templateContainer,"-",0);


					componentsMap.put(modName + "." + templateName, elemTemplate);
				}
			}
		});

		return componentsMap;
	}



}
