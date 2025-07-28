package dev.xkmc.relicthespire.content.items.potion;

import dev.xkmc.l2library.base.effects.EffectBuilder;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.ArrayList;

public class DoublePotionRecipe implements IBrewingRecipe {

	@Override
	public boolean isInput(ItemStack input) {
		return input.is(RtSItems.ODDLY_SMOOTH_STONE.get());
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.is(RtSItems.ODDLY_SMOOTH_STONE.get());
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		var effs = PotionUtils.getMobEffects(input);
		var col = PotionUtils.getColor(input);
		var name = input.getHoverName();
		var ans = new ItemStack(input.getItem());
		var list = new ArrayList<MobEffectInstance>();
		for (var e : effs) {
			list.add(new EffectBuilder(new MobEffectInstance(e)).setDuration(e.getDuration() * 2).ins);
		}
		PotionUtils.setCustomEffects(ans, list);
		ans.getOrCreateTag().putInt("CustomPotionColor", col);
		ans.setHoverName(name);
		return ans;
	}

}
