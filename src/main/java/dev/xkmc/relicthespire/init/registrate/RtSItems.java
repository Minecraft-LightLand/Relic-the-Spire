package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
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
import dev.xkmc.relicthespire.content.items.potion.RtSPotion;
import dev.xkmc.relicthespire.content.items.potion.RtSThrowablePotion;
import dev.xkmc.relicthespire.content.items.special.PotionBelt;
import dev.xkmc.relicthespire.content.items.special.SnakeSkull;
import dev.xkmc.relicthespire.content.items.special.ToyOrnithopter;
import dev.xkmc.relicthespire.content.items.ticking.*;
import dev.xkmc.relicthespire.content.items.trigger.BagOfMarbles;
import dev.xkmc.relicthespire.content.items.trigger.BloodVial;
import dev.xkmc.relicthespire.content.items.trigger.BurningBlood;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import dev.xkmc.relicthespire.init.data.RtSTagGen;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
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
	public static final BlockEntry<CarpetBlock> PLAIN_CARPET, DECORATED_CARPET;

	public static final ItemEntry<Item> CORRUPT_POTION;
	public static final ItemEntry<RtSPotion> BLOOD_POTION, FLEX_POTION;
	public static final ItemEntry<RtSThrowablePotion> WEAK_POTION, FEAR_POTION, EXPLOSIVE_POTION, FLAME_POTION;

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
					.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(),
							state -> pvd.models().torchWall(ctx.getName(), pvd.modLoc("block/green_torch"))
									.renderType("cutout"), 90))
					.setData(ProviderType.LANG, NonNullBiConsumer.noop())
					.register();
			RelicTheSpire.REGISTRATE.item("green_torch", p -> new StandingAndWallBlockItem(
							GREEN_TORCH.get(), GREEN_WALL_TORCH.get(), p, Direction.DOWN))
					.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("block/" + ctx.getName())))
					.setData(ProviderType.LANG, NonNullBiConsumer.noop())
					.register();

			PLAIN_CARPET = RelicTheSpire.REGISTRATE.block("plain_merchant_carpet", CarpetBlock::new)
					.blockstate((ctx, pvd) ->
							pvd.simpleBlock(ctx.get(), pvd.models().carpet(ctx.getName(), pvd.modLoc("block/" + ctx.getName()))))
					.tag(BlockTags.WOOL_CARPETS)
					.simpleItem()
					.register();

			DECORATED_CARPET = RelicTheSpire.REGISTRATE.block("decorated_merchant_carpet", CarpetBlock::new)
					.blockstate((ctx, pvd) ->
							pvd.simpleBlock(ctx.get(), pvd.models().carpet(ctx.getName(), pvd.modLoc("block/" + ctx.getName()))))
					.tag(BlockTags.WOOL_CARPETS)
					.simpleItem()
					.register();
		}

		{

			CORRUPT_POTION = RelicTheSpire.REGISTRATE.item("corrupt_potion", Item::new)
					.properties(p -> p.stacksTo(1))
					.register();

			BLOOD_POTION = RelicTheSpire.REGISTRATE.item("blood_potion",
							p -> new RtSPotion(p, RtSEffect.BLOOD_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();

			FLEX_POTION = RelicTheSpire.REGISTRATE.item("flex_potion",
							p -> new RtSPotion(p, RtSEffect.FLEX_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();

			WEAK_POTION = RelicTheSpire.REGISTRATE.item("weak_potion",
							p -> new RtSThrowablePotion(p, RtSEffect.WEAK_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();

			FEAR_POTION = RelicTheSpire.REGISTRATE.item("fear_potion",
							p -> new RtSThrowablePotion(p, RtSEffect.FEAR_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();

			EXPLOSIVE_POTION = RelicTheSpire.REGISTRATE.item("explosive_potion",
							p -> new RtSThrowablePotion(p, RtSEffect.EXPLOSIVE_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();

			FLAME_POTION = RelicTheSpire.REGISTRATE.item("flame_potion",
							p -> new RtSThrowablePotion(p, RtSEffect.FLAME_POTION))
					.properties(p -> p.stacksTo(1))
					.tab(TAB.getKey(), (a, b) -> b.accept(a.get().getDefaultInstance()))
					.tag(RtSTagGen.POTIONS)
					.register();
		}

		{

			BURNING_BLOOD = curio("burning_blood", BurningBlood::new, "body");
			BRONZE_SCALES = curio("bronze_scales", BronzeScales::new, "body");

			THE_BOOT = curio("the_boot", TheBoot::new, "feet");

			RED_SKULL = curioBlock("red_skull", RedSkull::new, "head",
					RtSItems::skullModel, RtSItems::head,
					BlockProxy.HORIZONTAL, WallHorizontalBlock.of(4, 0, 4, 4, 4, 8, 8, 8, 8));
			SNAKE_SKULL = curioBlock("snake_skull", SnakeSkull::new, "head",
					RtSItems::skullModel, RtSItems::head,
					BlockProxy.HORIZONTAL, WallHorizontalBlock.of(4, 0, 2, 4, 4, 6, 8, 8, 10));

			BAG_OF_MARBLES = curio("bag_of_marbles", BagOfMarbles::new, "belt");
			BLOOD_VIAL = curio("blood_vial", BloodVial::new, "belt");
			POTION_BELT = curio("potion_belt", PotionBelt::new, "belt");
			VAJRA = curio("vajra", Vajra::new, "belt", RtSItems::custom);

			ODDLY_SMOOTH_STONE = curioBlock("oddly_smooth_stone", OddlySmoothStone::new, "charm",
					RtSItems::horizontalModel, RtSItems::generated,
					BlockProxy.HORIZONTAL, BaseHorizontalBlock.of(5, 0, 6, 11, 2, 10));
			ORICHALCUM = curio("orichalcum", Orichalcum::new, "charm");
			//PRESERVED_INSECT = reg("preserved_insect", PreservedInsect::new, "charm");
			AKABEKO = curioBlock("akabeko", Akabeko::new, "charm",
					RtSItems::horizontalModel, RtSItems::generated,
					BlockProxy.HORIZONTAL, BaseHorizontalBlock.of(5, 0, 1, 11, 8, 15));
			PEN_NIB = curio("pen_nib", PenNib::new, "charm");
			TOY_ORNITHOPTER = curio("toy_ornithopter", ToyOrnithopter::new, "charm");
		}
	}

	private static <T extends BaseRelicItem> ItemEntry<T> curio(String id, NonNullFunction<Item.Properties, T> factory, String slot) {
		return curio(id, factory, slot, RtSItems::generated);
	}

	private static <T extends BaseRelicItem> ItemEntry<T> curio(
			String id, NonNullFunction<Item.Properties, T> factory, String slot,
			NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> itemModel
	) {
		ALL_CURIOS.add(id);
		return RelicTheSpire.REGISTRATE.item(id, factory).model(itemModel)
				.tag(ItemTags.create(new ResourceLocation("curios", slot)), RtSTagGen.RELICS)
				.register();
	}

	private static <T extends BlockRelicItem> ItemEntry<T> curioBlock(
			String id, NonNullBiFunction<Block, Item.Properties, T> factory, String slot,
			NonNullBiConsumer<DataGenContext<Block, DelegateBlock>, RegistrateBlockstateProvider> blockModel,
			NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> itemModel,
			BlockMethod... methods) {
		ALL_CURIOS.add(id);
		var prop = BlockBehaviour.Properties.of().strength(1).pushReaction(PushReaction.DESTROY);
		var builder = RelicTheSpire.REGISTRATE.block(id, p ->
				DelegateBlock.newBaseBlock(prop, methods)).blockstate(blockModel);
		builder.tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
		var item = builder.item(factory);
		return item.model(itemModel)
				.tag(ItemTags.create(new ResourceLocation("curios", slot)), RtSTagGen.RELICS)
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

	private static <T extends Item> void generated(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		pvd.generated(ctx);
	}

	private static <T extends Item> void head(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		var itemModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(new ModelFile.UncheckedModelFile(pvd.mcLoc("item/generated")))
				.texture("layer0", pvd.modLoc("item/" + ctx.getName()));
		var blockModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName())))
				.renderType("cutout");
		pvd.getBuilder("item/" + ctx.getName())
				.guiLight(BlockModel.GuiLight.FRONT)
				.customLoader(SeparateTransformsModelBuilder::begin)
				.base(itemModel)
				.perspective(ItemDisplayContext.HEAD, blockModel);
	}

	private static <T extends Item> void custom(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		var itemModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(new ModelFile.UncheckedModelFile(pvd.mcLoc("item/generated")))
				.texture("layer0", pvd.modLoc("item/" + ctx.getName()));
		var blockModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(new ModelFile.UncheckedModelFile(pvd.modLoc("custom/" + ctx.getName())))
				.texture("all", pvd.modLoc("block/" + ctx.getName()))
				.renderType("cutout");
		pvd.getBuilder("item/" + ctx.getName())
				.guiLight(BlockModel.GuiLight.FRONT)
				.customLoader(SeparateTransformsModelBuilder::begin)
				.base(blockModel)
				.perspective(ItemDisplayContext.GUI, itemModel);
	}

	public static void register() {

	}

}
