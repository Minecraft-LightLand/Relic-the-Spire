package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import net.minecraft.world.item.ItemStack;

public interface IAttackRelicItem extends IBaseRelicItem {

	default void onAttackTarget(ItemStack stack, AttackCache cache) {

	}

	default void onDamaged(ItemStack stack, AttackCache cache) {

	}

}
