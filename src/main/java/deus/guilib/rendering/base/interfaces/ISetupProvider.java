package deus.guilib.rendering.base.interfaces;

import net.minecraft.core.player.inventory.Container;

@FunctionalInterface
public interface ISetupProvider {
	Container createContainer();

}

