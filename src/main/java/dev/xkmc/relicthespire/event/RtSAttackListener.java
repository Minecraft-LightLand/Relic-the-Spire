package dev.xkmc.relicthespire.event;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.relicthespire.content.capability.BattleTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class RtSAttackListener implements AttackListener {

	@Override
	public void postAttack(AttackCache cache, LivingAttackEvent event, ItemStack weapon) {
		if (cache.getAttacker() instanceof Player player) {
			player.getCapability(BattleTracker.CAPABILITY).resolve().ifPresent(e ->
					e.onAttack(cache.getAttackTarget()));
		}if (cache.getAttackTarget() instanceof Player player) {
			player.getCapability(BattleTracker.CAPABILITY).resolve().ifPresent(e ->
					e.onAttacked(cache.getAttacker()));
		}
	}

}
