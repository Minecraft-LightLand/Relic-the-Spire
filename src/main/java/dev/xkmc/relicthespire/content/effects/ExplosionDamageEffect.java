package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ExplosionDamageEffect extends InstantEffect {

	public ExplosionDamageEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyInstantenousEffect(@Nullable Entity self, @Nullable Entity owner, LivingEntity target, int amp, double dist) {
		var source = new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
				.getOrThrow(DamageTypes.EXPLOSION), self, owner);
		target.hurt(source, 10);
	}

}
