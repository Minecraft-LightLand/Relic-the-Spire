package dev.xkmc.relicthespire.content.items.special;

import dev.xkmc.l2library.base.effects.EffectBuilder;
import dev.xkmc.relicthespire.content.items.core.BlockRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class SnakeSkull extends BlockRelicItem {

	public SnakeSkull(Block block, Properties prop) {
		super(block, prop);
	}

	public static void trigger(Player player, MobEffectInstance ins) {
		var item = RtSItems.SNAKE_SKULL.get();
		if (!item.isEnabled()) return;
		CuriosApi.getCuriosInventory(player).resolve().flatMap(e -> e.findFirstCurio(item))
				.ifPresent(ctx -> new EffectBuilder(ins).setAmplifier(ins.getAmplifier() + 1));
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Effects.EFFECT_BOOST.gray(RtSLang.effect(MobEffects.POISON, 0)));
	}

	@Override
	public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}

	@Override
	public boolean isValidSlot(EquipmentSlot slot) {
		return slot == EquipmentSlot.HEAD;
	}

}
