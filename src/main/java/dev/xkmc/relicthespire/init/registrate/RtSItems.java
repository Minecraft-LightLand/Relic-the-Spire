package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.l2modularblock.BlockProxy;
import dev.xkmc.l2modularblock.DelegateBlock;
import dev.xkmc.l2modularblock.type.BlockMethod;
import dev.xkmc.relicthespire.content.block.BaseHorizontalBlock;
import dev.xkmc.relicthespire.content.block.ModTorchBlock;
import dev.xkmc.relicthespire.content.block.ModWallTorchBlock;
import dev.xkmc.relicthespire.content.block.WallHorizontalBlock;
import dev.xkmc.relicthespire.content.items.attack.Akabeko;
import dev.xkmc.relicthespire.content.items.attack.PenNib;
import dev.xkmc.relicthespire.content.items.attack.TheBoot;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.content.items.core.BlockRelicItem;
import dev.xkmc.relicthespire.content.items.special.PotionBelt;
import dev.xkmc.relicthespire.content.items.special.SnakeSkull;
import dev.xkmc.relicthespire.content.items.special.ToyOrnithopter;
import dev.xkmc.relicthespire.content.items.ticking.*;
import dev.xkmc.relicthespire.content.items.trigger.BagOfMarbles;
import dev.xkmc.relicthespire.content.items.trigger.BloodVial;
import dev.xkmc.relicthespire.content.items.trigger.BurningBlood;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class RtSItems {

	public static final RegistryEntry<CreativeModeTab> TAB;
	public static final ItemEntry<Item> BLIGHT_SHARD;
	public static final BlockEntry<DropExperienceBlock> BLIGHT_BLOCK;
	public static final RegistryEntry<SimpleParticleType> BLIGHT_FLAME;
	public static final BlockEntry<ModTorchBlock> GREEN_TORCH;
	public static final BlockEntry<ModWallTorchBlock> GREEN_WALL_TORCH;

	public static final List<String> ALL_CURIOS = new ArrayList<>();

	public static final ItemEntry<BurningBlood> BURNING_BLOOD;
	public static final ItemEntry<BagOfMarbles> BAG_OF_MARBLES;
	public static final ItemEntry<BloodVial> BLOOD_VIAL;
	public static final ItemEntry<BronzeScales> BRONZE_SCALES;
	public static final ItemEntry<TheBoot> THE_BOOT;
	public static final ItemEntry<OddlySmoothStone> ODDLY_SMOOTH_STONE;
	public static final ItemEntry<Orichalcum> ORICHALCUM;
	public static final ItemEntry<RedSkull> RED_SKULL;
	public static final ItemEntry<SnakeSkull> SNAKE_SKULL;
	public static final ItemEntry<PotionBelt> POTION_BELT;
	public static final ItemEntry<Vajra> VAJRA;
	//public static final ItemEntry<PreservedInsect> PRESERVED_INSECT;
	public static final ItemEntry<Akabeko> AKABEKO;
	public static final ItemEntry<PenNib> PEN_NIB;
	public static final ItemEntry<ToyOrnithopter> TOY_ORNITHOPTER;

	static {
		TAB = RelicTheSpire.REGISTRATE.buildModCreativeTab("items", "Relic the Spire", e ->
				e.icon(RtSItems.BLOOD_VIAL::asStack));

		{
			BLIGHT_SHARD = RelicTheSpire.REGISTRATE.item("blight_shard", Item::new).register();
			BLIGHT_BLOCK = RelicTheSpire.REGISTRATE.block("blight_block",
							p -> new DropExperienceBlock(p, UniformInt.of(2, 5)))
					.initialProperties(() -> Blocks.NETHER_QUARTZ_ORE)
					.simpleItem()
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
							.add(AlternativesEntry.alternatives(
									LootTableTemplate.getItem(block.asItem(), 1).when(
											LootTableTemplate.silk(false)),
									LootTableTemplate.getItem(block.asItem(), 1).when(
											BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE,
													0.05f, 0.065f, 0.08f, 0.1f)
									))))))
					.register();

			BLIGHT_FLAME = RelicTheSpire.REGISTRATE.simple("green_flame", ForgeRegistries.Keys.PARTICLE_TYPES,
					() -> new SimpleParticleType(false));
			GREEN_TORCH = RelicTheSpire.REGISTRATE.block("green_torch",
							p -> new ModTorchBlock(p, BLIGHT_FLAME::get))
					.initialProperties(() -> Blocks.TORCH).properties(p -> p.lightLevel(s -> 12))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(),
							pvd.models().torch(ctx.getName(), pvd.modLoc("block/green_torch"))
									.renderType("cutout")))
					.register();
			GREEN_WALL_TORCH = RelicTheSpire.REGISTRATE.block("green_wall_torch",
							p -> new ModWallTorchBlock(p, BLIGHT_FLAME::get))
					.initialProperties(() -> Blocks.WALL_TORCH).properties(p -> p.lightLevel(s -> 12))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(),
							pvd.models().torchWall(ctx.getName(), pvd.modLoc("block/green_torch"))
									.renderType("cutout")))
					.setData(ProviderType.LANG, NonNullBiConsumer.noop())
					.register();
			RelicTheSpire.REGISTRATE.item("green_torch", p -> new StandingAndWallBlockItem(
							GREEN_TORCH.get(), GREEN_WALL_TORCH.get(), p, Direction.DOWN))
					.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("block/" + ctx.getName())))
					.setData(ProviderType.LANG, NonNullBiConsumer.noop())
					.register();
		}

		{

			BURNING_BLOOD = curio("burning_blood", BurningBlood::new, "body");
			BRONZE_SCALES = curio("bronze_scales", BronzeScales::new, "body");

			THE_BOOT = curio("the_boot", TheBoot::new, "feet");

			RED_SKULL = curioBlock("red_skull", RedSkull::new, "head", RtSItems::skullModel,
					BlockProxy.HORIZONTAL, WallHorizontalBlock.of(4, 0, 4, 4, 4, 8, 8, 8, 8));
			SNAKE_SKULL = curioBlock("snake_skull", SnakeSkull::new, "head", RtSItems::skullModel,
					BlockProxy.HORIZONTAL, WallHorizontalBlock.of(4, 0, 2, 4, 4, 6, 8, 8, 10));

			BAG_OF_MARBLES = curio("bag_of_marbles", BagOfMarbles::new, "belt");
			BLOOD_VIAL = curio("blood_vial", BloodVial::new, "belt");
			POTION_BELT = curio("potion_belt", PotionBelt::new, "belt");
			VAJRA = curio("vajra", Vajra::new, "belt");

			ODDLY_SMOOTH_STONE = curioBlock("oddly_smooth_stone", OddlySmoothStone::new, "charm", RtSItems::horizontalModel,
					BlockProxy.HORIZONTAL, BaseHorizontalBlock.of(5, 0, 6, 11, 2, 10));
			ORICHALCUM = curio("orichalcum", Orichalcum::new, "charm");
			//PRESERVED_INSECT = reg("preserved_insect", PreservedInsect::new, "charm");
			AKABEKO = curioBlock("akabeko", Akabeko::new, "charm", RtSItems::horizontalModel,
					BlockProxy.HORIZONTAL, BaseHorizontalBlock.of(5, 0, 1, 11, 8, 15));
			PEN_NIB = curio("pen_nib", PenNib::new, "charm");
			TOY_ORNITHOPTER = curio("toy_ornithopter", ToyOrnithopter::new, "charm");
		}
	}

	private static <T extends BaseRelicItem> ItemEntry<T> curio(String id, NonNullFunction<Item.Properties, T> factory, String slot) {
		ALL_CURIOS.add(id);
		return RelicTheSpire.REGISTRATE.item(id, factory)
				.tag(ItemTags.create(new ResourceLocation("curios", slot)))
				.register();
	}


	private static <T extends BlockRelicItem> ItemEntry<T> curioBlock(
			String id, NonNullBiFunction<Block, Item.Properties, T> factory, String slot,
			NonNullBiConsumer<DataGenContext<Block, DelegateBlock>, RegistrateBlockstateProvider> model,
			BlockMethod... methods) {
		ALL_CURIOS.add(id);
		var prop = BlockBehaviour.Properties.of().strength(1).pushReaction(PushReaction.DESTROY);
		var builder = RelicTheSpire.REGISTRATE.block(id, p ->
				DelegateBlock.newBaseBlock(prop, methods)).blockstate(model);
		builder.tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
		var item = builder.item(factory);
		return item.model((ctx, pvd) -> pvd.generated(ctx))
				.tag(ItemTags.create(new ResourceLocation("curios", slot)))
				.register();
	}

	private static void horizontalModel(DataGenContext<Block, DelegateBlock> ctx, RegistrateBlockstateProvider pvd) {
		pvd.horizontalBlock(ctx.get(), pvd.models().getBuilder("block/" + ctx.getName())
				.parent(new ModelFile.UncheckedModelFile(pvd.modLoc("custom/" + ctx.getName())))
				.texture("all", pvd.modLoc("block/" + ctx.getName()))
				.renderType("cutout"));
	}

	private static void skullModel(DataGenContext<Block, DelegateBlock> ctx, RegistrateBlockstateProvider pvd) {
		var ground = pvd.models().getBuilder("block/" + ctx.getName())
				.parent(new ModelFile.UncheckedModelFile(pvd.modLoc("custom/" + ctx.getName())))
				.texture("all", pvd.modLoc("block/" + ctx.getName()))
				.renderType("cutout");
		var wall = pvd.models().getBuilder("block/" + ctx.getName() + "_wall")
				.parent(new ModelFile.UncheckedModelFile(pvd.modLoc("custom/" + ctx.getName() + "_wall")))
				.texture("all", pvd.modLoc("block/" + ctx.getName()))
				.renderType("cutout");
		pvd.horizontalBlock(ctx.get(), e -> e.getValue(WallHorizontalBlock.WALL) ? wall : ground);
	}

	public static void register() {

	}

}
