package dev.xkmc.relicthespire.init.data;

import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class RtSTagGen {

	public static final TagKey<EntityType<?>> ELITE_WHITELIST = of(RelicTheSpire.loc("elite_whitelist"));
	public static final TagKey<EntityType<?>> ELITE_BLACKLIST = of(RelicTheSpire.loc("elite_blacklist"));

	private static TagKey<EntityType<?>> of(ResourceLocation id) {
		return TagKey.create(Registries.ENTITY_TYPE, id);
	}

}
