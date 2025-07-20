package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2library.base.effects.EffectBuilder;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ApplyWeakEffect extends InstantEffect {

	public ApplyWeakEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyInstantenousEffect(@Nullable Entity self, @Nullable Entity owner, LivingEntity target, int amp, double dist) {
		if (dist == 1) {
			var old = target.getEffect(MobEffects.WEAKNESS);
			if (old == null) target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, amp));
			else target.addEffect(new EffectBuilder(old).setDuration(old.getDuration() + 900)
					.setAmplifier(Math.max(old.getAmplifier(), amp)).ins);
		}
	}
}
