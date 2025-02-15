package dev.xkmc.relicthespire.content.items.attack;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.relicthespire.content.capability.BattleTracker;
import dev.xkmc.relicthespire.content.items.core.BlockRelicItem;
import dev.xkmc.relicthespire.content.items.core.IAttackRelicItem;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class Akabeko extends BlockRelicItem implements IAttackRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.akabekoDamageAdd.get();
	}

	public Akabeko(Block block, Properties prop) {
		super(block, prop);
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.FIRST_ATTACK.gray());
		list.add(RtSLang.Effects.DAMAGE_BOOST.bulletBlue(RtSLang.num(amount())));
	}

	@Override
	public void onAttackTarget(ItemStack stack, AttackCache cache) {
		if (cache.getAttacker() instanceof Player player &&
				player.getCapability(BattleTracker.CAPABILITY).resolve().map(e ->
						e.getAttackCount() == 1 && e.isValidAttackStamp(player.level())
				).orElse(false)) {
			cache.addHurtModifier(DamageModifier.add((float) amount()));
		}
	}

}
