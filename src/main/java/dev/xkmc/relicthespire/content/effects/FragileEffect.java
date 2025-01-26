package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.relicthespire.init.registrate.RtSEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FragileEffect extends InherentEffect {

	public FragileEffect(MobEffectCategory category, int color) {
		super(category, color);
		String uuid = MathHelper.getUUIDFromString("fragile").toString();
		addAttributeModifier(L2DamageTracker.REDUCTION.get(), uuid, 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	public static void inflictActive(int dur, LivingEntity target, LivingEntity user) {
		int amp = 1;
		//TODO modify amp
		EffectUtil.addEffect(target, new MobEffectInstance(RtSEffect.FRAGILE.get(), dur, amp,
				false, true, true), EffectUtil.AddReason.NONE, user);
	}

}
