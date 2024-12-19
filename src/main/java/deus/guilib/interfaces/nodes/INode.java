package deus.guilib.interfaces.nodes;

import deus.guilib.nodes.config.Placement;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Map;

public interface INode extends IDrawable, IUpdatable {

	/* Placement Configuration */

	/**
	 * Sets the placement configuration for child elements.
	 *
	 * @param placement The placement strategy for child elements.
	 * @return The current instance of the element (for method chaining).
	 */
	INode setChildrenPlacement(Placement placement);

	/**
	 * Retrieves the current placement configuration for child elements.
	 *
	 * @return The current child placement configuration.
	 */
	Placement getChildrenPlacement();

	/**
	 * Retrieves the element's self-placement configuration.
	 *
	 * @return The current self-placement configuration.
	 */
	Placement getSelfPlacement();

	/**
	 * Sets the element's self-placement configuration.
	 *
	 * @param placement The self-placement configuration to set.
	 */
	void setSelfPlacement(Placement placement);

	/* Position Management */

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
	 * Gets the element's global X coordinate.
	 *
	 * @return The global X coordinate of the element.
	 */
	int getGx();

	/**
	 * Gets the element's global Y coordinate.
	 *
	 * @return The global Y coordinate of the element.
	 */
	int getGy();

	/* Size Management */

	/**
	 * Gets the width of the element.
	 *
	 * @return The current width of the element.
	 */
	int getWidth();

	/**
	 * Gets the height of the element.
	 *
	 * @return The current height of the element.
	 */
	int getHeight();

	/**
	 * Sets the width of the element.
	 *
	 * @param width The width to set.
	 * @return The current instance of the element.
	 */
	INode setWidth(int width);

	/**
	 * Sets the height of the element.
	 *
	 * @param height The height to set.
	 * @return The current instance of the element.
	 */
	INode setHeight(int height);

	/* Hierarchy Management */

	/**
	 * Gets the parent element of this element.
	 *
	 * @return The parent element.
	 */
	INode getParent();

	/**
	 * Sets the parent element for this element.
	 *
	 * @param parent The parent element to set.
	 */
	void setParent(INode parent);

	/**
	 * Checks if the element has child elements.
	 *
	 * @return True if the element has children, false otherwise.
	 */
	boolean hasChildren();

	/**
	 * Gets the list of child elements associated with this element.
	 *
	 * @return A list of child elements.
	 */
	List<INode> getChildren();

	/**
	 * Gets the list of all descendant elements associated with this element.
	 *
	 * @return A list of descendant elements.
	 */
	List<INode> getDescendants();

	/**
	 * Adds multiple child elements to this element.
	 *
	 * @param children The child elements to add.
	 * @return The current instance of the element.
	 */
	INode addChildren(INode... children);

	/* Attributes Management */

	/**
	 * Gets the group to which the element belongs.
	 *
	 * @return The group name.
	 */
	String getGroup();

	/**
	 * Sets the group to which the element belongs.
	 *
	 * @param group The group name to set.
	 * @return The current instance of the element.
	 */
	INode setGroup(String group);

	/**
	 * Gets the Session Identifier (SID) of the element.
	 *
	 * @return The SID of the element.
	 */
	String getId();

	/**
	 * Sets the Session Identifier (SID) for the element.
	 *
	 * @param sid The SID to set.
	 * @return The current instance of the element.
	 */
	INode setSid(String sid);

	/**
	 * Gets the attributes of the element.
	 *
	 * @return A map of the attributes.
	 */
	Map<String, String> getAttributes();

	/**
	 * Sets the attributes for the element.
	 *
	 * @param attributes A map of attributes to set.
	 */
	void setAttributes(Map<String, String> attributes);

	/* Node Search Methods */

	/**
	 * Finds a node by its ID.
	 *
	 * @param id The ID to search for.
	 * @return The node with the matching ID, or null if not found.
	 */
	INode getNodeById(String id);

	/**
	 * Finds nodes by their group name.
	 *
	 * @param group The group name to search for.
	 * @return A list of nodes in the matching group.
	 */
	List<INode> getNodeByGroup(String group);

	/**
	 * Finds nodes by their class name.
	 *
	 * @param className The class name to search for.
	 * @return A list of nodes with the matching class name.
	 */
	List<INode> getNodeByClass(String className);
}
