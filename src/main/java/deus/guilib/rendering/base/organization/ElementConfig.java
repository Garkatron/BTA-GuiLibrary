package deus.guilib.rendering.base.organization;

public class ElementConfig {
	private boolean internal = false;
	private childrenPlacement placement = childrenPlacement.NONE;
	private String theme = "VANILLA";
	private boolean ignoreFatherPlacement = false;
	private boolean useTheme = true;

	// Constructor privado para evitar la creación de objetos sin configurar
	private ElementConfig() {}

	// Método estático para iniciar la configuración
	public static ElementConfig create() {
		return new ElementConfig();
	}

	// Métodos encadenados para configurar la instancia
	public ElementConfig setInternal(boolean internal) {
		this.internal = internal;
		return this;
	}

	public ElementConfig setPlacement(childrenPlacement placement) {
		this.placement = placement;
		return this;
	}

	public ElementConfig setTheme(String theme) {
		this.theme = theme;
		return this;
	}

	public ElementConfig setIgnoreFatherPlacement(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;
		return this;
	}

	public ElementConfig setUseTheme(boolean useTheme) {
		this.useTheme = useTheme;
		return this;
	}

	// Métodos getter
	public boolean isInternal() {
		return internal;
	}

	public childrenPlacement getPlacement() {
		return placement;
	}

	public String getTheme() {
		return theme;
	}

	public boolean getIgnoreFatherPlacement() {
		return ignoreFatherPlacement;
	}

	public boolean isUseTheme() {
		return useTheme;
	}
}
