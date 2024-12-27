package deus.guilib.nodes.stylesystem;

import deus.guilib.util.rendering.TextureProperties;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

	private static TextureManager instance;
	private Map<String, TextureProperties> textureMap;

	private TextureManager() {
		textureMap = new HashMap<>();
	}

	public static TextureManager getInstance() {
		if (instance == null) {
			synchronized (TextureManager.class) {
				if (instance == null) {
					instance = new TextureManager();
				}
			}
		}
		return instance;
	}

	public Map<String, TextureProperties> getTextureMap() {
		return textureMap;
	}

	public void addTexture(String id, TextureProperties texture) {
		if (id == null || texture == null) {
			throw new IllegalArgumentException("ID and texture cannot be null.");
		}
		textureMap.put(id.trim(), texture);
	}

	public TextureProperties getTexture(String id) {
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
