package deus.guilib.util.configuration;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static deus.guilib.GuiLib.MOD_ID;

public class ConfigHandler {

	private int BLOCK_ID;
	private int ITEM_ID;

	private static final TomlConfigHandler config;
	private static File players;

	static {
		Toml toml = new Toml(MOD_ID.toUpperCase(Locale.ROOT));

		toml.addCategory("IDs")
			.addEntry("startBlockId", 12000)
			.addEntry("startItemId", 11000);

		toml.addCategory("Example")
			.addEntry("activated", false);

		config = new TomlConfigHandler(null, MOD_ID, toml);

		// Inicializa el archivo de jugadores
		try {
			// Reemplaza el nombre del archivo en la ruta de configuración
			File playersFile = replaceFileName(config.getFilePath(), "permanent_rune_player.txt");
			players = new File(playersFile.getAbsolutePath());
			if (!players.exists()) {
				players.createNewFile(); // Crea el archivo si no existe
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static File replaceFileName(String oldFilePath, String newFileName) throws IOException {
		File oldFile = new File(oldFilePath);
		String directoryPath = oldFile.getParent();

		if (directoryPath == null) {
			throw new IOException("Invalid file path: " + oldFilePath);
		}

		return new File(directoryPath, newFileName);
	}

	public ConfigHandler() {
		BLOCK_ID = config.getInt("IDs.startBlockId");
		ITEM_ID = config.getInt("IDs.startItemId");
	}



	public TomlConfigHandler getConfig() {
		return config;
	}

	public int newBlockID() {
		BLOCK_ID = BLOCK_ID + 1;
		return BLOCK_ID;
	}

	public int newItemID() {
		ITEM_ID = ITEM_ID + 1;
		return ITEM_ID;
	}
}
