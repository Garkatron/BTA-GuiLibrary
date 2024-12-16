package deus.guilib;

import deus.guilib.examples.GuiLibTestBlocks;
import deus.guilib.nodes.domsystem.XMLProcessor;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.util.configuration.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GuiLib implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "guilib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigHandler config = new ConfigHandler();
	public static File folder = null;

	@Override
    public void onInitialize() {
        LOGGER.info("Gui Lib initialized. Debug: {}", GuiLib.LOGGER.isDebugEnabled());
		if (config.getConfig().getBoolean("Example.activated")){
			LOGGER.info("Gui Lib Blocks initializing");
			new GuiLibTestBlocks().initialize();
			new GuiLibTestBlocks().blockAddDetails();
		}

		File mcdir = Minecraft.getMinecraft(this).getMinecraftDir();

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
