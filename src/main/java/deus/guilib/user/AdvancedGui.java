package deus.guilib.user;

import deus.guilib.element.GNode;
import deus.guilib.element.config.Placement;
import deus.guilib.interfaces.element.INode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public abstract class AdvancedGui extends GuiContainer  {

	private int lastWidth = -1;
	private int lastHeight = -1;
	protected List<INode> children;
	protected int mouseX = 0;
	protected int mouseY = 0;
	public Placement elementsPlacement = Placement.CHILD_DECIDE;

	public AdvancedGui(Container container, GNode... children) {
		super(container);
		mc = Minecraft.getMinecraft(this);
		this.children = new ArrayList<>(List.of(children));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);

		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		update();

		if (mc == null) {
			System.out.println("Error on drawChild, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}

		if (children.isEmpty()) return;

		int[] basePos = calculateBasePosition();
		int baseX = basePos[0];
		int baseY = basePos[1];

		// Bucle for-each para iterar sobre los hijos y dibujarlos
		for (INode child : children) {
			int relativeX = baseX + child.getX();
			int relativeY = baseY + child.getY();

			if (false) {
				child.setPosition(relativeX, relativeY);


			}

			child.draw();
		}
	}

	private int[] calculateBasePosition() {
		int baseX, baseY;

		switch (elementsPlacement) {
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
			default:
				baseX = 0;
				baseY = 0;
				break;
		}
		return new int[]{baseX, baseY};
	}

	public void update() {
		int newWidth = this.mc.resolution.scaledWidth;
		int newHeight = this.mc.resolution.scaledHeight;

		if (newWidth != lastWidth || newHeight != lastHeight) {
			this.xSize = newWidth;
			this.ySize = newHeight;
			lastWidth = newWidth;
			lastHeight = newHeight;
		}

		System.out.println(this.xSize);
		System.out.println(lastWidth);
		System.out.println(newWidth);

	}


	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}

	public List<INode> getChildren() {
		return children;
	}

	public AdvancedGui setChildren(INode... children) {
		this.children = new ArrayList<>(List.of(children));
		return this;
	}

	public void addChildren(INode... children) {
		this.children.addAll(Arrays.asList(children));
	}
}
