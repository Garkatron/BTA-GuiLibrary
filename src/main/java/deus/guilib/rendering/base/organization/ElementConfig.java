package deus.guilib.rendering.base.organization;

public class ElementConfig {
	private boolean internal = false;
	private childrenPlacement placement = childrenPlacement.NONE;
	private String theme = "VANILLA";
	private boolean ignoreFatherPlacement = false;
	private boolean useTheme = true;

	// Constructor completo
	public ElementConfig(boolean internal, childrenPlacement placement, String theme, boolean ignoreFatherPlacement, boolean useTheme) {
		this.internal = internal;
		this.placement = placement;
		this.theme = theme;
		this.ignoreFatherPlacement = ignoreFatherPlacement;
		this.useTheme = useTheme;
	}

	// Constructor con todas las variables excepto useTheme (valor predeterminado: true)
	public ElementConfig(boolean internal, childrenPlacement placement, String theme, boolean ignoreFatherPlacement) {
		this(internal, placement, theme, ignoreFatherPlacement, true);
	}

	// Constructor con todas las variables excepto ignoreFatherPlacement y useTheme (valor predeterminado: false y true respectivamente)
	public ElementConfig(boolean internal, childrenPlacement placement, String theme) {
		this(internal, placement, theme, false, true);
	}

	// Constructor con todas las variables excepto theme y useTheme (valor predeterminado: "VANILLA" y true respectivamente)
	public ElementConfig(boolean internal, childrenPlacement placement) {
		this(internal, placement, "VANILLA", false, true);
	}

	// Constructor con todas las variables excepto internal, placement y useTheme (valor predeterminado: false, NONE y true respectivamente)
	public ElementConfig(String theme, boolean ignoreFatherPlacement) {
		this(false, childrenPlacement.NONE, theme, ignoreFatherPlacement, true);
	}

	// Constructor con todas las variables excepto ignoreFatherPlacement y useTheme (valor predeterminado: false y true respectivamente)
	public ElementConfig(String theme) {
		this(false, childrenPlacement.NONE, theme, false, true);
	}

	// Constructor con todas las variables excepto internal, placement y useTheme (valor predeterminado: false, NONE y true respectivamente)
	public ElementConfig(boolean internal, boolean ignoreFatherPlacement, boolean useTheme) {
		this(internal, childrenPlacement.NONE, "VANILLA", ignoreFatherPlacement, useTheme);
	}

	// Constructor con todas las variables excepto placement y useTheme (valor predeterminado: NONE y true respectivamente)
	public ElementConfig(childrenPlacement placement) {
		this(false, placement, "VANILLA", false, true);
	}

	// Constructor con todas las variables excepto theme, ignoreFatherPlacement y useTheme (valor predeterminado: "VANILLA", false y true respectivamente)
//	public ElementConfig(boolean ignoreFatherPlacement) {
//		this(false, childrenPlacement.NONE, "VANILLA", ignoreFatherPlacement, true);
//	}

	// Constructor con todas las variables excepto useTheme (valor predeterminado: true)
	public ElementConfig(String theme, boolean ignoreFatherPlacement, boolean useTheme) {
		this(false, childrenPlacement.NONE, theme, ignoreFatherPlacement, useTheme);
	}

	// Métodos getter y setter
	public boolean isInternal() {
		return internal;
	}

	public ElementConfig setInternal(boolean internal) {
		this.internal = internal;
		return this;
	}

	public childrenPlacement getPlacement() {
		return placement;
	}

	public ElementConfig setPlacement(childrenPlacement placement) {
		this.placement = placement;
		return this;
	}

	public String getTheme() {
		return theme;
	}

	public ElementConfig setTheme(String theme) {
		this.theme = theme;
		return this;

	}

	public boolean getIgnoreFatherPlacement() {
		return ignoreFatherPlacement;
	}

	public ElementConfig setIgnoreFatherPlacement(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;
		return this;

	}

	public boolean isUseTheme() {
		return useTheme;
	}

	public ElementConfig setUseTheme(boolean useTheme) {
		this.useTheme = useTheme;
		return this;

	}
}
