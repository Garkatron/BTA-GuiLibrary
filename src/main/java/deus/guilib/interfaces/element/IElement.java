package deus.guilib.interfaces.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.IElementConfigLambda;
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
	 * Gets the Y coordinate of the element.
	 *
	 * @return The Y position of the element.
	 */
	int getY();

	/**
	 * Sets the X and Y position of the element.
	 *
	 * @param x The X position to set.
	 * @param y The Y position to set.
	 * @return The current instance of the element.
	 */
	IElement setPosition(int x, int y);

	/**
	 * @return The current instance of the element.
	 */
	IElement setPosition(Placement placement);

	/**
	 * Sets the global X and Y position of the element.
	 *
	 * @param gx The global X position to set.
	 * @param gy The global Y position to set.
	 * @return The current instance of the element.
	 */
	IElement setGlobalPosition(int gx, int gy);


	Placement getPlacement();

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
	 * Sets the configuration for the element using a lambda function.
	 *
	 * @param configLambda The configuration lambda to apply.
	 * @return The current instance of the element.
	 */
	IElement config(IElementConfigLambda<ElementConfig> configLambda);

	/**
	 * Gets the list of child elements.
	 *
	 * @return A list of child elements.
	 */
	List<IElement> getChildren();

	/**
	 * Adds child elements to this element.
	 *
	 * @param children The child elements to add.
	 * @return The current instance of the element.
	 */
	IElement addChildren(IElement... children);
	IElement addChild(IElement child);

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

	/**
	 * Gets the global Y coordinate of the element.
	 *
	 * @return The global Y position of the element.
	 */
	int getGy();

	/**
	 * Gets the global X coordinate of the element.
	 *
	 * @return The global X position of the element.
	 */
	int getGx();

	/**
	 * Gets the parent of the element.
	 *
	 * @return The parent element.
	 */
	IElement getParent();

	/**
	 * Sets the parent of the element.
	 *
	 * @param parent The parent element to set.
	 */
	void setParent(IElement parent);

	/**
	 * Modifies child elements using a lambda function.
	 *
	 * @param lambda The lambda function to modify children.
	 * @return The current instance of the element.
	 */
	IElement modifyChildren(IChildrenLambda lambda);

	/**
	 * Modifies a specific child element using a lambda function.
	 *
	 * @param index  The index of the child element to modify.
	 * @param lambda The lambda function to modify the child.
	 * @return The current instance of the element.
	 */
	IElement modifyChild(int index, IChildLambda lambda);

	/**
	 * Gets the child element with the specified SID.
	 *
	 * @param sid The SID of the child element to find.
	 * @return The child element with the specified SID.
	 */
	IElement getElementWithSid(String sid);

	/**
	 * Gets a list of child elements in the specified group.
	 *
	 * @param group The group to filter child elements by.
	 * @return A list of child elements in the specified group.
	 */
	List<IElement> getElementsInGroup(String group);
}
