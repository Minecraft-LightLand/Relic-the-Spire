package dev.xkmc.relicthespire.content.items.attack;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.relicthespire.content.capability.BattleTracker;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.core.IAttackRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Akabeko extends BaseRelicItem implements IAttackRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.akabekoDamageAdd.get();
	}

	public Akabeko(Properties prop) {
		super(prop);
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.FIRST_ATTACK.yellow());
		list.add(RtSLang.Effects.DAMAGE_BOOST.bullet(RtSLang.num(amount())));
	}

	@Override
	public void onAttackTarget(ItemStack stack, AttackCache cache) {
		if (cache.getAttacker() instanceof Player player &&
				player.getCapability(BattleTracker.CAPABILITY).resolve().map(e ->
						e.getAttackCount() == 1 && e.isValidAttackStamp(player.level())
				).orElse(false)) {
			cache.addHurtModifier(DamageModifier.add((float) amount()));
		}
	}

}
