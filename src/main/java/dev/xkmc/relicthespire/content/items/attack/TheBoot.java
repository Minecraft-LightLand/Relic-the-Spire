package dev.xkmc.relicthespire.content.items.attack;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.core.IAttackRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TheBoot extends BaseRelicItem implements IAttackRelicItem {

	private static double val() {
		return RtSModConfig.COMMON.curios.theBootMinDamage.get();
	}

	public TheBoot(Properties prop) {
		super(prop);
	}

	@Override
	public void onAttackTarget(ItemStack stack, AttackCache cache) {
		cache.addHurtModifier(DamageModifier.nonlinearFinal(162, e -> e <= 0.02 ? e : Math.max(e, (float) val())));
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Effects.MIN_DAMAGE.gray(RtSLang.num(val())));
	}

}
