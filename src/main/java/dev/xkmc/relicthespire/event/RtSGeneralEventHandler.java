package dev.xkmc.relicthespire.event;

import dev.xkmc.relicthespire.content.capability.BattleTracker;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RelicTheSpire.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RtSGeneralEventHandler {

	@SubscribeEvent
	public static void onTarget(LivingChangeTargetEvent event) {
		if (event.getNewTarget() instanceof Player player) {
			player.getCapability(BattleTracker.CAPABILITY).resolve().ifPresent(e ->
					e.onTarget(event.getEntity()));
		}
	}

	@SubscribeEvent
	public static void onKill(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof Player player) {
			player.getCapability(BattleTracker.CAPABILITY).resolve().ifPresent(e ->
					e.onKill(event.getEntity()));
		}
	}

}
