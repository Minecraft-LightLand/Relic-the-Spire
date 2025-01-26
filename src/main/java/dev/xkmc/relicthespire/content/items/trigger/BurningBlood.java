package dev.xkmc.relicthespire.content.items.trigger;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.core.ITriggerRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BurningBlood extends BaseRelicItem implements ITriggerRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.burningBloodHeal.get();
	}

	public BurningBlood(Properties prop) {
		super(prop);
	}

	@Override
	public void killLastTarget(ItemStack stack, LivingEntity self) {
		self.heal((float) amount());
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.END_COMBAT.yellow());
		list.add(RtSLang.Effects.HEAL.bullet(RtSLang.num(amount())));
	}
}
