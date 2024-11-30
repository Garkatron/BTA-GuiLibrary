package deus.guilib.interfaces.element;

import deus.guilib.element.config.Placement;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;

import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Map;

/**
 * Represents a graphical element within the Graphical User Interface (GUI).
 * Defines methods to manage textures, positioning, configuration, and child elements.
 */
public interface INode extends IDrawable {

	/*
	/**
	 * Retrieves the element's texture.
	 *
	 * @return The current texture of the element.

	Texture getTexture();

	/**
	 * Sets the texture of the element.
	 *
	 * @param texture The new texture to apply to the element.
	 * @return The current instance of the element (for method chaining).

	IElement setTexture(Texture texture);
	*/

	INode setChildrenPlacement(Placement placement);
	Placement getChildrenPlacement();

	/**
	 * Gets the element's X coordinate (horizontal position).
	 *
	 * @return The current X position of the element.
	 */
	int getX();

	/**
	 * Gets the element's Y coordinate (vertical position).
	 *
	 * @return The current Y position of the element.
	 */
	int getY();

	/**
	 * Sets the local position of the element at the specified X and Y coordinates.
	 *
	 * @param x The X coordinate to set.
	 * @param y The Y coordinate to set.
	 * @return The current instance of the element.
	 */
	INode setPosition(int x, int y);

	/**
	 * Sets the global position of the element at the specified X and Y coordinates.
	 *
	 * @param gx The global X coordinate to set.
	 * @param gy The global Y coordinate to set.
	 * @return The current instance of the element.
	 */
	INode setGlobalPosition(int gx, int gy);

	/**
	 * Gets the width of the element.
	 *
	 * @return The current width of the element, based on its texture.
	 */
	int getWidth();

	/**
	 * Gets the height of the element.
	 *
	 * @return The current height of the element, based on its texture.
	 */
	int getHeight();

	/**
	 * Gets a list of the child elements associated with this element.
	 *
	 * @return A list of child elements.
	 */
	List<INode> getChildren();

	/**
	 * Adds multiple child elements to this element.
	 *
	 * @param children The child elements to add.
	 * @return The current instance of the element.
	 */
	INode addChildren(INode... children);

	/**
	 * Adds a single child element to this element.
	 *
	 * @param child The child element to add.
	 * @return The current instance of the element.
	 */
	INode addChild(INode child);

	/**
	 * Sets the Minecraft instance for this element.
	 *
	 * @param mc The Minecraft instance to associate with the element.
	 */
	void setMc(Minecraft mc);

	/**
	 * Checks if the element has any dependencies set (e.g., Minecraft instance).
	 *
	 * @return true if the element has dependencies, false otherwise.
	 */
	boolean hasDependency();


	/**
	 * Marks the element as positioned or not.
	 *
	 * @param positioned true if the element is positioned, false otherwise.
	 * @return The current instance of the element.
	 */
	INode setPositioned(boolean positioned);

	/**
	 * Checks if the element has a set position.
	 *
	 * @return true if the element is positioned, false otherwise.
	 */
	boolean isPositioned();

	/**
	 * Gets the group to which the element belongs.
	 *
	 * @return The name of the group to which the element belongs.
	 */
	String getGroup();

	/**
	 * Retrieves the Session Identifier (SID) of the element.
	 *
	 * @return The SID of the element.
	 */
	String getSid();

	/**
	 * Sets the Session Identifier (SID) for the element.
	 *
	 * @param sid The new SID to set.
	 * @return The current instance of the element.
	 */
	INode setSid(String sid);

	/**
	 * Sets the group to which the element belongs.
	 *
	 * @param group The name of the group to assign the element to.
	 * @return The current instance of the element.
	 */
	INode setGroup(String group);

	/**
	 * Gets the element's global Y coordinate.
	 *
	 * @return The global Y coordinate of the element.
	 */
	int getGy();

	/**
	 * Gets the element's global X coordinate.
	 *
	 * @return The global X coordinate of the element.
	 */
	int getGx();

	/**
	 * Retrieves the parent element of this element.
	 *
	 * @return The parent element of this element.
	 */
	INode getParent();

	/**
	 * Sets the parent element for this element.
	 *
	 * @param parent The parent element to assign.
	 */
	void setParent(INode parent);

	/**
	 * Modifies the child elements using a lambda function.
	 *
	 * @param lambda The lambda function to modify the child elements.
	 * @return The current instance of the element.
	 */
	INode modifyChildren(IChildrenLambda lambda);

	/**
	 * Modifies a specific child element at the given index using a lambda function.
	 *
	 * @param index  The index of the child element to modify.
	 * @param lambda The lambda function to modify the child.
	 * @return The current instance of the element.
	 */
	INode modifyChild(int index, IChildLambda lambda);

	boolean hasChildren();


	Map<String, String> getAttributes();

	void setAttributes(Map<String, String> attributes);
}
