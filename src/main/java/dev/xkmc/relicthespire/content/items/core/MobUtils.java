package dev.xkmc.relicthespire.content.items.core;

import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.data.RtSTagGen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MobUtils {

	public static double start() {
		return RtSModConfig.COMMON.misc.eliteStartHealth.get();
	}

	public static double end() {
		return RtSModConfig.COMMON.misc.eliteEndHealth.get();
	}

	public static boolean isElite(LivingEntity target) {
		if (target.getType().is(RtSTagGen.ELITE_WHITELIST))
			return true;
		if (target.getType().is(RtSTagGen.ELITE_BLACKLIST))
			return false;
		var attr = target.getAttribute(Attributes.MAX_HEALTH);
		if (attr == null) return false;
		double base = attr.getBaseValue();
		return base >= start() && base <= end();
	}

}
