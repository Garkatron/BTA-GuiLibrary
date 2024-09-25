package deus.guilib.element.elements.representation;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.resource.Texture;

import java.util.ArrayList;
import java.util.List;

public class FlowChartElement extends Element {

	protected List<IElement> connections = new ArrayList<>();
	private boolean isCircular = true; // By default, the element is circular

	public FlowChartElement() {
		super(new Texture("assets/textures/gui/graphCircle.png",32,32));
	}

	// Method to set whether the element is circular or rectangular
	public FlowChartElement setCircular(boolean isCircular) {
		this.isCircular = isCircular;
		return this;
	}

	// Method to draw lines connecting the element to its connected elements
	protected void drawLines() {
		connections.forEach(c -> {
			if (isCircular) {
				// If the element is circular, use the circular calculation
				drawLineFromCircularElement(c);
			} else {
				// If the element is rectangular, use the rectangular calculation
				drawLineFromRectangularElement(c);
			}
		});
		System.out.println("LINES");
	}

	// Method to draw a line from the edge of a circular element to another element
	private void drawLineFromCircularElement(IElement c) {
		// Center of this element
		int x1 = gx + (getWidth() / 2);
		int y1 = gy + (getHeight() / 2);

		// Center of the connected element
		int x2 = c.getGx() + (c.getWidth() / 2);
		int y2 = c.getGy() + (c.getHeight() / 2);

		// Calculate the angle of the line between the two centers
		double angle = Math.atan2(y2 - y1, x2 - x1);

		// Radius of this element (assuming it's circular)
		int radius1 = getWidth() / 2;

		// Radius of the connected element (assuming it's circular)
		int radius2 = c.getWidth() / 2;

		// Calculate the points on the edge of the circles for both elements
		int startX = (int) (x1 + radius1 * Math.cos(angle));
		int startY = (int) (y1 + radius1 * Math.sin(angle));
		int endX = (int) (x2 - radius2 * Math.cos(angle));
		int endY = (int) (y2 - radius2 * Math.sin(angle));

		// Draw the line from the edge of this element to the edge of the connected element
		this.drawLine(startX, startY, endX, endY, 0xFFFFFFFF);
	}

	// Method to draw a line from the edge of a rectangular element to another element
	private void drawLineFromRectangularElement(IElement c) {
		// Center of this element
		int x1 = gx + (getWidth() / 2);
		int y1 = gy + (getHeight() / 2);

		// Center of the connected element
		int x2 = c.getGx() + (c.getWidth() / 2);
		int y2 = c.getGy() + (c.getHeight() / 2);

		// Calculate the angle of the line between the two centers
		double angle = Math.atan2(y2 - y1, x2 - x1);

		// Get the intersection points with the edges of the rectangles
		int[] startCoords = getIntersectionWithRectangle(x1, y1, getWidth(), getHeight(), angle);
		int[] endCoords = getIntersectionWithRectangle(x2, y2, c.getWidth(), c.getHeight(), angle + Math.PI);

		// Draw the line from the edge of this element to the edge of the connected element
		this.drawLine(startCoords[0], startCoords[1], endCoords[0], endCoords[1], 0xFFFFFFFF);
	}

	// Method to calculate the intersection of a line with the edge of a rectangle
	private int[] getIntersectionWithRectangle(int cx, int cy, int width, int height, double angle) {
		double tanAngle = Math.tan(angle);
		int halfWidth = width / 2;
		int halfHeight = height / 2;

		// Calculate intersection with the sides of the rectangle
		if (Math.abs(tanAngle) < (double) height / width) {
			// Intersection with the left or right sides
			int x = (angle > 0 && angle < Math.PI) ? halfWidth : -halfWidth;
			int y = (int) (x * tanAngle);
			return new int[] {cx + x, cy + y};
		} else {
			// Intersection with the top or bottom
			int y = (angle > -Math.PI / 2 && angle < Math.PI / 2) ? halfHeight : -halfHeight;
			int x = (int) (y / tanAngle);
			return new int[] {cx + x, cy + y};
		}
	}

	@Override
	public void draw() {
		drawLines(); // Draw lines connecting this element to others
		super.draw(); // Call the parent class's draw method
	}

	// Method to add a connection to another GraphElement
	public FlowChartElement addConnection(FlowChartElement element) {
		connections.add(element);
		return this;
	}

	// Method to add multiple connections to other elements
	public FlowChartElement addConnections(IElement ...elements) {
		connections.addAll(List.of(elements));
		return this;
	}
}
