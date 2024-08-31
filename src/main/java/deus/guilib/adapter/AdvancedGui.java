package deus.guilib.adapter;

import deus.guilib.math.Tuple;
import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.Slot;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AdvancedGui extends GuiContainer {

	protected List<Element> children;


	public AdvancedGui(Container container, Element... children) {
		super(container);
		this.children = new ArrayList<>(List.of(children));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		for (Element element : children) {
			element.draw();
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
}
