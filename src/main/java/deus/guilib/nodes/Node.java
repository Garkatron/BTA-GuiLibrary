package deus.guilib.nodes;

import deus.guilib.GuiLib;
import deus.guilib.error.Error;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.nodes.config.BackgroundMode;
import deus.guilib.nodes.config.Placement;
import deus.guilib.nodes.stylesystem.BorderStyle;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.resource.Texture;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.PlacementHelper;
import net.minecraft.client.render.Scissor;

import java.util.*;
import java.util.stream.Collectors;

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
		updateLocalPosition();
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

			if (t.getPath().equals("transparent")) return;

			int bgwidth, bgheight;
			int tilesize;

			if (styles.containsKey("tilesize")) {
				tilesize = StyleParser.parsePixels((String) styles.get("tilesize"));
			} else {
				tilesize = 16;
			}

			BackgroundMode bgmode = BackgroundMode.DEFAULT;

			if (styles.containsKey("bgMode")) {
				bgmode = parseBgMode((String) styles.get("bgMode"));
			}

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

			if (styles.containsKey("bgWidth")) {
				bgwidth = StyleParser.parsePixels((String) styles.get("bgWidth"));
			} else {
				bgwidth = 16;
			}
			if (styles.containsKey("bgHeight")) {
				bgheight = StyleParser.parsePixels((String) styles.get("bgHeight"));
			} else {
				bgheight = 16;
			}

			if (bgmode == BackgroundMode.DEFAULT) {

				t.draw(mc, gx, gy, bgwidth==0 ? 16 : bgwidth, bgheight==0 ? 16 : bgheight, scaleW, scaleH);

			} else if (bgmode == BackgroundMode.TILE) {

				int finalScaleH = scaleH;
				int finalScaleW = scaleW;

				int finalBgWidth = bgwidth == 0 ? width : bgwidth;
				int finalBgHeight = bgheight == 0 ? height : bgheight;

				int endX = gx + width;
				int endY = gy + height;

				int tilesX = (width + tilesize - 1) / tilesize; // Número de tiles horizontalmente
				int tilesY = (height + tilesize - 1) / tilesize; // Número de tiles verticalmente

				Scissor.scissor(gx, gy, width, height, () -> {
					for (int i = 0; i < tilesY; i++) {
						for (int j = 0; j < tilesX; j++) {
							int x = gx + j * tilesize;
							int y = gy + i * tilesize;


							if (x < endX && y < endY) {
								// Dibujar el tile completo sin cambiar su tamaño
								t.draw(mc, x, y, finalBgWidth, finalBgHeight, finalScaleW, finalScaleH);
							}
						}
					}
				});
			}


		}
	}

	protected BackgroundMode parseBgMode(String string) {
		if (string.equals("tile")) {
			return BackgroundMode.TILE;
		} else if(string.equals("default")) {
			return BackgroundMode.DEFAULT;
		}
		return BackgroundMode.DEFAULT;
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


	protected void updateLocalPosition() {
		if (styles.containsKey("localX")) {
			this.x = (int) styles.get("localX");
		}

		if (styles.containsKey("localY")) {
			this.y = (int) styles.get("localY");
		}

	}

}
