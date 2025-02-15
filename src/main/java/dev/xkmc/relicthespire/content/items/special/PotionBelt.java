package dev.xkmc.relicthespire.content.items.special;

import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.relicthespire.content.client.PotionTooltip;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PotionBelt extends BaseRelicItem {

	private static int getSlotCount(ItemStack belt) {
		return RtSModConfig.COMMON.curios.potionBeltMaxSlot.get();
	}

	private static int getStackMax(ItemStack belt) {
		return RtSModConfig.COMMON.curios.potionBeltMaxCount.get();
	}

	public static List<ItemStack> getItems(ItemStack belt) {
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
	public boolean specialTooltip(ItemStack stack) {
		return !getItems(stack).isEmpty();
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		var list = getItems(stack);
		if (list.isEmpty()) return Optional.empty();
		return Optional.of(new PotionTooltip(list));
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		var stacks = getItems(stack);
		if (stacks.isEmpty()) {
			list.add(RtSLang.Special.POTION_BELT_DESC.gray(
					RtSLang.num(getSlotCount(stack)),
					RtSLang.num(getStackMax(stack))
			));
			list.add(RtSLang.Special.POTION_BELT_USE.gray());
		} else {
			for (var e : stacks) {
				list.add(e.getHoverName().copy().withStyle(ChatFormatting.GRAY)
						.append(Component.literal(" x" + e.getCount()).withStyle(ChatFormatting.AQUA)));
			}
		}
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction action, Player player) {
		if (self.getCount() != 1) return false;
		if (action != ClickAction.SECONDARY || !slot.allowModification(player)) return false;
		ItemStack inSlot = slot.getItem();
		if (!inSlot.isEmpty() && !inSlot.is(Items.POTION) && !inSlot.is(Items.SPLASH_POTION) && !inSlot.is(Items.LINGERING_POTION))
			return false;
		var list = getItems(self);
		if (inSlot.isEmpty()) {
			if (list.isEmpty()) return false;
			ItemStack ans = list.get(list.size() - 1);
			if (ans.isEmpty()) return false;
			ItemStack removeOne = ans.split(1);
			setItems(self, list);
			slot.safeInsert(removeOne);
		} else {
			int max = getStackMax(self);
			int size = getSlotCount(self);
			for (var e : list) {
				if (ItemStack.isSameItemSameTags(e, inSlot)) {
					if (e.getCount() >= max) continue;
					inSlot.shrink(1);
					e.grow(1);
					slot.setChanged();
					setItems(self, list);
					return true;
				}
			}
			if (list.size() >= size) return false;
			list.add(inSlot.split(1));
			slot.setChanged();
			setItems(self, list);
		}
		return true;
	}

	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack carried, Slot slot, ClickAction action, Player user, SlotAccess carriedAccess) {
		if (self.getCount() != 1) return false;
		if (action != ClickAction.SECONDARY || !slot.allowModification(user)) return false;
		if (!carried.isEmpty() && !carried.is(Items.POTION) && !carried.is(Items.SPLASH_POTION) && !carried.is(Items.LINGERING_POTION))
			return false;
		var list = getItems(self);
		if (carried.isEmpty()) {
			if (list.isEmpty()) return false;
			ItemStack ans = list.get(list.size() - 1);
			if (ans.isEmpty()) return false;
			ItemStack hand = ans.split(1);
			setItems(self, list);
			carriedAccess.set(hand);
		} else {
			int max = getStackMax(self);
			int size = getSlotCount(self);
			for (var e : list) {
				if (ItemStack.isSameItemSameTags(e, carried)) {
					if (e.getCount() >= max) continue;
					carried.shrink(1);
					e.grow(1);
					setItems(self, list);
					return true;
				}
			}
			if (list.size() >= size) return false;
			list.add(carried.split(1));
			setItems(self, list);
		}
		return true;

	}

	private static ItemStack findReplacement(LivingEntity entity, ItemStack used) {
		var item = RtSItems.POTION_BELT.get();
		if (!item.isEnabled()) return ItemStack.EMPTY;
		var opt = CuriosApi.getCuriosInventory(entity).resolve().flatMap(e -> e.findFirstCurio(item));
		if (opt.isEmpty()) return ItemStack.EMPTY;
		var belt = opt.get().stack();
		var list = getItems(belt);
		if (list.isEmpty()) return ItemStack.EMPTY;
		for (var e : list) {
			if (ItemStack.isSameItemSameTags(e, used)) {
				var rep = e.split(1);
				setItems(belt, list);
				return rep;
			}
		}
		return ItemStack.EMPTY;
	}

	public static void onThrowPotion(Player entity, PlayerInteractEvent.RightClickItem event) {
		if (entity.level().isClientSide()) return;
		ItemStack used = event.getItemStack().copy();
		var hand = event.getHand();
		GeneralEventHandler.schedule(() -> {
			if (!entity.getItemInHand(hand).isEmpty()) return;
			ItemStack replace = findReplacement(entity, used);
			if (replace.isEmpty()) return;
			entity.setItemInHand(hand, replace);
			entity.getCooldowns().addCooldown(used.getItem(), 5);
		});
	}

	public static void consumePotion(LivingEntity entity, LivingEntityUseItemEvent.Finish event) {
		if (entity.level().isClientSide()) return;
		var hand = entity.getUsedItemHand();
		ItemStack used = event.getItem().copy();
		GeneralEventHandler.schedule(() -> {
			ItemStack remain = entity.getItemInHand(hand);
			if (remain.isEmpty() || remain.is(Items.GLASS_BOTTLE)) {
				remain = remain.copy();
				ItemStack replace = findReplacement(entity, used);
				if (replace.isEmpty()) return;
				entity.setItemInHand(hand, replace);
				if (entity instanceof Player player) {
					if (!remain.isEmpty()) {
						player.getInventory().placeItemBackInInventory(remain);
					}
					player.getCooldowns().addCooldown(used.getItem(), 5);
					player.inventoryMenu.sendAllDataToRemote();
				}
			}
		});
	}

}
