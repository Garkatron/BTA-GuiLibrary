package deus.guilib.mixin;


import deus.guilib.atest.example.ExampleBlockTileEntity;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.player.inventory.ContainerChest;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerMP.class, remap = false)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements deus.guilib.atest.example.interfaces.mixin.IEntityPlayer, ICrafting
{
	@Shadow
	protected abstract void getNextWindowId();

	@Shadow
	private int currentWindowId;

	@Shadow
	public NetServerHandler playerNetServerHandler;

	@Shadow
	public abstract void displayGUIChest(IInventory iinventory);

	@Shadow
	public MinecraftServer mcServer;

	public EntityPlayerMPMixin(World world)
	{
		super(world);
	}

	@Override
	public void newSteps$displayGUILogPile(ExampleBlockTileEntity logPileTileEntity)
	{
		this.getNextWindowId();

		this.playerNetServerHandler.sendPacket(
			new Packet100OpenWindow(
				this.currentWindowId,
				24,
				logPileTileEntity.getInvName(),
				logPileTileEntity.getSizeInventory()
			));

		NetServerHandler.logger.info(this.username + " interacted with log pile at (" + this.x + ", " + this.y + ", " + this.z + ")");

		this.craftingInventory = new ContainerChest(this.inventory, logPileTileEntity);
		this.craftingInventory.windowId = this.currentWindowId;
		this.craftingInventory.onContainerInit(this);
	}

}
