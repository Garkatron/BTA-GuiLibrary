package deus.guilib.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.interfaces.IChildLambda;
import deus.guilib.interfaces.IChildrenLambda;
import deus.guilib.interfaces.IConfigLambda;
import deus.guilib.interfaces.ILambda;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.error.Error;
import deus.guilib.resource.Texture;
import deus.guilib.resource.ThemeManager;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Element extends Gui implements IElement {
	protected Texture texture;
	protected int x, y;
	protected int gx, gy;
	protected List<IElement> children = new ArrayList<>();
	protected boolean positioned = false;
	protected ThemeManager themeManager = ThemeManager.getInstance();
	protected Minecraft mc;
	protected ElementConfig config = ElementConfig.create();
	protected String sid = "";
	protected String group = "";
	protected IElement parent;

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
		if (parent != null) {
			// Dibujar usando las coordenadas globales gx y gy
			drawTexturedModalRect(gx, gy, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());
		} else {
			// Dibujar usando las coordenadas locales x y y
			drawTexturedModalRect(x, y, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());
		}	}

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

			for (IElement child : children) {
				if (!child.getConfig().isIgnoreFatherPlacement()) {

					PlacementHelper.positionChild(child, child.getParent(), getWidth(), getHeight());
				}
				child.draw();
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
	public int getGx() {
		if (this.parent instanceof IElement) {
			// La posición global es la suma de la posición global del padre y la posición local del nodo actual
			return this.parent.getGx() + this.x;
		}
		// Si no tiene padre, la posición global es la misma que la local
		return this.x;
	}

	@Override
	public IElement setGx(int gx) {
		if (this.parent instanceof IElement) {
			// Ajustamos la posición local según la posición global menos la global del padre
			this.x = gx - this.parent.getGx();
		} else {
			// Si no tiene padre, la posición global es directamente la local
			this.x = gx;
		}
		return this;
	}

	@Override
	public int getGy() {
		if (this.parent instanceof IElement) {
			return this.parent.getGy() + this.y;
		}
		return this.y;
	}

	@Override
	public IElement setGy(int gy) {
		if (this.parent instanceof IElement) {
			this.y = gy - this.parent.getGy();
		} else {
			this.y = gy;
		}
		return this;
	}

	// Método para actualizar la posición de los hijos cuando el nodo cambia
	protected void updateChildrenPosition() {
		for (IElement child : this.children) {
			if (child instanceof Element) {
				Element childElement = (Element) child;
				// Calculamos la nueva posición global del hijo
				int newGlobalX = this.getGx() + childElement.getX();
				int newGlobalY = this.getGy() + childElement.getY();

				// Actualizamos la posición global del hijo si ha cambiado
				if (childElement.getGx() != newGlobalX) {
					childElement.setGx(newGlobalX);
				}
				if (childElement.getGy() != newGlobalY) {
					childElement.setGy(newGlobalY);
				}
			}
		}
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public IElement setX(int x) {
		if (this.x != x) {  // Solo actualizar si hay un cambio real
			if (config.isTextureCenteredPosition()) {
				this.x = getCenteredX(x);
			} else {
				this.x = x;
			}
			updateChildrenPosition();  // Actualizar posiciones de los hijos
		}
		return this;
	}

	@Override
	public IElement setY(int y) {
		if (this.y != y) {  // Solo actualizar si hay un cambio real
			if (config.isTextureCenteredPosition()) {
				this.y = getCenteredY(y);
			} else {
				this.y = y;
			}
			updateChildrenPosition();  // Actualizar posiciones de los hijos
		}
		return this;
	}



//	@Override
//	public IElement setX(int x) {
//		if (config.isTextureCenteredPosition()) {
//			this.x = getCenteredX(x);
//		} else {
//			this.x = x;
//		}
//		return this;
//	}

	@Override
	public ElementConfig getConfig() {
		return config;
	}

	@Override
	public void setConfig(ElementConfig config) {
		this.config = config;
	}

	@Override
	public IElement config(IConfigLambda<ElementConfig> configLambda) {
		this.config = (ElementConfig) configLambda.execute(this.config);
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
		for (IElement child : this.children) {
			child.setParent(this);

		}
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

		setX(x);
		setY(y);

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



	private int getCenteredX(int x) {
		return x-(getWidth()/2);
	}

	private int getCenteredY(int y) {
		return y-(getHeight()/2);
	}

	@Override
	public IElement getParent() {
		return parent;
	}

	@Override
	public void setParent(IElement parent) {
		this.parent = parent;
	}

	@Override
	public IElement modifyChildren(IChildrenLambda lambda) {
		lambda.modify(children);
		return this;
	}

	@Override
	public IElement modifyChild(int index, IChildLambda lambda) {
		lambda.modify(children.get(index));
		return this;
	}

	/**
	 * Retrieves elements that belong to a specific group.
	 *
	 * @param group The group name.
	 * @return A list of elements in the specified group.
	 */
	public List<IElement> getElementsInGroup(String group) {
		return children.stream()
			.filter(c -> c.getGroup().equals(group))
			.collect(Collectors.toList());
	}

	/**
	 * Finds and returns an element with the specified SID.
	 *
	 * @param sid The SID of the element.
	 * @return The element with the specified SID, or null if not found.
	 */
	public IElement getElementWithSid(String sid) {
		return children.stream()
			.filter(c -> c.getSid().equals(sid))
			.findFirst()
			.orElse(null);
	}
}



