package deus.guilib.util.blockanditems;

import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

import static deus.guilib.GuiLib.MOD_ID;

public class ItemMaker {
	public static ItemBuilder genericItemBuilder = new ItemBuilder(MOD_ID);

	public static <T extends Item> T make(ItemBuilder builder, T item) {
		return builder.build(item);
	}

	public static  <T extends Item> T  make(T item) {
		return genericItemBuilder.build(item);
	}

	public static <T extends Item> List<Item> make(T... items) {
		List<Item> iItems = new ArrayList<>();
		for (T item : items) {
			iItems.add(genericItemBuilder.build(item));
		}
		return iItems;
	}

	public static <T extends Item> List<Item> make(ItemBuilder builder, T... items) {
		List<Item> iItems = new ArrayList<>();
		for (Item item : items) {
			iItems.add(builder.build(item));
		}
		return iItems;
	}
}


