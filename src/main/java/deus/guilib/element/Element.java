package deus.guilib.element;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.interfaces.element.IElement;
import deus.guilib.error.Error;
import deus.guilib.resource.Texture;
import deus.guilib.resource.ThemeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Element extends Gui implements IElement {
	protected Texture texture;
	protected int x, y;
	protected List<IElement> children = new ArrayList<>();
	protected boolean positioned = false;
	protected ThemeManager themeManager = ThemeManager.getInstance();
	protected Minecraft mc;
	protected ElementConfig config;
	protected String sid = "";
	protected String group = "";

	public Element(Texture texture) {
		mc = Minecraft.getMinecraft(this);
		this.texture = texture;
		setConfig(ElementConfig.create());
	}

	public Element() {
		mc = Minecraft.getMinecraft(this);
		setConfig(ElementConfig.create());
	}

	protected void injectDependency() {
		for (IElement child : this.children) {
			if (child != null && !child.hasDependency()) {
				child.setMc(mc);
				if (child instanceof Element) {
					((Element) child).injectDependency();
				} else {
					for (IElement grandChild : child.getChildren()) {
						grandChild.setMc(mc);
					}
				}
			}
		}
	}

	@Override
	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {
		if (mc == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);

		if (!Objects.equals(config.getTheme(), "NONE")) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(config.getTheme()).get(getClass().getSimpleName())));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		}

		GL11.glDisable(GL11.GL_BLEND);
		drawTexturedModalRect(x, y, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());
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

	protected void drawChild() {
		if (mc == null) {
			System.out.println(Error.MISSING_MC);
			return;
		}
		if (!children.isEmpty()) {
			int offsetX = 0;
			int offsetY = 0;

			switch (config.getPlacement()) {
				case CENTER:
					offsetX = getWidth();
					offsetY = getHeight();
					break;

				case TOP:
					offsetX = 0;
					offsetY = -children.get(0).getHeight();
					break;

				case BOTTOM:
					offsetX = 0;
					offsetY = children.get(0).getHeight();
					break;

				case LEFT:
					offsetX = -children.get(0).getWidth();
					offsetY = 0;
					break;

				case RIGHT:
					offsetX = children.get(0).getWidth();
					offsetY = 0;
					break;

				case TOP_LEFT:
					offsetX = -children.get(0).getWidth();
					offsetY = -children.get(0).getHeight();
					break;

				case BOTTOM_LEFT:
					offsetX = -children.get(0).getWidth();
					offsetY = children.get(0).getHeight();
					break;

				case BOTTOM_RIGHT:
					offsetX = children.get(0).getWidth();
					offsetY = children.get(0).getHeight();
					break;

				case TOP_RIGHT:
					offsetX = children.get(0).getWidth();
					offsetY = -children.get(0).getHeight();
					break;

				case NONE:
					offsetX = 0;
					offsetY = 0;
					break;
			}

			if (config.getPlacement() == ChildrenPlacement.LEFT) {
				int xOffset = 0;
				for (IElement child : children) {
					child.setX(x - xOffset);
					child.setY(y);
					child.draw();
					xOffset += child.getWidth();
				}
			} else if (config.getPlacement() == ChildrenPlacement.RIGHT) {
				int xOffset = getWidth();
				for (IElement child : children) {
					child.setX(x + xOffset);
					child.setY(y);
					child.draw();
					xOffset += child.getWidth();
				}
			} else if (config.getPlacement() == ChildrenPlacement.CENTER) {
				for (IElement child : children) {
					int xOffset = (getWidth() - child.getWidth()) / 2;
					int yOffset = (getHeight() - child.getHeight()) / 2;
					child.setX(x + xOffset);
					child.setY(y + yOffset);
					child.draw();
				}
			} else {
				for (IElement child : children) {
					int childX = x + offsetX;
					int childY = y + offsetY;
					child.setX(childX);
					child.setY(childY);
					child.draw();
					if (config.getPlacement() == ChildrenPlacement.TOP || config.getPlacement() == ChildrenPlacement.BOTTOM) {
						offsetY += child.getHeight();
					} else if (config.getPlacement() == ChildrenPlacement.LEFT || config.getPlacement() == ChildrenPlacement.RIGHT) {
						offsetX += child.getWidth();
					}
				}
			}
		}
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
	public int getY() {
		return y;
	}

	@Override
	public IElement setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public IElement setX(int x) {
		this.x = x;
		return this;
	}

	@Override
	public ElementConfig getConfig() {
		return config;
	}

	@Override
	public void setConfig(ElementConfig config) {
		this.config = config;
	}

	@Override
	public IElement config(ElementConfig elementConfig) {
		this.config = elementConfig;
		return this;
	}

	@Override
	public List<IElement> getChildren() {
		return children;
	}

	@Override
	public IElement setChildren(IElement... children) {
		this.children = new ArrayList<>(Arrays.asList(children));
		injectDependency();
		return this;
	}

	@Override
	public IElement addChildren(IElement... children) {
		this.children.addAll(Arrays.asList(children));
		injectDependency();
		return this;
	}

	@Override
	public int getHeight() {
		return texture.getHeight();
	}

	@Override
	public int getWidth() {
		return texture.getWidth();
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
	public IElement setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getSid() {
		return sid;
	}

	@Override
	public IElement setSid(String sid) {
		this.sid = sid;
		return this;
	}

	@Override
	public IElement setGroup(String group) {
		this.group = group;
		return this;
	}
}



