package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class FlameDamageEffect extends InstantEffect {

	public FlameDamageEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyInstantenousEffect(@Nullable Entity self, @Nullable Entity owner, LivingEntity target, int amp, double dist) {
		if (dist == 1) {
			var source = new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
					.getOrThrow(DamageTypes.FIREBALL), self, owner);
			target.hurt(source, 20);
		}
	}
}
