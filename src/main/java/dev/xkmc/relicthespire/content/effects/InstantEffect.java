package dev.xkmc.relicthespire.content.effects;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class InstantEffect extends InherentEffect {

	protected InstantEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean isInstantenous() {
		return true;
	}

}
