package deus.guilib.interfaces.element;

import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.resource.Texture;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * Interface representing a graphical element in the GUI.
 * It contains methods to manage textures, position, configuration, and the hierarchy of child elements.
 */
public interface IElement {

	/**
	 * Gets the texture of the element.
	 *
	 * @return The texture of the element.
	 */
	Texture getTexture();

	/**
	 * Sets the texture of the element.
	 *
	 * @param texture The texture to be set.
	 * @return The current instance of the element.
	 */
	IElement setTexture(Texture texture);

	/**
	 * Gets the X coordinate of the element.
	 *
	 * @return The X position of the element.
	 */
	int getX();

	/**
	 * Sets the X coordinate of the element.
	 *
	 * @param x The X position to set.
	 * @return The current instance of the element.
	 */
	IElement setX(int x);

	/**
	 * Gets the Y coordinate of the element.
	 *
	 * @return The Y position of the element.
	 */
	int getY();

	/**
	 * Sets the Y coordinate of the element.
	 *
	 * @param y The Y position to set.
	 * @return The current instance of the element.
	 */
	IElement setY(int y);

	/**
	 * Sets the X and Y position of the element.
	 *
	 * @param x The X position to set.
	 * @param y The Y position to set.
	 * @return The current instance of the element.
	 */
	IElement setPosition(int x, int y);

	/**
	 * Gets the width of the element.
	 *
	 * @return The width of the element.
	 */
	int getWidth();

	/**
	 * Gets the height of the element.
	 *
	 * @return The height of the element.
	 */
	int getHeight();

	/**
	 * Gets the configuration of the element.
	 *
	 * @return The configuration object of the element.
	 */
	ElementConfig getConfig();

	/**
	 * Sets the configuration for the element.
	 *
	 * @param config The configuration object to be set.
	 */
	void setConfig(ElementConfig config);

	/**
	 * Sets the configuration for the element and returns the current instance.
	 *
	 * @param config The configuration object to be set.
	 * @return The current instance of the element.
	 */
	IElement config(ElementConfig config);

	/**
	 * Gets the list of child elements.
	 *
	 * @return A list of child elements.
	 */
	List<IElement> getChildren();

	/**
	 * Sets the child elements for this element.
	 *
	 * @param children The child elements to be set.
	 * @return The current instance of the element.
	 */
	IElement setChildren(IElement... children);

	/**
	 * Adds child elements to this element.
	 *
	 * @param children The child elements to add.
	 * @return The current instance of the element.
	 */
	IElement addChildren(IElement... children);

	/**
	 * Sets the Minecraft instance.
	 *
	 * @param mc The Minecraft instance to be set.
	 */
	void setMc(Minecraft mc);

	/**
	 * Checks if the element has any dependencies set.
	 *
	 * @return True if the element has dependencies, false otherwise.
	 */
	boolean hasDependency();

	/**
	 * Draws the element.
	 */
	void draw();

	/**
	 * Sets whether the element has a position set.
	 *
	 * @param positioned True to mark the element as positioned.
	 * @return The current instance of the element.
	 */
	IElement setPositioned(boolean positioned);

	/**
	 * Checks if the element has a position set.
	 *
	 * @return True if the element is positioned, false otherwise.
	 */
	boolean isPositioned();

	/**
	 * Gets the group of the element.
	 *
	 * @return The group of the element.
	 */
	String getGroup();

	/**
	 * Gets the SID (session identifier) of the element.
	 *
	 * @return The SID of the element.
	 */
	String getSid();

	/**
	 * Sets the SID (session identifier) for the element.
	 *
	 * @param sid The SID to be set.
	 * @return The current instance of the element.
	 */
	IElement setSid(String sid);

	/**
	 * Sets the group for the element.
	 *
	 * @param group The group to be set.
	 * @return The current instance of the element.
	 */
	IElement setGroup(String group);
}
