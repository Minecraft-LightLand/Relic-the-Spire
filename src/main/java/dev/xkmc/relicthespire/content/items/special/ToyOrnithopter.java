package dev.xkmc.relicthespire.content.items.special;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class ToyOrnithopter extends BaseRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.toyOrithopterHeal.get();
	}

	public static void trigger(LivingEntity player) {
		var item = RtSItems.TOY_ORNITHOPTER.get();
		if (!item.isEnabled()) return;
		CuriosApi.getCuriosInventory(player).resolve().flatMap(e -> e.findFirstCurio(item))
				.ifPresent(e -> player.heal((float) amount()));
	}

	public ToyOrnithopter(Properties prop) {
		super(prop);
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.USE_POTION.yellow());
		list.add(RtSLang.Effects.HEAL.bullet(RtSLang.num(amount())));
	}


}
