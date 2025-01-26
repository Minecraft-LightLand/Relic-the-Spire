package dev.xkmc.relicthespire.content.items.ticking;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.registrate.RtSEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BronzeScales extends BaseRelicItem {

	private static int amp() {
		return RtSModConfig.COMMON.curios.bronzeScalesThornAmplifier.get();
	}

	public BronzeScales(Properties prop) {
		super(prop);
	}

	@Override
	protected void tick(ItemStack stack, LivingEntity user) {
		inflictAmbient(RtSEffect.THORN.get(), amp(), user, user);
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Effects.EFFECT_SELF.gray(RtSLang.effect(RtSEffect.THORN.get(), amp())));
	}

}
