package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Texture;

public class Button extends Element {


	public Button(Texture texture) {
		super(texture);
	}

	public Button(Texture texture, Element... children) {
		super(texture, children);
	}

	public Button(Texture texture, int x, int y, Element... children) {
		super(texture, x, y, children);
	}

	public Button(Texture texture, int x, int y, childrenPlacement placement) {
		super(texture, x, y, placement);
	}

	public Button(Texture texture, int x, int y) {
		super(texture, x, y);
	}

	public Button(Texture texture, int x, int y, ElementConfig config, Element... children) {
		super(texture, x, y, config, children);
	}
}
