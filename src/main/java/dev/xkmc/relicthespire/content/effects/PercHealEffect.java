package dev.xkmc.relicthespire.content.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class PercHealEffect extends InstantEffect {

	public PercHealEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyInstantenousEffect(@Nullable Entity self, @Nullable Entity owner, LivingEntity target, int amp, double dist) {
		if (dist == 1) {
			target.heal(target.getMaxHealth() * 0.2f * (1 + amp));
		}
	}
}
