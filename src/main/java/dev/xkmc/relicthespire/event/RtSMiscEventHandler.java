package dev.xkmc.relicthespire.event;

import dev.xkmc.relicthespire.content.items.special.PotionBelt;
import dev.xkmc.relicthespire.content.items.special.SnakeSkull;
import dev.xkmc.relicthespire.content.items.special.ToyOrnithopter;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RelicTheSpire.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RtSMiscEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
		if (event.isCanceled()) return;
		if (event.getItemStack().is(Items.SPLASH_POTION) || event.getItemStack().is(Items.LINGERING_POTION)) {
			ToyOrnithopter.trigger(event.getEntity(), event.getItemStack());
			PotionBelt.onThrowPotion(event.getEntity(), event);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
		if (event.getItem().is(Items.POTION)) {
			ToyOrnithopter.trigger(event.getEntity(), event.getItem());
			PotionBelt.consumePotion(event.getEntity(), event);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onEffectTest(MobEffectEvent.Added event) {
		if (event.getEffectSource() instanceof Player player && event.getEntity() != player) {
			var ins = event.getEffectInstance();
			if (ins != null && ins.getEffect() == MobEffects.POISON) {
				SnakeSkull.trigger(player, ins);
			}
		}
	}

}
