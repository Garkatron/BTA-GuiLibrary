package deus.guilib.element;

import deus.guilib.GuiLib;
import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.stylesystem.BorderStyle;
import deus.guilib.element.stylesystem.StyleParser;
import deus.guilib.element.stylesystem.YAMLProcessor;
import deus.guilib.element.util.AdvancedGui;
import deus.guilib.error.Error;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.IElementConfigLambda;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.interfaces.element.IStylable;
import deus.guilib.resource.Texture;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Element extends AdvancedGui implements IElement, IStylable {

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
	protected List<IElement> children = new ArrayList<>();
	protected IElement parent;
	protected Placement placement = Placement.NONE;

	/* Identifiers */
	protected String sid = "";
	protected String group = "";

	/* Config */
	protected ElementConfig config = ElementConfig.create();

	/* Dependencies */
	protected Minecraft mc;

	public Element() {
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

		if (styles.containsKey("BackgroundColor")) {
			this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB((String) styles.get("BackgroundColor")));
		}

		if (styles.containsKey("BackgroundTexture")) {
			((Texture)  styles.get("BackgroundTexture")).draw(mc, gx, gy, width, height);
		}

		if (styles.containsKey("Border")) {

			BorderStyle borderStyle = StyleParser.parseBorder((String) styles.get("Border"));

			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
			drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
			drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
			drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha

			if (styles.containsKey("border-top")) {
				BorderStyle borderTopStyle = StyleParser.parseBorder((String) styles.get("Border-top"));
				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
			}

			if (styles.containsKey("border-bottom")) {
				BorderStyle borderBottomStyle = StyleParser.parseBorder((String) styles.get("Border-bottom"));
				drawRect(this.gx, this.gy, this.gx + borderBottomStyle.width, this.gy + getHeight(), borderBottomStyle.color); // Izquierda
			}

			if (styles.containsKey("border-left")) {
				BorderStyle borderLeftStyle = StyleParser.parseBorder((String) styles.get("Border-left"));
				drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
			}

			if (styles.containsKey("border-right")) {
				BorderStyle borderRightStyle = StyleParser.parseBorder((String) styles.get("Border-right"));
				drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
			}

		}

		if (styles.containsKey("Width")) {
			this.width = StyleParser.parsePixels((String) styles.get("Width"));
		}

		if (styles.containsKey("Height")) {
			this.height = StyleParser.parsePixels((String) styles.get("Height"));
		}

		//texture.draw(mc, gx, gy);

	}


	protected void drawChild() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		for (IElement child : children) {

			if (this.styles.containsKey("Disposition")) {
				String disposition = (String) styles.get("Disposition");
				if (Objects.equals(disposition, "manual")) {

					continue;
				} else if (Objects.equals(disposition, "parent")) {
					PlacementHelper.positionChild(child, this);
				}
			}

			child.draw();
		}
	}

	@Override
	public void applyStyle(Map<String, Object> styles) {

		this.styles = styles;
	}

	@Override
	public IElement setPositioned(boolean positioned) {
		this.positioned = positioned;
		return this;
	}

	@Override
	public boolean isPositioned() {
		return positioned;
	}

	/*
	@Override
	public Texture getTexture() {
		return this.texture;
	}
	*/

	/*
	@Override
	public IElement setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	*/

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public IElement setPosition(int x, int y) {

		if (config.isCentered()) {
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
	public IElement setPosition(Placement placement) {
		this.placement = placement;
		return this;
	}

	@Override
	public Placement getPlacement() {
		return placement;
	}

	@Override
	public IElement setGlobalPosition(int gx, int gy) {
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
	public ElementConfig getConfig() {
		return this.config;
	}

	@Override
	public IElement config(IElementConfigLambda<ElementConfig> configLambda) {
		configLambda.apply(config);
		return this;
	}

	@Override
	public List<IElement> getChildren() {
		return children;
	}

	@Override
	public IElement addChildren(IElement... children) {
		for (IElement child : children) {
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
	public IElement setGroup(String group) {
		this.group = group;
		return this;
	}

	@Override
	public String getSid() {
		return this.sid;
	}

	@Override
	public IElement setSid(String sid) {
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
	public IElement getParent() {
		return this.parent;
	}

	@Override
	public void setParent(IElement parent) {
		this.parent = parent;
	}

	@Override
	public IElement modifyChildren(IChildrenLambda lambda) {
		lambda.apply(children);
		return this;
	}

	@Override
	public IElement modifyChild(int index, IChildLambda lambda) {
		lambda.apply(children.get(index));
		return this;
	}

	@Override
	public IElement getElementWithSid(String sid) {
		return children.stream()
			.filter(c -> sid.equals(c.getSid()))
			.findFirst()
			.orElse(null);
	}

	@Override
	public List<IElement> getElementsInGroup(String group) {
		return children.stream()
			.filter(c -> group.equals(c.getGroup()))
			.collect(Collectors.toList());
	}

	protected void updateChildrenPosition() {
		for (IElement child : children) {
			child.setGlobalPosition(this.gx + child.getX(), this.gy + child.getY());
		}
	}

	@Override
	public IElement addChild(IElement child) {
		child.setParent(this);
		this.children.add(child);
		return this;
	}
}
