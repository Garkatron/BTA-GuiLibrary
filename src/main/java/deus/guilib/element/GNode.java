package deus.guilib.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.stylesystem.BorderStyle;
import deus.guilib.element.stylesystem.StyleParser;
import deus.guilib.element.util.AdvancedGui;
import deus.guilib.error.Error;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.element.INode;
import deus.guilib.interfaces.element.IStylable;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.stream.Collectors;

public class GNode extends AdvancedGui implements INode, IStylable {

	//protected Texture texture;
	protected Map<String, Object> styles = new HashMap<>();

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
	private Placement childrenPlacement = Placement.NONE;


	/* Dependencies */
	protected Minecraft mc;

	public GNode() {
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

	/*public Element() {
		mc = Minecraft.getMinecraft(this);
	}*/

	@Override
	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		if (styles.containsKey("localx")) {
			this.x = (int) styles.get("localx");
		}

		if (styles.containsKey("localy")) {
			this.y = (int) styles.get("localy");
		}

		if (styles.containsKey("globalx")) {
			this.gx = (int) styles.get("globalx");
		}

		if (styles.containsKey("globaly")) {
			this.gy = (int) styles.get("globaly");
		}


		if (styles.containsKey("backgroundColor")) {
			this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB((String) styles.get("backgroundColor")));
		}

		if (styles.containsKey("backgroundTexture")) {
			//Texture t = new Texture((String) styles.get("backgroundTexture"), width, height);
			//t.draw(mc, gx, gy, width, height);
		}

		if (styles.containsKey("border")) {

			BorderStyle borderStyle = StyleParser.parseBorder((String) styles.get("border"));

			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
			drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
			drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
			drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha

			if (styles.containsKey("border-top")) {
				BorderStyle borderTopStyle = StyleParser.parseBorder((String) styles.get("border-top"));
				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
			}

			if (styles.containsKey("border-bottom")) {
				BorderStyle borderBottomStyle = StyleParser.parseBorder((String) styles.get("border-bottom"));
				drawRect(this.gx, this.gy, this.gx + borderBottomStyle.width, this.gy + getHeight(), borderBottomStyle.color); // Izquierda
			}

			if (styles.containsKey("border-left")) {
				BorderStyle borderLeftStyle = StyleParser.parseBorder((String) styles.get("border-left"));
				drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
			}

			if (styles.containsKey("border-right")) {
				BorderStyle borderRightStyle = StyleParser.parseBorder((String) styles.get("border-right"));
				drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
			}

		}

		if (styles.containsKey("width")) {
			this.width = StyleParser.parsePixels((String) styles.get("width"));
		}

		if (styles.containsKey("height")) {
			this.height = StyleParser.parsePixels((String) styles.get("height"));
		}


		if (styles.containsKey("childrenPlacement")) {
			this.childrenPlacement = Placement.valueOf((String) styles.get("childrenPlacement"));
		}

		//texture.draw(mc, gx, gy);

	}


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
	public void applyStyle(Map<String, Object> styles) {

		this.styles = styles;
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

	@Override
	public INode getElementWithSid(String sid) {
		return children.stream()
			.filter(c -> sid.equals(c.getSid()))
			.findFirst()
			.orElse(null);
	}

	@Override
	public List<INode> getElementsInGroup(String group) {
		return children.stream()
			.filter(c -> group.equals(c.getGroup()))
			.collect(Collectors.toList());
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
}
