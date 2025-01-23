package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.BiConsumer;

public class BaseRelicItem extends Item implements ICurioItem {

	public static void onTrigger(LivingEntity self, BiConsumer<ItemStack, BaseRelicItem> cons) {
		CuriosApi.getCuriosInventory(self).resolve()
				.ifPresent(cap -> cap.findCurios(s -> s.getItem() instanceof BaseRelicItem)
						.forEach(s -> cons.accept(s.stack(), (BaseRelicItem) s.stack().getItem())));
	}

	public BaseRelicItem(Properties prop) {
		super(prop.stacksTo(1));
	}

	@Override
	public final void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		addText(list, stack);
	}

	@Override
	public final void curioTick(SlotContext slotContext, ItemStack stack) {
		tick(stack, slotContext.entity());
	}

	protected void addText(List<Component> list, ItemStack stack) {
	}

	protected void tick(ItemStack stack, LivingEntity user) {

	}

	public void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity target) {

	}

	public void onEnterCombatMode(ItemStack stack, LivingEntity self) {

	}

	public void killLastTarget(ItemStack stack, LivingEntity self) {
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
