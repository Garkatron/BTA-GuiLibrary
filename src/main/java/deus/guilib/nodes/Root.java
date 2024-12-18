package deus.guilib.nodes;

import deus.guilib.error.Error;
import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Placement;
import deus.guilib.util.math.PlacementHelper;
import deus.guilib.util.rendering.RenderUtils;
import net.minecraft.client.Minecraft;

import java.util.*;

public class Root extends RenderUtils implements INode {

	/* Attributes and Styles HashMaps */
	protected Map<String, Object> styles = new HashMap<>();  // ! Here is your cache, @Kheppanant Khepnacious Kheppery :)
	protected Map<String, String> attributes = new HashMap<>(); // ! Here is your cache, @Kheppanant Khepnacious Kheppery x2  ::))

	/* Position */
	protected int x, y; // Local coordinates
	protected int gx, gy; // Global coordinates
	protected boolean positioned = false;

	/* Sizing */
	protected int width = 20;
	protected int height = 20;

	/* Parent & Children */
	protected List<INode> children = new ArrayList<>();
	protected INode parent;

	/* Config */
	protected Placement childrenPlacement = Placement.NONE;

	/* Signals */
	protected Signal<List<INode>> currentChildrenSignal = new Signal<>();
	protected Signal<INode[]> childRemovedSignal = new Signal<>();

	protected Signal<Map<String, Object>> updateStylesSignal = new Signal<>();
	protected Signal<Boolean> needsUpdate = new Signal<>();

	/* Dependencies */
	protected Minecraft mc;

	/* Constructors */
	public Root() {
		mc = Minecraft.getMinecraft(this);
		connectSignals();
	}

	public Root(Map<String, String> attributes) {
		this();

		if (attributes.containsKey("id")) {
			this.setSid(attributes.get("id"));
		}

		if (attributes.containsKey("group")) {
			this.setGroup(attributes.get("group"));
		}
		this.attributes = attributes;
	}

	/* Auxiliary methods */

	protected void connectSignals() {
		// ! It probably needs optimization

		// ? Useless
		childRemovedSignal.connect((r, children) -> {
			for (INode child : children) {
				PlacementHelper.positionChild(child, this);
			}
		});

		// ? ...
		currentChildrenSignal.connect((r, children) -> {
			for (INode child : children) {
				PlacementHelper.positionChild(child, this);
			}
		});
	}

	/* Drawing methods */

	@Override
	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {
	}

	protected void drawChild() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		for (INode child : children) {
			child.draw();
		}
	}

	/* Updating methods */

	@Override
	public void update() {
		updateIt();
		updateChildren();
	}

	protected void updateIt() {}

	protected void updateChildren() {
		for (INode child : children) {
			child.update();
		}
	}

	/* Basic methods */

	@Override
	public INode addChildren(INode... children) {
		for (INode child : children) {
			child.setParent(this);
			this.children.add(child);
		}
		this.currentChildrenSignal.emit(this.children);

		return this;
	}

	@Override
	public void removeChildren(INode... children) {
		for (INode child : children) {
			this.children.remove(child);
		}
		this.childRemovedSignal.emit(children);

	}

	protected void updateChildrenPosition() {
		for (INode child : children) {
			child.setGlobalPosition(this.gx + child.getGx(), this.gy + child.getGy());
		}
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/* Setters */

	public INode setChildrenPlacement(Placement placement) {
		this.childrenPlacement = placement;
		return this;
	}

	@Override
	public INode setPosition(int x, int y) {

		this.x = x;
		this.y = y;

		this.gx += this.x;
		this.gy += this.y;

		updateChildrenPosition();
		return this;
	}

	@Override
	public INode setGlobalPosition(int gx, int gy) {
		this.gx = gx;
		this.gy = gy;
		updateChildrenPosition();

		return this;
	}

	@Override
	public INode setWidth(int width) {
		this.width = width;
		return this;
	}

	@Override
	public INode setHeight(int height) {
		this.height = height;
		return this;
	}

	@Override
	public INode setSid(String sid) {
		this.attributes.put("id", sid);
		return this;
	}

	public Placement getChildrenPlacement() {
		return childrenPlacement;
	}

	@Override
	public Placement getSelfPlacement() {
		return null;
	}

	/* Getters */

	@Override
	public void setSelfPlacement(Placement placement) {
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public List<INode> getChildren() {
		return children;
	}

	@Override
	public List<INode> getDescendants() {

		List<INode> descendants = new ArrayList<>();

		for (INode child : children) {
			descendants.add(child);

			if (child.hasChildren()) {
				descendants.addAll(child.getDescendants());
			}
		}

		return descendants;
	}

	@Override
	public int getGy() {
		return this.gy;
	}

	@Override
	public int getGx() {
		return this.gx;
	}

	@Override
	public INode getParent() {
		return this.parent;
	}

	@Override
	public void setParent(INode parent) {
		this.parent = parent;
	}

	@Override
	public String getGroup() {
		if (this.attributes.containsKey("group")) {
			return this.attributes.get("group");
		}
		return "NO_GROUP";
	}

	@Override
	public INode setGroup(String group) {
		this.attributes.put("id", group);
		return this;
	}

	@Override
	public String getId() {
		if (this.attributes.containsKey("id")) {
			return this.attributes.get("id");
		}
		return "NO_ID";
	}

	@Override
	public INode getNodeById(String id) {
		return getChildById(id, this);
	}

	@Override
	public List<INode> getNodeByGroup(String group) {
		return getChildByGroup(group, this);
	}

	@Override
	public List<INode> getNodeByClass(String className) {
		return getChildByClassName(className, this);
	}

	private INode getChildById(String id, INode parent) {
		if (id == null || id.isEmpty()) {
			return null;
		}

		Stack<INode> stack = new Stack<>();
		stack.push(parent);

		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			if (id.equals(currentNode.getId())) {
				return currentNode;
			}

			if (currentNode.hasChildren()) {
				for (INode child : currentNode.getChildren()) {
					stack.push(child);
				}
			}
		}
		return null;
	}

	private List<INode> getChildByClassName(String className, INode parent) {
		List<INode> nodes = new ArrayList<>();

		if (className == null || className.isEmpty()) {
			return nodes;
		}

		LinkedList<INode> stack = new LinkedList<>();
		stack.push(parent);

		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			if (className.equals(currentNode.getClass().getSimpleName().toLowerCase())) {
				nodes.add(currentNode);
			}

			if (currentNode.hasChildren()) {
				stack.addAll(currentNode.getChildren());
			}
		}

		return nodes;
	}

	private List<INode> getChildByGroup(String group, INode parent) {
		List<INode> nodes = new ArrayList<>();

		if (group == null || group.isEmpty()) {
			return nodes;
		}

		LinkedList<INode> stack = new LinkedList<>();
		stack.push(parent);

		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			if (group.equals(currentNode.getGroup())) {
				nodes.add(currentNode);
			}

			if (currentNode.hasChildren()) {
				stack.addAll(currentNode.getChildren());
			}
		}

		return nodes;
	}
}
