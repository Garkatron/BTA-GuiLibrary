package deus.guilib.rendering.base.foreground;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Text extends ForegroundElement {

	protected String text = "";
	protected int y;
	protected int x;

	public Text(Gui gui, Minecraft mc, int x, int y, String text, ForegroundElement... children) {
		super(gui, mc, x, y, children);
		this.text = text;

	}

	public Text(Gui gui, Minecraft mc, String text, int x, int y) {
		super(gui, mc, x, y);
		this.text = text;

	}

	public Text(String text, int x, int y) {
		super(null, null, x, y);
		this.text = text;

	}


	@Override
	protected void drawIt() {
		super.drawIt();
		this.mc.fontRenderer.drawString(text,x,y,4210752);
	}
}
