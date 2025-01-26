package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.relicthespire.content.effects.FragileEffect;
import dev.xkmc.relicthespire.content.effects.ThornEffect;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RtSEffect {

	public static final RegistryEntry<FragileEffect> FRAGILE = genEffect("fragile",
			() -> new FragileEffect(MobEffectCategory.HARMFUL, 0x000000),
			"Increase the damage you take");

	public static final RegistryEntry<ThornEffect> THORN = genEffect("thorn",
			() -> new ThornEffect(MobEffectCategory.BENEFICIAL, 0x000000),
			"Deal damage to attacker when attacked with direct damage");

	private static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup, String desc) {
		return RelicTheSpire.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register();
	}

	public static void register() {

	}

}
