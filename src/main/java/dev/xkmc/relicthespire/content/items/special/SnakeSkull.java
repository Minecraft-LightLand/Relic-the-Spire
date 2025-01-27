package dev.xkmc.relicthespire.content.items.special;

import dev.xkmc.l2library.base.effects.EffectBuilder;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class SnakeSkull extends BaseRelicItem {

	public SnakeSkull(Properties prop) {
		super(prop);
	}

	public static void trigger(Player player, MobEffectInstance ins) {
		var item = RtSItems.SNAKE_SKULL.get();
		if (!item.isEnabled()) return;
		CuriosApi.getCuriosInventory(player).resolve().flatMap(e -> e.findFirstCurio(item))
				.ifPresent(ctx -> new EffectBuilder(ins).setAmplifier(ins.getAmplifier() + 1));
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Effects.EFFECT_BOOST.gray(RtSLang.effect(MobEffects.POISON, 0)));
	}

}
