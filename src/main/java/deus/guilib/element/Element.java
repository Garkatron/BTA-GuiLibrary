package deus.guilib.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.error.Error;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.IConfigLambda;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.resource.Texture;
import deus.guilib.resource.ThemeManager;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static deus.guilib.util.Utils.print;

public abstract class Element extends Gui implements IElement {
	protected Texture texture;
	protected int x, y; // Local coordinates
	protected int gx, gy; // Global coordinates
	protected List<IElement> children = new ArrayList<>();
	protected boolean positioned = false;
	protected ThemeManager themeManager = ThemeManager.getInstance();
	protected Minecraft mc;
	protected ElementConfig config = ElementConfig.create();
	protected String sid = "";
	protected String group = "";
	protected IElement parent;
	protected Placement placement = Placement.NONE;

	public Element(Texture texture) {
		mc = Minecraft.getMinecraft(this);
		this.texture = texture;
	}

	public Element() {
		mc = Minecraft.getMinecraft(this);
	}

	@Override
	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!Objects.equals(config.getTheme(), "NONE")) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(config.getTheme()).get(getClass().getSimpleName())));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		}

		GL11.glDisable(GL11.GL_BLEND);
		drawTexturedModalRect(gx, gy, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());

	}

	protected void drawChild() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		for (IElement child : children) {

			if (!child.getConfig().isIgnoredParentPlacement() & parent != null) {

				PlacementHelper.positionChild(child, parent);
			}

			child.draw();
		}
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

	@Override
	public Texture getTexture() {
		return this.texture;
	}

	@Override
	public IElement setTexture(Texture texture) {
		this.texture = texture;
		return this;
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
	public IElement setPosition(int x, int y) {

		if (config.isCentered()) {
			this.x = x - getWidth()/2;
			this.y = y - getHeight()/2;
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
		if (config.isCentered()) {
			this.gx = gx - getWidth()/2;
			this.gy = gy - getHeight()/2;
		} else {
			this.gx = gx;
			this.gy = gy;
		}
		return this;
	}




	@Override
	public int getWidth() {
		return texture.getWidth();
	}

	@Override
	public int getHeight() {
		return texture.getHeight();
	}

	@Override
	public ElementConfig getConfig() {
		return this.config;
	}

	@Override
	public IElement config(IConfigLambda<ElementConfig> configLambda) {
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
}
