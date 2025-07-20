package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2library.serial.ingredients.PotionIngredient;
import dev.xkmc.relicthespire.content.effects.*;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class RtSEffect {

	public static final RegistryEntry<FragileEffect> FRAGILE = genEffect("fragile",
			() -> new FragileEffect(MobEffectCategory.HARMFUL, 0xce6274),
			"Increase the damage you take");

	public static final RegistryEntry<ThornEffect> THORN = genEffect("thorns",
			() -> new ThornEffect(MobEffectCategory.BENEFICIAL, 0xc5f0e5),
			"Deal damage to attacker when attacked with direct damage");


	public static final RegistryEntry<PowerEffect> POWER = genEffect("power",
			() -> new PowerEffect(MobEffectCategory.BENEFICIAL, 0xce6274),
			"Increase attack damage by 1");

	public static final RegistryEntry<PercHealEffect> BLOOD = genEffect("blood",
			() -> new PercHealEffect(MobEffectCategory.BENEFICIAL, 0xce6274),
			"Heal by 20%");

	public static final RegistryEntry<ApplyWeakEffect> WEAK = genEffect("weak",
			() -> new ApplyWeakEffect(MobEffectCategory.HARMFUL, 0xce6274),
			"Increase Weakness duration by 45 seconds on target hit");

	public static final RegistryEntry<ApplyFragileEffect> FEAR = genEffect("fear",
			() -> new ApplyFragileEffect(MobEffectCategory.HARMFUL, 0xce6274),
			"Increase Fragile duration by 45 seconds on target hit");

	public static final RegistryEntry<ExplosionDamageEffect> EXPLOSIVE = genEffect("explosive",
			() -> new ExplosionDamageEffect(MobEffectCategory.HARMFUL, 0xce6274),
			"Deal 10 damage in range");

	public static final RegistryEntry<FlameDamageEffect> FLAME = genEffect("flame",
			() -> new FlameDamageEffect(MobEffectCategory.HARMFUL, 0xce6274),
			"Deal 20 damage on target hit");

	public static final RegistryEntry<Potion> BLOOD_POTION = regPotion("blood", BLOOD);
	public static final RegistryEntry<Potion> FLEX_POTION = regPotion("flex", POWER, 300, 4);
	public static final RegistryEntry<Potion> WEAK_POTION = regPotion("weak", WEAK);
	public static final RegistryEntry<Potion> FEAR_POTION = regPotion("fear", FEAR);
	public static final RegistryEntry<Potion> EXPLOSIVE_POTION = regPotion("explosive", EXPLOSIVE);
	public static final RegistryEntry<Potion> FLAME_POTION = regPotion("flame", FLAME);

	private static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup, String desc) {
		return RelicTheSpire.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register();
	}

	private static RegistryEntry<Potion> regPotion(String id, RegistryEntry<? extends MobEffect> eff, int time, int amp) {
		return RelicTheSpire.REGISTRATE.simple(id, Registries.POTION, () -> new Potion(
				new MobEffectInstance(eff.get(), time, amp)
		));
	}

	private static RegistryEntry<Potion> regPotion(String id, RegistryEntry<? extends MobEffect> eff) {
		return regPotion(id, eff, 1, 0);
	}

	public static void registerPotionRecipes() {
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				new PotionIngredient(Potions.WATER),
				Ingredient.of(RtSItems.BLIGHT_SHARD),
				RtSItems.CORRUPT_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(Items.PHANTOM_MEMBRANE),
				RtSItems.WEAK_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				RtSItems.FEAR_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(Items.GUNPOWDER),
				RtSItems.EXPLOSIVE_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(Items.MAGMA_CREAM),
				RtSItems.FLAME_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(Items.APPLE),
				RtSItems.BLOOD_POTION.get().getDefaultInstance()
		));

		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(
				Ingredient.of(RtSItems.CORRUPT_POTION),
				Ingredient.of(ItemTags.WOOL),
				RtSItems.FLEX_POTION.get().getDefaultInstance()
		));

	}

	public static void register() {

	}

}
