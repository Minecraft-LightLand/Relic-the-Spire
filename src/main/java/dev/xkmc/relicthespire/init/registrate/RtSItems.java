package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.relicthespire.content.items.attack.TheBoot;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.special.SnakeSkull;
import dev.xkmc.relicthespire.content.items.ticking.RedSkull;
import dev.xkmc.relicthespire.content.items.ticking.Vajra;
import dev.xkmc.relicthespire.content.items.trigger.BagOfMarbles;
import dev.xkmc.relicthespire.content.items.trigger.BloodVial;
import dev.xkmc.relicthespire.content.items.trigger.BurningBlood;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class RtSItems {

	public static final List<String> ALL_CURIOS = new ArrayList<>();

	public static final ItemEntry<BurningBlood> BURNING_BLOOD;
	public static final ItemEntry<BagOfMarbles> BAG_OF_MARBLES;
	public static final ItemEntry<BloodVial> BLOOD_VIAL;
	public static final ItemEntry<TheBoot> THE_BOOT;
	public static final ItemEntry<RedSkull> RED_SKULL;
	public static final ItemEntry<SnakeSkull> SNAKE_SKULL;
	public static final ItemEntry<Vajra> VAJRA;

	static {
		RelicTheSpire.REGISTRATE.buildModCreativeTab("items", "Relic the Spire", e ->
				e.icon(RtSItems.BLOOD_VIAL::asStack));

		BURNING_BLOOD = reg("burning_blood", BurningBlood::new, "body");
		BAG_OF_MARBLES = reg("bag_of_marbles", BagOfMarbles::new, "belt");
		BLOOD_VIAL = reg("blood_vial", BloodVial::new, "belt");
		THE_BOOT = reg("the_boot", TheBoot::new, "feet");
		RED_SKULL = reg("red_skull", RedSkull::new, "head");
		SNAKE_SKULL = reg("snake_skull", SnakeSkull::new, "head");
		VAJRA = reg("vajra", Vajra::new, "hands");
	}

	private static <T extends BaseRelicItem> ItemEntry<T> reg(String id, NonNullFunction<Item.Properties, T> factory, String slot) {
		ALL_CURIOS.add(id);
		return RelicTheSpire.REGISTRATE.item(id, factory)
				.tag(ItemTags.create(new ResourceLocation("curios", slot)))
				.register();
	}

	public static void register() {

	}

}
