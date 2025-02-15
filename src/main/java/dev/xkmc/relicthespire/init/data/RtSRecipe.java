package dev.xkmc.relicthespire.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.BiFunction;

public class RtSRecipe {

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RtSItems.BLIGHT_BLOCK, 4)::unlockedBy, RtSItems.BLIGHT_SHARD.get())
				.pattern("AA").pattern("AA")
				.define('A', RtSItems.BLIGHT_SHARD)
				.save(pvd);

		unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, RtSItems.GREEN_TORCH, 4)::unlockedBy, Items.COPPER_INGOT)
				.pattern("C").pattern("I").pattern("S")
				.define('C', ItemTags.COALS)
				.define('I', Tags.Items.INGOTS_COPPER)
				.define('S', Tags.Items.RODS_WOODEN)
				.save(pvd);
	}


	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
