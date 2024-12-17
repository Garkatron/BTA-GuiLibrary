package deus.guilib.nodes;

import deus.guilib.GuiLib;
import deus.guilib.error.Error;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.nodes.config.Placement;
import deus.guilib.nodes.stylesystem.BorderStyle;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.resource.Texture;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.PlacementHelper;

import java.util.*;

public class Node extends Root implements IStylable {

	protected Placement selfPlacement = Placement.NONE;
	private String debugHexColor = "";

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
	public void deleteStylesRecursive() {
		LinkedList<INode> stack = new LinkedList<>();
		stack.push(parent);

		while (!stack.isEmpty()) {
			INode currentNode = stack.pop();

			if (currentNode instanceof IStylable) {
				((IStylable) currentNode).deleteStyles();
			}

			if (currentNode.hasChildren()) {
				stack.addAll(currentNode.getChildren());
			}
		}

	}

	@Override
	public void deleteStyles() {
		styles = new HashMap<>();
	}


	@Override
	protected void drawIt() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}

		if (parent != null) {
			PlacementHelper.positionItSelf(this, parent);
		}

		drawBackgroundColor();
		drawBackgroundImage();
		drawBorder();

		if (GuiLib.debugMode) {
			if (debugHexColor.isEmpty()) {
				debugHexColor = GuiHelper.randomHexColor();
			}
			drawCornersDebug(debugHexColor);
			drawCenterPoint(debugHexColor);
		}

	}

	@Override
	protected void updateIt() {
		super.updateIt();
		updateLocalAndGlobalPositionFromStyle();
		updateSizeFromStyle();
	}


	@Override
	public Placement getSelfPlacement() {
		return selfPlacement;
	}

	@Override
	public void setSelfPlacement(Placement placement) {
		this.selfPlacement = placement;
	}

	protected void drawBackgroundColor() {
		if (styles.containsKey("backgroundColor")) {
			String background = (String) styles.get("backgroundColor");
			if (!background.equals("transparent")) {
				this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB((String) styles.get("backgroundColor")));
			}
		}
	}

	protected void drawCornersDebug(String hex) {
		// TOP LEFT
		this.drawRect((this.gx + getWidth() - 2), this.gy, this.gx + getWidth(), this.gy + 2, StyleParser.parseColorToARGB(hex));

		// TOP RIGHT
		this.drawRect(this.gx, this.gy, this.gx + 2, this.gy + 2, StyleParser.parseColorToARGB(hex));

		// BOTTOM LEFT
		this.drawRect(this.gx, this.gy + getHeight(), this.gx + 2, (this.gy + getHeight() - 2), StyleParser.parseColorToARGB(hex));

		// BOTTOM RIGHT
		this.drawRect(this.gx + getWidth(), this.gy + getHeight(), (this.gx + getWidth() - 2), (this.gy + getHeight() - 2), StyleParser.parseColorToARGB(hex));
	}

	protected void drawCenterPoint(String hex) {
		this.drawRect(this.gx + getWidth()/2, this.gy + getHeight()/2, (this.gx + getWidth()/2 )+ 2, (this.gy + getHeight()/2 )+2, StyleParser.parseColorToARGB(hex));
	}

	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			if (!t.getPath().equals("transparent")) {

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
	}

	protected void drawBorder() {
		if (styles.containsKey("border")) {

			BorderStyle borderStyle = StyleParser.parseBorder((String) styles.get("border"));

			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
			drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
			drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
			drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha

		}

		if (styles.containsKey("border-top")) {
			BorderStyle borderTopStyle = StyleParser.parseBorder((String) styles.get("border-top"));
			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
		}

		if (styles.containsKey("border-left")) {
			BorderStyle borderLeftStyle = StyleParser.parseBorder((String) styles.get("border-left"));
			drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
		}

		if (styles.containsKey("border-bottom")) {
			BorderStyle borderBottomStyle = StyleParser.parseBorder((String) styles.get("border-bottom"));
			drawRect(this.gx, this.gy + getHeight() - borderBottomStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderBottomStyle.color); // Inferior
		}


		if (styles.containsKey("border-right")) {
			BorderStyle borderRightStyle = StyleParser.parseBorder((String) styles.get("border-right"));
			drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
		}

	}

	protected void updateSizeFromStyle() {
		updateWidth();
		updateHeight();

		updateSelfPlacement();
		updateChildrenPlacement();
	}

	protected void updateWidth() {
		if (styles.containsKey("width")) {
			String widthValue = (String) styles.get("width");

			if (widthValue.endsWith("%")) {
				this.width = PlacementHelper.calcRelativeSize(
					StyleParser.parseRelativeNumber(widthValue),
					parent.getWidth(),
					parent.getHeight()
				).getFirst();
			} else {
				this.width = StyleParser.parsePixels(widthValue);
			}
		}
	}
	// ! I'm thinking of a solution for that

	protected void updateHeight() {
		if (styles.containsKey("height")) {
			String heightValue = (String) styles.get("height");

			if (heightValue.endsWith("%")) {
				this.height = PlacementHelper.calcRelativeSize(
					StyleParser.parseRelativeNumber(heightValue),
					parent.getWidth(),
					parent.getHeight()
				).getSecond();
			} else {
				this.height = StyleParser.parsePixels(heightValue);
			}
		}
	}

	protected void updateChildrenPlacement() {
		if (styles.containsKey("childrenPlacement")) {
			this.childrenPlacement = Placement.valueOf((String) styles.get("childrenPlacement"));
		}
	}

	protected void updateSelfPlacement() {
		if (styles.containsKey("selfPlacement")) {
			this.selfPlacement = Placement.valueOf((String) styles.get("selfPlacement"));
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
