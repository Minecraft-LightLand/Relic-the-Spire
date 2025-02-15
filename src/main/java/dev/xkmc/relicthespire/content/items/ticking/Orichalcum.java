package dev.xkmc.relicthespire.content.items.ticking;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Orichalcum extends BaseRelicItem {

	private static int interval() {
		return RtSModConfig.COMMON.curios.orichalcumInterval.get();
	}

	private static double amount() {
		return RtSModConfig.COMMON.curios.orichalcumAbsorption.get();
	}

	public Orichalcum(Properties prop) {
		super(prop);
	}

	@Override
	public void tick(ItemStack stack, LivingEntity user) {
		if (user.tickCount % interval() == 0) {
			user.setAbsorptionAmount(Math.max(user.getAbsorptionAmount(), (float) amount()));
		}
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.INTERVAL.gray(RtSLang.num(interval() / 20d)));
		list.add(RtSLang.Effects.ABSORPTION.bullet(RtSLang.num(amount())));
	}

}
