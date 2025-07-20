package dev.xkmc.relicthespire.content.entity.merchant;

import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerchantRenderer extends MobRenderer<MerchantEntity, MerchantModel<MerchantEntity>> {

	public static final ResourceLocation TEX = RelicTheSpire.loc("textures/entities/merchant.png");

	public MerchantRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new MerchantModel<>(ctx.bakeLayer(MerchantModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(MerchantEntity e) {
		return TEX;
	}

}
