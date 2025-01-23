package dev.xkmc.relicthespire.content.items.triggered;

import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BloodVial extends BaseRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.bloodVialHeal.get();
	}

	private static int cd() {
		return RtSModConfig.COMMON.curios.bloodVialCoolDown.get();
	}

	public BloodVial(Properties prop) {
		super(prop);
	}

	@Override
	public void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity cache) {
		if (self instanceof Player player) {
			var cd = player.getCooldowns();
			if (cd.isOnCooldown(this))
				return;
			cd.addCooldown(this, cd());
			self.heal((float) amount());
		}
	}

	@Override
	protected void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.JOIN_COMBAT.yellow());
		list.add(RtSLang.Effects.HEAL.bullet(RtSLang.num(amount())));
		list.add(RtSLang.Effects.CD.gray(RtSLang.num(cd() / 20d)));
	}
}
