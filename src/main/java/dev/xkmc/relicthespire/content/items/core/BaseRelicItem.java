package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.relicthespire.init.data.RtSLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public abstract class BaseRelicItem extends Item implements ICurioItem, IBaseRelicItem {

	public BaseRelicItem(Properties prop) {
		super(prop.stacksTo(1));
	}

	public boolean isEnabled() {
		return true;
	}

	@Override
	public final void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		if (!isEnabled()) {
			list.add(RtSLang.Tooltip.BAN.get().withStyle(ChatFormatting.RED));
			return;
		}
		if (!specialTooltip(stack) && TooltipHelper.showLore(level)) {
			var id = ForgeRegistries.ITEMS.getKey(this);
			if (id != null) {
				list.add(RtSLang.translate(id.getNamespace() + ".item_lore." + id.getPath()));
			}
		}
		if (specialTooltip(stack) || TooltipHelper.showDesc(level))
			addText(list, stack);
	}

	protected boolean specialTooltip(ItemStack stack) {
		return false;
	}

	@Override
	public final void curioTick(SlotContext slotContext, ItemStack stack) {
		if (!isEnabled()) return;
		tick(stack, slotContext.entity());
	}

	protected abstract void addText(List<Component> list, ItemStack stack);

	protected void tick(ItemStack stack, LivingEntity user) {

	}

	protected static void inflictAmbient(MobEffect eff, int amp, LivingEntity target, LivingEntity user) {
		EffectUtil.refreshEffect(target, new MobEffectInstance(eff, 40, amp,
				true, false, true), EffectUtil.AddReason.NONE, user);
	}

	protected static void inflictActive(MobEffect eff, int dur, int amp, LivingEntity target, LivingEntity user) {
		EffectUtil.addEffect(target, new MobEffectInstance(eff, dur, amp,
				false, true, true), EffectUtil.AddReason.NONE, user);
	}

}
