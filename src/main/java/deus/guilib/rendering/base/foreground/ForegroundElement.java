package deus.guilib.rendering.base.foreground;

import deus.guilib.rendering.base.interfaces.IDadForegroundElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ForegroundElement implements IDadForegroundElement {

	protected int x, y;
	protected Gui gui;
	protected Minecraft mc;
	protected List<ForegroundElement> children = new ArrayList<>();

	public ForegroundElement(Gui gui, Minecraft mc, int x, int y, ForegroundElement... children) {
		this.mc = mc;
		this.gui = gui;
		this.children = new ArrayList<>(Arrays.asList(children)); // Inicializa como lista mutable
		this.x = x;
		this.y = y;
		injectDependency();
	}

	public ForegroundElement(Gui gui, Minecraft mc, int x, int y) {
		this.mc = mc;
		this.gui = gui;
		this.x = x;
		this.y = y;
	}

	public ForegroundElement(int x, int y) {
		this(null,null, x, y);
	}

	protected void injectDependency() {
		// Inyectar dependencias en los hijos directos
		for (ForegroundElement child : this.children) {
			if (child != null && !child.hasDependency()) {
				child.setMc(mc);
				child.setGui(gui);

				// Llamar a injectDependency de forma recursiva para inyectar dependencias en los hijos del hijo
				child.injectDependency();
			}
		}
	}
	protected void setMc(Minecraft mc) {
		this.mc = mc;
	}

	protected void setGui(Gui gui) {
		this.gui = gui;
	}

	protected boolean hasDependency() {
		return gui!=null && mc!=null;
	}
	protected void drawIt() {
		if (mc==null || gui==null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
	}

	protected void drawChildren() {
		if (mc==null || gui==null) {
			System.out.println("Error on drawChild, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		for (ForegroundElement element : children) {
			element.draw();
		}
	}

	public void draw() {
		drawIt();
		drawChildren();
	}

	@Override
	public List<ForegroundElement> getChildren() {
		return children;
	}

	@Override
	public ForegroundElement addChildren(ForegroundElement... children) {
		injectDependency();
		return this;
	}

	@Override
	public ForegroundElement setChildren(ForegroundElement... children) {
		injectDependency();
		return this;
	}
}
