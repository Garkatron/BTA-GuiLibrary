package deus.guilib.element.config;

public class Config<T extends Config<T>> {
	private String theme = "NONE";
	private ChildrenPlacement placement = ChildrenPlacement.NONE;

	public static <T extends Config<T>> T create(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create config instance", e);
		}
	}

	public ChildrenPlacement getPlacement() {
		return placement;
	}

	public T setPlacement(ChildrenPlacement placement) {
		this.placement = placement;
		return (T) this;
	}

	public T setTheme(String theme) {
		this.theme = theme;
		return (T) this;
	}


	public String getTheme() {
		return theme;
	}
}
