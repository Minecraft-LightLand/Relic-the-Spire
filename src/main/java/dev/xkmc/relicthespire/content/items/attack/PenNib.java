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

public class PenNib extends BaseRelicItem implements IAttackRelicItem {

	private static double factor() {
		return RtSModConfig.COMMON.curios.penNibDamageFactor.get();
	}

	private static double count() {
		return RtSModConfig.COMMON.curios.penNibDamageCount.get();
	}

	public PenNib(Properties prop) {
		super(prop);
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.NTH_ATTACK.yellow(RtSLang.num(count())));
		list.add(RtSLang.Effects.DAMAGE_BOOST.bullet(RtSLang.perc(factor())));
	}

	@Override
	public void onAttackTarget(ItemStack stack, AttackCache cache) {
		if (cache.getAttacker() instanceof Player player &&
				player.getCapability(BattleTracker.CAPABILITY).resolve().map(e ->
						e.getAttackCount() % count() == 0 && e.isValidAttackStamp(player.level())
				).orElse(false)) {
			cache.addHurtModifier(DamageModifier.multBase(1 + (float) factor()));
		}
	}

}
