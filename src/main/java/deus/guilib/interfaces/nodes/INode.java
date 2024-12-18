package deus.guilib.interfaces.nodes;

import deus.guilib.nodes.config.Placement;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;

import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Map;

/**
 * Represents a graphical element within the Graphical User Interface (GUI).
 * Defines methods to manage textures, positioning, configuration, and child elements.
 */
public interface INode extends IDrawable, IUpdatable {

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

	Placement getSelfPlacement();
	void setSelfPlacement(Placement placement);

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

	INode setWidth(int width);

	INode setHeight(int height);

	/**
	 * Gets a list of the child elements associated with this element.
	 *
	 * @return A list of child elements.
	 */
	List<INode> getChildren();

	List<INode> getDescendants();

	/**
	 * Adds multiple child elements to this element.
	 *
	 * @param children The child elements to add.
	 * @return The current instance of the element.
	 */
	INode addChildren(INode... children);
	void removeChildren(INode... children);


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
	String getId();

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

	boolean hasChildren();


	Map<String, String> getAttributes();

	void setAttributes(Map<String, String> attributes);

	INode getNodeById(String id);
	List<INode> getNodeByGroup(String group);
	List<INode> getNodeByClass(String className);


}
