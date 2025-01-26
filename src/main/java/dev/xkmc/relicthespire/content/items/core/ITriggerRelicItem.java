package dev.xkmc.relicthespire.content.items.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.BiConsumer;

public interface ITriggerRelicItem extends IBaseRelicItem {

	static void onTrigger(LivingEntity self, BiConsumer<ItemStack, ITriggerRelicItem> cons) {
		CuriosApi.getCuriosInventory(self).resolve()
				.ifPresent(cap -> cap.findCurios(s -> s.getItem() instanceof ITriggerRelicItem)
						.forEach(s -> cons.accept(s.stack(), (ITriggerRelicItem) s.stack().getItem())));
	}

	default void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity target) {

	}

	default void onEnterCombatMode(ItemStack stack, LivingEntity self) {

	}

	default void killLastTarget(ItemStack stack, LivingEntity self) {
	}

}
