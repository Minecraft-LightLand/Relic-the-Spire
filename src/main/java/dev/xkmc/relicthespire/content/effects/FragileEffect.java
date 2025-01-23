package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FragileEffect extends InherentEffect {

	public FragileEffect(MobEffectCategory category, int color) {
		super(category, color);
		String uuid = MathHelper.getUUIDFromString("fragile").toString();
		addAttributeModifier(L2DamageTracker.REDUCTION.get(), uuid, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

}
