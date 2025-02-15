package dev.xkmc.relicthespire.content.items.ticking;

import dev.xkmc.relicthespire.content.items.core.BlockRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class OddlySmoothStone extends BlockRelicItem {

	private static int amp() {
		return RtSModConfig.COMMON.curios.oddlySmoothStoneSpeedAmplifier.get();
	}

	public OddlySmoothStone(Block block, Properties prop) {
		super(block, prop);
	}

	@Override
	public void tick(ItemStack stack, LivingEntity user) {
		inflictAmbient(MobEffects.MOVEMENT_SPEED, amp(), user, user);
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Effects.EFFECT_SELF.gray(RtSLang.effect(MobEffects.MOVEMENT_SPEED, amp())));
	}

}
