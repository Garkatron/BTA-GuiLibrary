package deus.guilib.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ContainerComponent implements IContainerComponent {

	protected int x, y;
	protected int width, height;
	protected List<ContainerComponent> children = new ArrayList<>();
	protected AdvancedContainer container;

	// Constructor principal con solo Container
	public ContainerComponent(AdvancedContainer container) {
		this.container = container;
	}

	// Constructor con Container y posición (x, y)
	public ContainerComponent(AdvancedContainer container, int x, int y) {
		this.container = container;
		this.x = x;
		this.y = y;
	}

	// Constructor con Container, posición (x, y) y tamaño (width, height)
	public ContainerComponent(AdvancedContainer container, int x, int y, int width, int height) {
		this.container = container;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// Constructor con Container, posición (x, y), tamaño (width, height) y lista de hijos
	public ContainerComponent(AdvancedContainer container, int x, int y, int width, int height, ContainerComponent... children) {
		this.container = container;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setChildren(children); // Agrega e inyecta dependencias de los hijos
	}

	// Constructor con Container y lista de hijos
	public ContainerComponent(AdvancedContainer container, ContainerComponent... children) {
		this.container = container;
		setChildren(children); // Agrega e inyecta dependencias de los hijos
	}

	// Constructor con posición (x, y), tamaño (width, height) y lista de hijos
	public ContainerComponent(int x, int y, int width, int height, ContainerComponent... children) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setChildren(children); // Agrega e inyecta dependencias de los hijos
	}

	// Constructor con posición (x, y) y tamaño (width, height)
	public ContainerComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// Constructor con posición (x, y)
	public ContainerComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Constructor con lista de hijos
	public ContainerComponent(ContainerComponent... children) {
		setChildren(children); // Agrega e inyecta dependencias de los hijos
	}

	protected void injectDependency() {
		// Inyectar dependencias en los hijos directos
		for (ContainerComponent child : this.children) {
			if (child != null && !child.hasDependency()) {
				child.setContainer(container);

				// Llamar a injectDependency de forma recursiva para inyectar dependencias en los hijos del hijo
				child.injectDependency();
			}
		}
	}



	@Override
	public List<ContainerComponent> getChildren() {
		return children;
	}

	@Override
	public ContainerComponent setChildren(ContainerComponent... children) {
		this.children = new ArrayList<>(Arrays.asList(children)); // Cambiar a lista mutable
		injectDependency();
		return this;
	}

	@Override
	public ContainerComponent addChildren(ContainerComponent... children) {
		this.children.addAll(Arrays.asList(children)); // Agregar a la lista mutable
		injectDependency();
		return this;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	protected void setContainer(AdvancedContainer container) {
		this.container = container;
	}

	protected boolean hasDependency() {
		return container != null;
	}
}
