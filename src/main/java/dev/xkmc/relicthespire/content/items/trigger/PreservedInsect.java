package dev.xkmc.relicthespire.content.items.trigger;

import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.core.ITriggerRelicItem;
import dev.xkmc.relicthespire.content.items.core.MobUtils;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class PreservedInsect extends BaseRelicItem implements ITriggerRelicItem {

	private static double amount() {
		return RtSModConfig.COMMON.curios.preservedInsectDebuff.get();
	}

	public PreservedInsect(Properties prop) {
		super(prop);
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.JOIN_COMBAT_ELITE.gray());
		list.add(RtSLang.Effects.REDUCE_HEALTH.bullet(RtSLang.perc(amount())));
		list.add(RtSLang.Tooltip.ELITE.gray(
				RtSLang.num(MobUtils.start()),
				RtSLang.num(MobUtils.end())
		));
	}

	@Override
	public void onMobJoinBattle(ItemStack stack, LivingEntity self, LivingEntity target) {
		if (!MobUtils.isElite(target)) return;
		var ins = target.getAttribute(Attributes.MAX_HEALTH);
		if (ins == null) return;
		UUID id = MathHelper.getUUIDFromString("preserved_insect");
		if (ins.getModifier(id) != null) return;
		ins.addPermanentModifier(new AttributeModifier(id, "preserved_insect", -amount(),
				AttributeModifier.Operation.MULTIPLY_TOTAL));
		if (target.getHealth() > target.getMaxHealth()) {
			target.setHealth(target.getMaxHealth());
		}
	}

}
