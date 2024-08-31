package deus.guilib.rendering.base;

import deus.guilib.rendering.base.interfaces.IDadElement;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.Placement;
import deus.guilib.rendering.resource.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Element implements IDadElement {
	protected Texture texture;
	protected int x, y;
	protected List<Element> children = new ArrayList<>();

	protected Gui gui;
	protected Minecraft mc;
	protected ElementConfig config; // ElementConfig object

	private int childX = x;
	private int childY = y;

	// Constructor principal con ElementConfig
	public Element(Texture texture, int x, int y, ElementConfig config, Element... children) {
		this.gui = new Gui();
		this.mc = Minecraft.getMinecraft(this);
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.config = config; // Asegúrate de que el placement se establece correctamente
		this.children = new ArrayList<>(Arrays.asList(children)); // Inicializa como lista mutable

		// Configura los hijos si hay alguno
		injectDependency();
	}

	protected void injectDependency() {
		// Inyectar dependencias en los hijos directos
		for (Element child : this.children) {
			if (child != null && !child.hasDependency()) {
				child.setMc(mc);
				child.setGui(gui);

				// Llamar a injectDependency de forma recursiva para inyectar dependencias en los hijos del hijo
				child.injectDependency();
			}
		}
	}

	// Otros constructores que llaman al constructor principal con valores predeterminados
	public Element(Texture texture, int x, int y) {
		this(texture, x, y, new ElementConfig(true, Placement.NONE)); // Configuración predeterminada
	}

	public Element(Texture texture, int x, int y, Placement placement) {
		this(texture, x, y, new ElementConfig(true, Placement.NONE)); // Configuración con placement
	}

	public Element(Texture texture, int x, int y, Element... children) {
		this(texture, x, y, new ElementConfig(true, Placement.NONE), children); // Configuración predeterminada
	}


	public Element(Texture texture, Element... children) {
		this(texture , 0, 0, new ElementConfig(true, Placement.NONE), children); // Configuración predeterminada
	}

	public Element(Texture texture) {
		this(texture , 0, 0, new ElementConfig(true, Placement.NONE)); // Configuración predeterminada
	}

	public void draw() {
		drawIt();
		drawChild();
	}

	protected void drawIt() {
		if (mc==null || gui==null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(config.getTheme().getProperties().get(getClass().getSimpleName())));
		GL11.glDisable(GL11.GL_BLEND);
		gui.drawTexturedModalRect(x, y, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());

	}

	protected void drawChild() {
		if (mc==null || gui==null) {
			System.out.println("Error on drawChild, [Minecraft dependency] or [Gui dependency] are [null].");
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
					offsetX = 0;

					break;
			}

			if (config.getPlacement() == Placement.LEFT) {
				int xOffset = 0;
				for (Element child : children) {
					child.setX(x - xOffset);
					child.setY(y);
					child.draw();
					xOffset += child.getWidth();

				}
			} else if (config.getPlacement() == Placement.RIGHT) {
				int xOffset = getWidth();

				for (Element child : children) {
					child.setX(x + xOffset);
					child.setY(y);
					child.draw();
					xOffset += child.getWidth();

				}
			} else if (config.getPlacement() == Placement.CENTER) {

				for (Element child : children) {
					int xOffset = (getWidth() - child.getWidth()) / 2;
					int yOffset = (getHeight() - child.getHeight()) / 2;

					child.setX(x + xOffset);
					child.setY(y + yOffset);
					child.draw();

				}
			} else {
				for (Element child : children) {
					int childX = x + offsetX;
					int childY = y + offsetY;

					child.setX(childX);
					child.setY(childY);
					child.draw();

					if (config.getPlacement() == Placement.TOP || config.getPlacement() == Placement.BOTTOM) {
						offsetY += child.getHeight();
					} else if (config.getPlacement() == Placement.LEFT || config.getPlacement() == Placement.RIGHT) {
						offsetX += child.getWidth();
					}
				}
			}
		}
	}


	public Texture getTexture() {
		return this.texture;
	}

	public Element setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	public int getY() {
		return y;
	}

	public Element setY(int y) {
		this.y = y;
		return this;
	}

	public int getX() {
		return x;
	}

	public Element setX(int x) {
		this.x = x;
		return this;
	}

	public ElementConfig getConfig() {
		return config;
	}

	public void setConfig(ElementConfig config) {
		this.config = config;
	}

	public Element config(ElementConfig elementConfig) {
		this.config = elementConfig;

		return this;
	}

	@Override
	public List<Element> getChildren() {
		return children;
	}

	@Override
	public Element setChildren(Element... children) {
		this.children = new ArrayList<>(Arrays.asList(children)); // Cambiar a lista mutable
		injectDependency();
		return this;
	}

	@Override
	public Element addChildren(Element... children) {
		this.children.addAll(Arrays.asList(children)); // Agregar a la lista mutable
		injectDependency();
		return this;
	}

	public int getHeight() {
		return texture.getHeight();
	}
	public int getWidth() {
		return texture.getWidth();
	}

	public void setMc(Minecraft mc) {
		this.mc = mc;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	protected boolean hasDependency() {
		return gui!=null && mc!=null;
	}
}
