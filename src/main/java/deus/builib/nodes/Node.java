package deus.builib.nodes;

import deus.builib.GuiLib;
import deus.builib.error.BUIError;
import deus.builib.interfaces.nodes.INode;
import deus.builib.interfaces.nodes.IStylable;
import deus.builib.nodes.config.Placement;
import deus.builib.nodes.stylesystem.BorderStyle;
import deus.builib.nodes.stylesystem.StyleParser;
import deus.builib.nodes.stylesystem.TextureManager;
import deus.builib.util.GuiHelper;
import deus.builib.util.math.PlacementHelper;
import deus.builib.util.rendering.TextureProperties;

import java.util.*;

public class Node extends Root implements IStylable {

	protected Placement selfPlacement = Placement.NONE;
	private String debugHexColor = "";
	protected TextureManager tgm = TextureManager.getInstance();

	private int margin = 0;
	private int marginTop = 0;
	private int marginBottom = 0;
	private int marginLeft = 0;
	private int marginRight = 0;

	public Node() {
		super();
	}

	public Node(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	public void applyStyle(Map<String, Object> styles) {
		this.styles = styles;
		//StyleSystem.loadImagesFromStyles();
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
			throw new IllegalStateException(BUIError.MISSING_MC.getMessage());
		}

		if (parent != null) {
			PlacementHelper.positionItSelf(this, parent);
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
		updateLocalPosition();
		updateSizeFromStyle();
		updateMargin();
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
				this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB(background));
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
			String id = (String) styles.get("backgroundImage");

			if ("transparent".equals(id)) return;

			TextureProperties textureProps = tgm.getTexture(id);

			int bgwidth, bgheight;

			if (styles.containsKey("bgWidth")) {
				bgwidth = StyleParser.parsePixels((String) styles.get("bgWidth"));
			} else {
				bgwidth = 0;
			}
			if (styles.containsKey("bgHeight")) {
				bgheight = StyleParser.parsePixels((String) styles.get("bgHeight"));
			} else {
				bgheight = 0;
			}

			drawTexture(mc, textureProps, gx, gy, bgwidth==0 ? getWidth() : bgwidth, bgheight==0 ? getHeight() : bgheight);

		}
	}

	private void drawMargin() {
		int marginColor = StyleParser.parseColorToARGB("#AAAAAA"); // Color para visualizar márgenes

		// Dibujar márgenes visualmente
		this.drawRect(gx - marginLeft, gy - marginTop, gx + getWidth() + marginRight, gy, marginColor); // Superior
		this.drawRect(gx - marginLeft, gy + getHeight(), gx + getWidth() + marginRight, gy + getHeight() + marginBottom, marginColor); // Inferior
		this.drawRect(gx - marginLeft, gy, gx, gy + getHeight(), marginColor); // Izquierda
		this.drawRect(gx + getWidth(), gy, gx + getWidth() + marginRight, gy + getHeight(), marginColor); // Derecha
	}

	protected void drawBorder() {
		if (styles.containsKey("border")) {

			String b = (String) styles.get("border");
			if(!"none".equals(b)) {
				BorderStyle borderStyle = StyleParser.parseBorder(b);

				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
				drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
				drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
				drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha
			}



		}

		if (styles.containsKey("border-top")) {
			String b = (String) styles.get("border-top");
			if(!"none".equals(b)) {
				BorderStyle borderTopStyle = StyleParser.parseBorder(b);
				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
			}
		}

		if (styles.containsKey("border-left")) {
			String b = (String) styles.get("border-left");
			if(!"none".equals(b)) {
				BorderStyle borderLeftStyle = StyleParser.parseBorder(b);
				drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
			}
		}

		if (styles.containsKey("border-bottom")) {
			String b = (String) styles.get("border-bottom");
			if(!"none".equals(b)) {
				BorderStyle borderBottomStyle = StyleParser.parseBorder(b);
				drawRect(this.gx, this.gy + getHeight() - borderBottomStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderBottomStyle.color); // Inferior
			}
		}


		if (styles.containsKey("border-right")) {
			String b = (String) styles.get("border-right");
			if(!"none".equals(b)) {
				BorderStyle borderRightStyle = StyleParser.parseBorder(b);
				drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
			}
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

	protected void updateMargin() {
		if (styles.containsKey("margin")) {
			margin = StyleParser.parsePixels((String) styles.get("margin"));
		}

		if (styles.containsKey("margin-top")) {
			marginTop = StyleParser.parsePixels((String) styles.get("margin-top"));
		} else {
			marginTop = margin;
		}

		if (styles.containsKey("margin-bottom")) {
			marginBottom = StyleParser.parsePixels((String) styles.get("margin-bottom"));
		} else {
			marginBottom = margin;
		}

		if (styles.containsKey("margin-left")) {
			marginLeft = StyleParser.parsePixels((String) styles.get("margin-left"));
		} else {
			marginLeft = margin;
		}

		if (styles.containsKey("margin-right")) {
			marginRight = StyleParser.parsePixels((String) styles.get("margin-right"));
		} else {
			marginRight = margin;
		}

	}

	protected void updateLocalPosition() {
		if (styles.containsKey("localX")) {
			this.x = (int) styles.get("localX");
		}

		if (styles.containsKey("localY")) {
			this.y = (int) styles.get("localY");
		}

	}

}
