package dev.xkmc.relicthespire.content.items.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ITriggerRelicItem extends IBaseRelicItem {

	default void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity target) {

	}

	default void onEnterCombatMode(ItemStack stack, LivingEntity self) {

	}

	default void killLastTarget(ItemStack stack, LivingEntity self) {
	}

}
