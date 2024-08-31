package deus.guilib.adapter;

import deus.guilib.math.Tuple;
import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.Slot;
import deus.guilib.rendering.base.organization.GuiConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Theme;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AdvancedGui extends GuiContainer {

	private int childDrawed = 0;
	protected List<Element> children;
	protected GuiConfig config;

	public AdvancedGui(Container container, Element... children) {
		super(container);
		this.children = new ArrayList<>(List.of(children));
		this.config = new GuiConfig(childrenPlacement.CENTER, Theme.VANILLA);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		if (mc == null) {
			System.out.println("Error on drawChild, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}

		if (children.isEmpty()) return;

		// Variables base de posición
		int baseX = 0;
		int baseY = 0;

		// Calcular las posiciones base en función del placement
		switch (config.getPlacement()) {
			case CENTER:
				baseX = (width - xSize) / 2;
				baseY = (height - ySize) / 2;
				break;
			case TOP:
				baseX = (width - xSize) / 2;
				baseY = 0;
				break;
			case BOTTOM:
				baseX = (width - xSize) / 2;
				baseY = height - ySize;
				break;
			case LEFT:
				baseX = 0;
				baseY = (height - ySize) / 2;
				break;
			case RIGHT:
				baseX = width - xSize;
				baseY = (height - ySize) / 2;
				break;
			case TOP_LEFT:
				baseX = 0;
				baseY = 0;
				break;
			case BOTTOM_LEFT:
				baseX = 0;
				baseY = height - ySize;
				break;
			case BOTTOM_RIGHT:
				baseX = width - xSize;
				baseY = height - ySize;
				break;
			case TOP_RIGHT:
				baseX = width - xSize;
				baseY = 0;
				break;
			case NONE:
			default:
				baseX = 0;
				baseY = 0;
				break;
		}

		// Bucle for para iterar sobre los hijos y dibujarlos
		for (int i = 0; i < children.size(); i++) {
			// Calcular la posición relativa
			int relativeX = 0;
			int relativeY = 0;

			if (childDrawed==i) {
			     relativeX = baseX + children.get(i).getX();
				 relativeY = baseY + children.get(i).getY();
			}
			// Si el hijo tiene la configuración de ignorar el placement del padre, dibujarlo sin aplicar el offset
			if (children.get(i).getConfig().getIgnoreFatherPlacement()) {
				children.get(i).draw();
			} else {
				// Dibujar el hijo en la posición relativa calculada
				// No actualizamos las coordenadas del hijo
				children.get(i).setY(relativeY);
				children.get(i).setX(relativeX);
				children.get(i).draw();
			}
			childDrawed++;

		}
	}





	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}

	public List<Element> getChildren() {
		return children;
	}

	public AdvancedGui setChildren(Element... children) {
		this.children = new ArrayList<>(List.of(children));
		return this;
	}

	public AdvancedGui addChildren(Element... children) {
		this.children.addAll(Arrays.asList(children));
		return this;
	}

	public List<Tuple<Integer, Integer>> getFinalSlotsPositions () {
		List<Tuple<Integer, Integer>> pos = new ArrayList<>();
		children.forEach(
			child ->
			{
				if (child instanceof Slot) {
					pos.add(((Slot) child).getFinalPosition());
				}
			});
		return pos;
	}



	public void config(GuiConfig config) {
		this.config = config;
	}
}
