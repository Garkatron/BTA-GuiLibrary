package deus.builib.nodes.stylesystem.textures;

import net.minecraft.client.render.texture.meta.gui.GuiTextureProperties;

import java.util.HashMap;
import java.util.Map;

public class BUITextureManager {

	private static BUITextureManager instance;
	private Map<String, BuiTextureProperties> textureMap;

	private BUITextureManager() {
		textureMap = new HashMap<>();
	}

	public static BUITextureManager getInstance() {
		if (instance == null) {
			synchronized (BUITextureManager.class) {
				if (instance == null) {
					instance = new BUITextureManager();
				}
			}
		}
		return instance;
	}

	public Map<String, BuiTextureProperties> getTextureMap() {
		return textureMap;
	}

	public void addTexture(String id, BuiTextureProperties texture) {
		if (id == null || texture == null) {
			throw new IllegalArgumentException("ID and texture cannot be null.");
		}
		textureMap.put(id.trim(), texture);
	}

	public BuiTextureProperties getTexture(String id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		return textureMap.get(id.trim());
	}

	public boolean hasTexture(String id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		return textureMap.containsKey(id.trim());
	}

	public void removeTexture(String id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		textureMap.remove(id.trim());
	}
}
