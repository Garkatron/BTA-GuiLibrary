package deus.guilib.util;

import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.domsystem.XMLProcessor;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import static deus.guilib.GuiLib.MOD_ID;

/**
 * A utility class to assist with GUI-related operations and XML processing.
 */
public class GuiHelper {

	public static int mouseX = 0;
	public static int mouseY = 0;

	/**
	 * Retrieves the texture ID for a GUI element.
	 *
	 * @param texture The name of the texture file (e.g., "button.png").
	 * @return The texture ID associated with the specified texture.
	 */
	public static int getGuiTexture(String texture) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		return mc.renderEngine.getTexture("/assets/" + MOD_ID + "/gui/" + texture);
	}

	/**
	 * Registers a custom node for XML processing.
	 *
	 * @param modId The identifier of the mod registering the node.
	 * @param nodeName The name of the node to register.
	 * @param node The implementation of the node to be registered.
	 */
	public static void registerNode(@NotNull String modId, @NotNull String nodeName, @NotNull Class<?> node) {
		XMLProcessor.registerNode(modId, nodeName, node);
	}

	/**
	 * Parses an XML file and returns the root node of its structure.
	 *
	 * @param path The file path of the XML file to parse.
	 * @return The root node containing the parsed nodes from the XML file.
	 */
	public static INode getComponent(String path) {
		return XMLProcessor.parseXML(path, false);
	}
}
