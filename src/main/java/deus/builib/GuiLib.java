package deus.builib;


import deus.builib.examples.GuiLibTestBlocks;
import deus.builib.util.configuration.ConfigHandler;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


import java.io.File;


public class GuiLib implements ModInitializer, RecipeEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "builib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigHandler config = new ConfigHandler();
	public static File folder = null;
	public static boolean debugMode = false;

	@Override
    public void onInitialize() {
        LOGGER.info("Gui Lib initialized. Debug: {}", GuiLib.LOGGER.isDebugEnabled());
		if (config.getConfig().getBoolean("Example.activated")){
			LOGGER.info("Gui Lib Blocks initializing");
			new GuiLibTestBlocks().initialize();
			new GuiLibTestBlocks().blockAddDetails();
		}

		File mcdir = Minecraft.getMinecraft().getMinecraftDir();

		File guiLibFolder = new File(mcdir, "GuiLibrary/TestFolder/");

		folder = guiLibFolder;

		try {
			if (guiLibFolder.exists()) {
				System.out.println("The 'guiLibFolder' folder already exists at: " + guiLibFolder.getAbsolutePath());
			} else {
				if (guiLibFolder.mkdirs()) {
					System.out.println("The 'guiLibFolder' folder was successfully created at: " + guiLibFolder.getAbsolutePath());
				} else {
					System.err.println("Unable to create the 'lava' folder. Please check permissions or the path.");
				}
			}
		} catch (Exception e) {
			System.err.println("An error occurred while creating the 'guiLibFolder' folder: " + e.getMessage());
		}

		//XMLProcessor.printChildNodes(XMLProcessor.parseXMLFromAssets(GuiLib.class, "/assets/guilib/examples/craftingTableExample/craftingTable.xml",true),"-",0);
	}



	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeClientStart() {

	}
	public void afterClientStart() {
	}

}
