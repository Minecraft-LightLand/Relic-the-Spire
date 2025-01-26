package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.BiConsumer;

public interface IAttackRelicItem extends IBaseRelicItem {

	static void onTrigger(LivingEntity self, BiConsumer<ItemStack, IAttackRelicItem> cons) {
		CuriosApi.getCuriosInventory(self).resolve()
				.ifPresent(cap -> cap.findCurios(s -> s.getItem() instanceof IAttackRelicItem)
						.forEach(s -> cons.accept(s.stack(), (IAttackRelicItem) s.stack().getItem())));
	}

	default void onAttackTarget(ItemStack stack, AttackCache cache) {

	}

	default void onDamaged(ItemStack stack, AttackCache cache) {

	}

}
