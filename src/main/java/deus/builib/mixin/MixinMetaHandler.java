package deus.builib.mixin;

import com.b100.utils.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deus.builib.interfaces.textures.IMetaHandler;
import net.minecraft.client.render.texture.meta.TextureMetaHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TextureMetaHandler.class)
public class MixinMetaHandler implements IMetaHandler {

	@Shadow
	private @Nullable JsonObject metaJson;

	@Unique
	@Override
	public void bui$setMeta(JsonObject meta) {
		this.metaJson = meta;
	}
}
