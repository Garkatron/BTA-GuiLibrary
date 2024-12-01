package deus.guilib.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.util.AdvancedGui;
import deus.guilib.error.Error;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.element.INode;
import deus.guilib.interfaces.element.IRootNode;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;

import java.util.*;

public class Root extends AdvancedGui implements INode, IRootNode {

	//protected Texture texture;
	protected Map<String, Object> styles = new HashMap<>();
	private Map<String, String> attributes = new HashMap<>();

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

	/* Identifiers */
	protected String sid = "";
	protected String group = "";

	/* Config */
	protected Placement childrenPlacement = Placement.NONE;


	/* Dependencies */
	protected Minecraft mc;

	public Root() {
		mc = Minecraft.getMinecraft(this);

		/*
		this.styles.put("BackgroundColor","#FE9900");
		//this.styles.put("BackgroundTexture", new Texture("assets/textures/gui/Button.png", 20, 20));
		this.styles.put("Width","20px");
		this.styles.put("Height","2000px");
		this.styles.put("Disposition","manual");
		this.styles.put("Border","2px #E9C46A");
		*/
		//this.texture = texture;
	}

	public Root(Map<String, String> attributes) {
		mc = Minecraft.getMinecraft(this);
		if (attributes.containsKey("id")) {
			this.setSid(attributes.get("id"));
			//attributes.remove("id");
		}

		if (attributes.containsKey("group")) {
			this.setGroup(attributes.get("group"));
			//attributes.remove("group");
		}

		this.attributes = attributes;


	}

	/*public Element() {
		mc = Minecraft.getMinecraft(this);
	}*/


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
		// Validación temprana: si el ID está vacío, devolver null inmediatamente
		if (id == null || id.isEmpty()) {
			return null;
		}

		// Utilizar un stack para evitar el desbordamiento de pila en la recursión
		Stack<INode> stack = new Stack<>();
		stack.push(parent);

		// Iterar de manera iterativa usando un stack
		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			// Verificar si el ID coincide con el actual
			if (id.equals(currentNode.getSid())) {
				return currentNode;
			}

			// Si el nodo tiene hijos, agregarlos al stack
			if (currentNode.hasChildren()) {
				for (INode child : currentNode.getChildren()) {
					stack.push(child);
				}
			}
		}
		return null; // No se encontró el nodo con ese ID
	}

	private List<INode> getChildByClassName(String className, INode parent) {
		List<INode> nodes = new ArrayList<>();

		// Validación temprana: si la clase está vacía o nula, devolver lista vacía
		if (className == null || className.isEmpty()) {
			return nodes;
		}

		// Utilizar un stack para evitar desbordamiento de pila en la recursión
		LinkedList<INode> stack = new LinkedList<>();
		stack.push(parent);

		// Iterar de manera iterativa usando un stack
		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			// Verificar si el nombre de la clase coincide con el nodo actual
			if (className.equals(currentNode.getClass().getSimpleName())) {
				nodes.add(currentNode);
			}

			// Si el nodo tiene hijos, agregarlos al stack
			if (currentNode.hasChildren()) {
				stack.addAll(currentNode.getChildren());
			}
		}

		return nodes; // Regresar lista con nodos encontrados
	}

	private List<INode> getChildByGroup(String group, INode parent) {
		List<INode> nodes = new ArrayList<>();

		// Validación temprana: si el grupo está vacío o nulo, devolver lista vacía
		if (group == null || group.isEmpty()) {
			return nodes;
		}

		// Utilizar un stack para evitar desbordamiento de pila en la recursión
		LinkedList<INode> stack = new LinkedList<>();
		stack.push(parent);

		// Iterar de manera iterativa usando un stack
		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			// Verificar si el grupo coincide con el nodo actual
			if (group.equals(currentNode.getGroup())) {
				nodes.add(currentNode);
			}

			// Si el nodo tiene hijos, agregarlos al stack
			if (currentNode.hasChildren()) {
				stack.addAll(currentNode.getChildren());
			}
		}

		return nodes; // Regresar lista con nodos encontrados
	}



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

	/**
	 * Returns the current {@link Placement} value.
	 *
	 * @return The current {@code ChildrenPlacement}.
	 */
	public Placement getChildrenPlacement() {
		return childrenPlacement;
	}

	/**
	 * Sets the placement configuration.
	 *
	 * @param placement The {@link Placement} value to set.
	 * @return The current instance of the config for method chaining.
	 */
	public INode setChildrenPlacement(Placement placement) {
		this.childrenPlacement = placement;
		return this;
	}


	@Override
	public INode setPositioned(boolean positioned) {
		this.positioned = positioned;
		return this;
	}

	@Override
	public boolean isPositioned() {
		return positioned;
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
	public INode setPosition(int x, int y) {

		if (false) {
			this.x = getCenteredX(x);
			this.y = getCenteredY(y);
		} else {
			this.x = x;
			this.y = y;
		}

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

	private int getCenteredX(int x) {
		return x - (getWidth() / 2);
	}

	private int getCenteredY(int y) {
		return y - (getHeight() / 2);
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
	public INode addChildren(INode... children) {
		for (INode child : children) {
			child.setParent(this);
			this.children.add(child);
		}
		return this;
	}

	@Override
	public void setMc(Minecraft mc) {
		this.mc = mc;
	}

	@Override
	public boolean hasDependency() {
		return mc != null;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public INode setGroup(String group) {
		this.group = group;
		return this;
	}

	@Override
	public String getSid() {
		return this.sid;
	}

	@Override
	public INode setSid(String sid) {
		this.sid = sid;
		return this;
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
	public INode modifyChildren(IChildrenLambda lambda) {
		lambda.apply(children);
		return this;
	}

	@Override
	public INode modifyChild(int index, IChildLambda lambda) {
		lambda.apply(children.get(index));
		return this;
	}

	protected void updateChildrenPosition() {
		for (INode child : children) {
			child.setGlobalPosition(this.gx + child.getX(), this.gy + child.getY());
		}
	}

	@Override
	public INode addChild(INode child) {
		child.setParent(this);
		this.children.add(child);
		return this;
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
}
