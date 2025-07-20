package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PowerEffect extends MobEffect {

	public PowerEffect(MobEffectCategory category, int color) {
		super(category, color);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, MathHelper.getUUIDFromString("power_effect").toString(),
				1, AttributeModifier.Operation.ADDITION);
	}

}
