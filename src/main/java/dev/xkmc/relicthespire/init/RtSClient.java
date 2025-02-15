package dev.xkmc.relicthespire.init;

import dev.xkmc.relicthespire.content.client.PotionClientTooltip;
import dev.xkmc.relicthespire.content.client.PotionDeco;
import dev.xkmc.relicthespire.content.client.PotionTooltip;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = RelicTheSpire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtSClient {

	@SubscribeEvent
	public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
		var deco = new PotionDeco();
		event.register(Items.POTION, deco);
		event.register(Items.SPLASH_POTION, deco);
		event.register(Items.LINGERING_POTION, deco);
	}

	@SubscribeEvent
	public static void registerClientTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(PotionTooltip.class, PotionClientTooltip::new);
	}

	@SubscribeEvent
	public static void registerParticle(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(RtSItems.BLIGHT_FLAME.get(), FlameParticle.Provider::new);
	}

}
