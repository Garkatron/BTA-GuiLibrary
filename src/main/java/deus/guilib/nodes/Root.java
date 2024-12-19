package deus.guilib.nodes;

import deus.guilib.nodes.config.Placement;
import deus.guilib.util.rendering.RenderUtils;
import deus.guilib.error.Error;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Root extends RenderUtils implements INode {

	/* Attributes and Styles HashMaps */
	protected Map<String, Object> styles = new HashMap<>();
	protected Map<String, String> attributes = new HashMap<>();

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

	/* Dependencies */
	protected Minecraft mc;

	public Root() {
		mc = Minecraft.getMinecraft(this);
	}

	public Root(Map<String, String> attributes) {
		mc = Minecraft.getMinecraft(this);
		if (attributes.containsKey("id")) {
			this.setSid(attributes.get("id"));
		}
		if (attributes.containsKey("group")) {
			this.setGroup(attributes.get("group"));
		}
		this.attributes = attributes;
	}

	/* Render Methods */
	@Override
	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {}

	protected void drawChild() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}
		for (INode child : children) {
			PlacementHelper.positionChild(child, this);
			child.draw();
		}
	}

	/* Update Methods */
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

	/* Placement Configuration */
	public Placement getChildrenPlacement() {
		return childrenPlacement;
	}

	@Override
	public Placement getSelfPlacement() {
		return null;
	}

	@Override
	public void setSelfPlacement(Placement placement) {}

	public INode setChildrenPlacement(Placement placement) {
		this.childrenPlacement = placement;
		return this;
	}

	/* Getters */
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
	public int getGx() {
		return this.gx;
	}

	@Override
	public int getGy() {
		return this.gy;
	}

	@Override
	public List<INode> getChildren() {
		return children;
	}

	@Override
	public List<INode> getDescendants() {
		return children.stream()
			.flatMap(child -> Stream.concat(Stream.of(child), child.getDescendants().stream()))
			.collect(Collectors.toList());
	}

	@Override
	public String getGroup() {
		return this.attributes.getOrDefault("group", "NO_GROUP");
	}

	@Override
	public String getId() {
		return this.attributes.getOrDefault("id", "NO_ID");
	}

	@Override
	public INode getParent() {
		return this.parent;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	/* Setters */
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
	public INode addChildren(INode... children) {
		for (INode child : children) {
			child.setParent(this);
			this.children.add(child);
		}
		return this;
	}

	@Override
	public INode setGroup(String group) {
		this.attributes.put("group", group);
		return this;
	}

	@Override
	public INode setSid(String sid) {
		this.attributes.put("id", sid);
		return this;
	}

	@Override
	public void setParent(INode parent) {
		this.parent = parent;
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/* Utility Methods */

	protected void updateChildrenPosition() {
		for (INode child : children) {
			child.setGlobalPosition(this.gx + child.getX(), this.gy + child.getY());
		}
	}

	private List<INode> findNodes(Predicate<INode> predicate, INode parent) {
		List<INode> result = new ArrayList<>();
		if (predicate == null) return result;
		Deque<INode> stack = new ArrayDeque<>();
		stack.push(parent);
		while (!stack.isEmpty()) {
			INode current = stack.pop();
			if (predicate.test(current)) {
				result.add(current);
			}
			stack.addAll(current.getChildren());
		}
		return result;
	}

	@Override
	public INode getNodeById(String id) {
		return findNodes(node -> id.equals(node.getId()), this).stream().findFirst().orElse(null);
	}

	@Override
	public List<INode> getNodeByGroup(String group) {
		return findNodes(node -> group.equals(node.getGroup()), this);
	}

	@Override
	public List<INode> getNodeByClass(String className) {
		return findNodes(node -> className.equals(node.getClass().getSimpleName().toLowerCase()), this);
	}
}
