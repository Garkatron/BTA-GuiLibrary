package deus.guilib.nodes;

import deus.guilib.nodes.config.Placement;
import deus.guilib.nodes.stylesystem.BorderStyle;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.error.Error;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.resource.Texture;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.PlacementHelper;

import java.util.Map;

public class Node extends Root implements IStylable {

	public Node() {
		super();
	}

	public Node(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	public void applyStyle(Map<String, Object> styles) {
		StyleSystem.loadImagesFromStyles(styles);
		this.styles = styles;
	}

	@Override
	public Map<String, Object> getStyle() {
		return this.styles;
	}

	@Override
	public void checkAndExecute(String x, Runnable r) {
		if (styles.containsKey(x)) {
			r.run();
		}
	}


	@Override
	protected void drawIt() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		updateLocalAndGlobalPositionFromStyle();
		updateSizeFromStyle();
		drawBackgroundColor();
		drawBackgroundImage();
		drawBorder();

	}

	protected void drawBackgroundColor() {
		if (styles.containsKey("backgroundColor")) {
			this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB((String) styles.get("backgroundColor")));
		}
	}

	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			int scaleW = 0, scaleH = 0;

			if (styles.containsKey("backgroundImageScale")) {
				scaleW = scaleH = (Integer) styles.get("backgroundImageScale");
			}

			if (styles.containsKey("backgroundImageScaleWidth")) {
				scaleW = (Integer) styles.get("backgroundImageScaleWidth");
			}

			if (styles.containsKey("backgroundImageScaleHeight")) {
				scaleH = (Integer) styles.get("backgroundImageScaleHeight");
			}

			t.draw(mc, gx, gy, width, height, scaleW, scaleH);
		}
	}

	protected void drawBorder() {
		if (styles.containsKey("border")) {

			BorderStyle borderStyle = StyleParser.parseBorder((String) styles.get("border"));

			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
			drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
			drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
			drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha

			if (styles.containsKey("border-top")) {
				BorderStyle borderTopStyle = StyleParser.parseBorder((String) styles.get("border-top"));
				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
			}

			if (styles.containsKey("border-bottom")) {
				BorderStyle borderBottomStyle = StyleParser.parseBorder((String) styles.get("border-bottom"));
				drawRect(this.gx, this.gy, this.gx + borderBottomStyle.width, this.gy + getHeight(), borderBottomStyle.color); // Izquierda
			}

			if (styles.containsKey("border-left")) {
				BorderStyle borderLeftStyle = StyleParser.parseBorder((String) styles.get("border-left"));
				drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
			}

			if (styles.containsKey("border-right")) {
				BorderStyle borderRightStyle = StyleParser.parseBorder((String) styles.get("border-right"));
				drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
			}

		}
	}

	protected void updateSizeFromStyle() {
		// Actualizar ancho
		updateWidth();
		// Actualizar alto
		updateHeight();
		// Actualizar colocación de hijos
		updateChildrenPlacement();
	}

	private void updateWidth() {
		if (styles.containsKey("width")) {
			String widthValue = (String) styles.get("width");

			if (widthValue.endsWith("%")) {
				this.width = PlacementHelper.calcRelativePosition(
					StyleParser.parseRelativeNumber(widthValue),
					parent.getWidth(),
					parent.getHeight()
				).getFirst();
			} else {
				this.width = StyleParser.parsePixels(widthValue);
			}
		}
	}

	private void updateHeight() {
		if (styles.containsKey("height")) {
			String heightValue = (String) styles.get("height");

			if (heightValue.endsWith("%")) {
				this.height = PlacementHelper.calcRelativePosition(
					StyleParser.parseRelativeNumber(heightValue),
					parent.getWidth(),
					parent.getHeight()
				).getSecond();
			} else {
				this.height = StyleParser.parsePixels(heightValue);
			}
		}
	}

	private void updateChildrenPlacement() {
		if (styles.containsKey("childrenPlacement")) {
			this.childrenPlacement = Placement.valueOf((String) styles.get("childrenPlacement"));
		}
	}

	protected void updateLocalAndGlobalPositionFromStyle() {
		if (styles.containsKey("localX")) {
			this.x = (int) styles.get("localX");
		}

		if (styles.containsKey("localY")) {
			this.y = (int) styles.get("localY");
		}

		if (styles.containsKey("globalX")) {
			this.gx = (int) styles.get("globalX");
		}

		if (styles.containsKey("globalY")) {
			this.gy = (int) styles.get("globalY");
		}
	}

}
