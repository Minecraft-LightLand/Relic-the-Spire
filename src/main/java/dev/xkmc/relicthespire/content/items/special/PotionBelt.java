package dev.xkmc.relicthespire.content.items.special;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PotionBelt extends BaseRelicItem {

	private static int getSlotCount(ItemStack belt) {
		return RtSModConfig.COMMON.curios.potionBeltMaxSlot.get();
	}

	private static int getStackMax(ItemStack belt) {
		return RtSModConfig.COMMON.curios.potionBeltMaxCount.get();
	}

	private static List<ItemStack> getItems(ItemStack belt) {
		var root = belt.getTag();
		if (root == null || !root.contains("Items", Tag.TAG_LIST))
			return new ArrayList<>();
		var arr = root.getList("Items", Tag.TAG_COMPOUND);
		List<ItemStack> ans = new ArrayList<>();
		for (int i = 0; i < arr.size(); i++) {
			var data = arr.getCompound(i);
			ItemStack stack = ItemStack.of(data);
			if (!stack.isEmpty()) {
				ans.add(stack);
			}
		}
		return ans;
	}

	private static void setItems(ItemStack belt, List<ItemStack> list) {
		var root = belt.getOrCreateTag();
		var arr = new ListTag();
		for (var e : list) {
			if (!e.isEmpty()) {
				arr.add(e.save(new CompoundTag()));
			}
		}
		root.put("Items", arr);
	}

	public PotionBelt(Properties prop) {
		super(prop);
	}

	@Override
	protected boolean specialTooltip(ItemStack stack) {
		return !getItems(stack).isEmpty();
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		var stacks = getItems(stack);
		if (stacks.isEmpty()) {
			list.add(RtSLang.Special.POTION_BELT_DESC.gray(
					RtSLang.num(getSlotCount(stack)),
					RtSLang.num(getStackMax(stack))
			));
			list.add(RtSLang.Special.POTION_BELT_USE.gray());
		} else {
			for (var e : stacks) {
				list.add(e.getDisplayName().copy().append(Component.literal(" x" + e.getCount())));
			}
		}
	}

	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack carried, Slot slot, ClickAction action, Player user, SlotAccess carriedAccess) {
		if (self.getCount() != 1) return false;
		if (action == ClickAction.SECONDARY && slot.allowModification(user)) {
			var list = getItems(self);
			if (carried.isEmpty()) {
				if (list.isEmpty()) return false;
				ItemStack ans = list.get(list.size() - 1);
				if (ans.isEmpty()) return false;
				ItemStack hand = ans.split(1);
				setItems(self, list);
				carriedAccess.set(hand);
				return true;
			} else {
				int max = getStackMax(self);
				int size = getSlotCount(self);
				for (var e : list) {
					if (ItemStack.isSameItemSameTags(e, carried)) {
						if (e.getCount() >= max) continue;
						carried.shrink(1);
						e.grow(1);
						return true;
					}
				}
				if (list.size() >= size) return false;
				list.add(carried.split(1));
				setItems(self, list);
				return true;
			}
		} else {
			return false;
		}
	}

}
