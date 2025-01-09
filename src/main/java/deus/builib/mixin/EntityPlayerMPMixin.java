package deus.builib.mixin;


import deus.builib.examples.exampleGui.ExampleBlockTileEntity;
import deus.builib.examples.interfaces.mixin.IEntityPlayer;

import net.minecraft.core.crafting.ContainerListener;
import net.minecraft.core.entity.player.Player;

import net.minecraft.core.net.packet.PacketContainerOpen;
import net.minecraft.core.player.inventory.menu.MenuCrafting;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class EntityPlayerMPMixin extends Player implements IEntityPlayer, ContainerListener
{
	@Shadow
	protected abstract void getNextWindowId();

	@Shadow
	private int currentWindowId;

	@Shadow
	public PacketHandlerServer playerNetServerHandler;


	public EntityPlayerMPMixin(World world)
	{
		super(world);
	}

	@Override
	public void guiLib$openExampleGui(ExampleBlockTileEntity logPileTileEntity) {
		this.getNextWindowId();
		this.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, 1, "Crafting", 9));
		this.craftingInventory.onCraftGuiClosed(this);
		this.craftingInventory = new MenuCrafting(this.inventory, this.world, (int) x, (int) y, (int) z);
		this.craftingInventory.containerId = this.currentWindowId;
		this.craftingInventory.addSlotListener(this);
	}

}
