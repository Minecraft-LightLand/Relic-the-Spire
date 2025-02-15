package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2serial.util.Wrappers;
import dev.xkmc.relicthespire.content.items.util.TokenRelicComponent;
import dev.xkmc.relicthespire.init.data.RtSLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.function.BiConsumer;

public interface IBaseRelicItem {

	static <T> void onSearch(LivingEntity self, Class<T> cls, BiConsumer<ItemStack, T> cons) {
		for (var e : EquipmentSlot.values()) {
			var stack = self.getItemBySlot(e);
			var item = stack.getItem();
			if (item instanceof IBaseRelicItem es && es.isValidSlot(e) && cls.isInstance(item)) {
				cons.accept(stack, Wrappers.cast(item));
			}
		}
		CuriosApi.getCuriosInventory(self).resolve()
				.ifPresent(cap -> cap.findCurios(s -> cls.isInstance(s.getItem()))
						.forEach(s -> cons.accept(s.stack(), Wrappers.cast(s.stack().getItem()))));
	}

	ResourceLocation getId();

	void addText(List<Component> list, ItemStack stack);

	@Nullable
	TokenRelicComponent<?> getToken();

	default boolean isEnabled() {
		return true;
	}

	default boolean specialTooltip(ItemStack stack) {
		return false;
	}

	default boolean isValidSlot(EquipmentSlot slot) {
		return false;
	}

	default void tick(ItemStack stack, LivingEntity user) {
		if (getToken() != null) {
			getToken().tick(stack, user);
		}
	}

	// default implementation

	default void appendHoverTextImpl(ItemStack stack, @Nullable Level level, List<Component> list) {
		if (!isEnabled()) {
			list.add(RtSLang.Tooltip.BAN.get().withStyle(ChatFormatting.RED));
			return;
		}
		if (!specialTooltip(stack) && TooltipHelper.showLore(level)) {
			var id = getId();
			list.add(RtSLang.translate(id.getNamespace() + ".item_lore." + id.getPath())
					.withStyle(ChatFormatting.GRAY));
			list.add(RtSLang.Tooltip.SHIFT.shift());
		}
		if (specialTooltip(stack) || TooltipHelper.showDesc(level))
			addText(list, stack);
	}

	// Utilities

	default void inflictAmbient(MobEffect eff, int amp, LivingEntity target, LivingEntity user) {
		EffectUtil.refreshEffect(target, new MobEffectInstance(eff, 40, amp,
				true, false, true), EffectUtil.AddReason.NONE, user);
	}

	default void inflictActive(MobEffect eff, int dur, int amp, LivingEntity target, LivingEntity user) {
		EffectUtil.addEffect(target, new MobEffectInstance(eff, dur, amp,
				false, true, true), EffectUtil.AddReason.NONE, user);
	}

}
