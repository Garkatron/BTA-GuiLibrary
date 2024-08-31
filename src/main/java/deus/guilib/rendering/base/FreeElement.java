package deus.guilib.rendering.base;

import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Texture;

public class FreeElement extends Element{


	public FreeElement(Texture texture, int x, int y, ElementConfig config, Element... children) {
		super(texture, x, y, config, children);
	}

	public FreeElement(Texture texture, int x, int y) {
		super(texture, x, y);
	}

	public FreeElement(Texture texture, int x, int y, childrenPlacement placement) {
		super(texture, x, y, placement);
	}

	public FreeElement(Texture texture, int x, int y, Element... children) {
		super(texture, x, y, children);
	}

	public FreeElement(Texture texture, Element... children) {
		super(texture, children);
	}

	public FreeElement(Texture texture) {
		super(texture);
	}
}
