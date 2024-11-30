package deus.guilib.element.elements.containers;

import deus.guilib.element.GNode;
import deus.guilib.interfaces.element.INode;
import deus.guilib.resource.Texture;

public class Column extends GNode {

	private int length = 3;
	private int offset = 7;
	private boolean small = false;

	public Column() {
		super();
		styles.put("BackgroundImage", new Texture("assets/textures/gui/Column.png", 32, 32));
	}
	public Column setLength(int length) {
		this.length = length;
		return this;
	}

	public Column setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public Column setSmall(boolean small) {
		this.small = small;
		return this;
	}

	@Override
	protected void drawIt() {
		super.drawIt();

		/*
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (i == length - 1) {
					texture.setFrameY(2);
				} else if (i > 0) {
					texture.setFrameY(1);
				} else {
					texture.setFrameY(0);
				}
				texture.draw(mc, gx, gy + (i * 32));
			}
		}
	*/
	}




	@Override
	protected void drawChild() {
		int numChildren = children.size();

		if (numChildren > 0) {
			int totalWidth = 0;
			for (INode child : children) {
				totalWidth += child.getWidth();
			}
			totalWidth += (numChildren - 1) * offset;

			int startY = gy + (getHeight() - totalWidth) / 2;
			int currentY = startY - (small ? 3 : 0);

			for (int i = 0; i < numChildren; i++) {
				INode child = children.get(i);

				int childHeight = child.getHeight();
				int posY = currentY;
				int posX = 7;

				child.setPosition(posX, posY);
				child.draw();

				currentY += childHeight + offset;
			}
		}
	}

	@Override
	public int getHeight() {
		return length * 32;
	}
}
