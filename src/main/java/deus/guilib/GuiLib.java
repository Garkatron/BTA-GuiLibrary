package deus.guilib;

import deus.guilib.test.GuiLibTestBlocks;
import deus.guilib.util.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class GuiLib implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "guilib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigHandler config = new ConfigHandler();

	@Override
    public void onInitialize() {
        LOGGER.info("guilib initialized.");
		new GuiLibTestBlocks().initialize();
		new GuiLibTestBlocks().blockAddDetails();
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
