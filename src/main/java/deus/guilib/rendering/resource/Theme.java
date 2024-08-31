package deus.guilib.rendering.resource;

import deus.guilib.rendering.advanced.PlayerInventory;
import deus.guilib.rendering.base.Panel;
import deus.guilib.rendering.base.Slot;

import java.util.HashMap;
import java.util.Map;

public enum Theme {
	VANILLA(new HashMap<String,String>() {{
		put(PlayerInventory.class.getSimpleName(),"assets/textures/gui/Inventory.png");
		put(Slot.class.getSimpleName(),"assets/textures/gui/Slot.png");
		put(Panel.class.getSimpleName(),"assets/textures/gui/Panel.png");
	}}),
	DARK(new HashMap<String, String>() {{
		put(PlayerInventory.class.getSimpleName(), "assets/textures/gui/themes/dark/inventory/Inventory.png");
		put(Slot.class.getSimpleName(),"assets/textures/gui/themes/dark/inventory/Slot.png");
	}});

	private final Map<String, String> properties;

	Theme(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
