package dev.xkmc.relicthespire.content.items.ticking;

import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.relicthespire.content.items.core.DiggerRelicItem;
import dev.xkmc.relicthespire.content.items.util.AttrToken;
import dev.xkmc.relicthespire.content.items.util.BaseTickingToken;
import dev.xkmc.relicthespire.content.items.util.TokenRelicComponent;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Vajra extends DiggerRelicItem {

	private static int amp() {
		return RtSModConfig.COMMON.curios.vajraAttack.get();
	}

	private static final TokenKey<Vajra.Data> KEY = TokenKey.of(RelicTheSpire.loc("vajra"));
	private static final AttrToken ATK = AttrToken.add(() -> Attributes.ATTACK_DAMAGE, Vajra::amp);

	public Vajra(Properties prop) {
		super(prop, 6, 1.4f, 8, new TokenRelicComponent<>(KEY, Data::new));
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(ATK.lang());
	}

	@Override
	public boolean isValidSlot(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND;
	}

	@Override
	public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.MAINHAND;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state) {
		if (state.is(BlockTags.NEEDS_DIAMOND_TOOL))
			return false;
		return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void start(Player player) {
			ATK.start(player, KEY);
		}

		@Override
		protected void stop(Player player) {
			ATK.stop(player, KEY);
		}

	}

}
