package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.relicthespire.content.items.util.TokenRelicComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public abstract class BaseRelicItem extends Item implements ICurioItem, IBaseRelicItem {

	@Nullable
	private final TokenRelicComponent<?> token;

	public BaseRelicItem(Properties prop) {
		this(prop, null);
	}

	public BaseRelicItem(Properties prop, @Nullable TokenRelicComponent<?> token) {
		super(prop.stacksTo(1));
		this.token = token;
	}

	@Override
	public ResourceLocation getId() {
		return builtInRegistryHolder().key().location();
	}

	@Override
	public @Nullable TokenRelicComponent<?> getToken() {
		return token;
	}

	@Override
	public final void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		appendHoverTextImpl(stack, level, list);
	}

	@Override
	public final void curioTick(SlotContext slotContext, ItemStack stack) {
		if (!isEnabled()) return;
		tick(stack, slotContext.entity());
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int index, boolean sel) {
		if (index <= 36 && !sel || !isEnabled() || !(entity instanceof LivingEntity le)) return;
		for (var e : EquipmentSlot.values()) {
			if (isValidSlot(e) && le.getItemBySlot(e) == stack) {
				tick(stack, le);
				return;
			}
		}
	}

}
