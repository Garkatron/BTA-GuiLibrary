package deus.guilib.element.interfaces;

import net.minecraft.core.player.inventory.Container;

@FunctionalInterface
public interface ISetupProvider {
	Container createContainer();

}

