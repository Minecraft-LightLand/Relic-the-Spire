package dev.xkmc.relicthespire.content.items.triggered;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.registrate.RtSEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BagOfMarbles extends BaseRelicItem {

	private static int dur() {
		return RtSModConfig.COMMON.curios.bagOfMarblesDuration.get();
	}

	public BagOfMarbles(Properties prop) {
		super(prop);
	}

	@Override
	public void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity target) {
		inflictActive(RtSEffect.FRAGILE.get(), dur(), 0, target, self);
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.JOIN_COMBAT.yellow());
		list.add(RtSLang.Effects.EFFECT_TARGET.bullet(RtSLang.num(dur())));
	}

}
