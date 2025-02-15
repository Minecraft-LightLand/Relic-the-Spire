package dev.xkmc.relicthespire.content.items.ticking;

import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.relicthespire.content.items.core.BlockRelicItem;
import dev.xkmc.relicthespire.content.items.util.AttrToken;
import dev.xkmc.relicthespire.content.items.util.BaseTickingToken;
import dev.xkmc.relicthespire.content.items.util.TokenRelicComponent;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedSkull extends BlockRelicItem {

	private static int amp() {
		return RtSModConfig.COMMON.curios.redSkullAttack.get();
	}

	private static double php() {
		return RtSModConfig.COMMON.curios.redSkullHealthPercentage.get();
	}

	private static final TokenKey<Data> KEY = TokenKey.of(RelicTheSpire.loc("red_skull"));
	private static final AttrToken ATK = AttrToken.add(() -> Attributes.ATTACK_DAMAGE, RedSkull::amp);

	public RedSkull(Block block, Properties prop) {
		super(block, prop, new TokenRelicComponent<>(KEY, Data::new));
	}

	@Override
	public void addText(List<Component> list, ItemStack stack) {
		list.add(RtSLang.Trigger.HEALTH.gray(RtSLang.perc(php())));
		list.add(ATK.bullet());
	}

	@Override
	public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}

	@Override
	public boolean isValidSlot(EquipmentSlot slot) {
		return slot == EquipmentSlot.HEAD;
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

		@Override
		protected boolean isValid(Player player) {
			return player.getMaxHealth() * php() > player.getHealth();
		}

	}

}
