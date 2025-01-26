package dev.xkmc.relicthespire.event;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.PlayerAttackCache;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.relicthespire.content.capability.BattleTracker;
import dev.xkmc.relicthespire.content.items.core.IAttackRelicItem;
import dev.xkmc.relicthespire.init.registrate.RtSEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class RtSAttackListener implements AttackListener {

	@Override
	public void onPlayerAttack(PlayerAttackCache cache) {
		if (cache.getStrength() > 0.95f) {
			cache.getAttacker().getCapability(BattleTracker.CAPABILITY).resolve().ifPresent(e ->
					e.onInitiateAttack(cache.getAttacker()));
		}
	}

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

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		var attacker = cache.getAttacker();
		var target = cache.getAttackTarget();
		if (attacker instanceof Player player) {
			IAttackRelicItem.onTrigger(player, (stack, e) -> e.onAttackTarget(stack, cache));
		}
		if (cache.getAttacker() != null) {
			var source = cache.getLivingHurtEvent().getSource();
			if (source.is(L2DamageTypes.DIRECT)) {
				var ins = target.getEffect(RtSEffect.THORN.get());
				if (ins != null) {
					GeneralEventHandler.schedule(() -> attacker.hurt(attacker.damageSources().thorns(target), ins.getAmplifier()));
				}
			}
		}
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack weapon) {
		if (cache.getAttackTarget() instanceof Player player) {
			IAttackRelicItem.onTrigger(player, (stack, e) -> e.onDamaged(stack, cache));
		}
	}

}
