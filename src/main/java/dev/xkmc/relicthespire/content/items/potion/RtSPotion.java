package dev.xkmc.relicthespire.content.items.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.function.Supplier;

public class RtSPotion extends PotionItem {

	private final Supplier<Potion> potion;

	public RtSPotion(Properties prop, Supplier<Potion> potion) {
		super(prop);
		this.potion = potion;
	}

	public String getDescriptionId(ItemStack p_43003_) {
		return getOrCreateDescriptionId();
	}

	@Override
	public ItemStack getDefaultInstance() {
		return PotionUtils.setPotion(super.getDefaultInstance(), potion.get());
	}

}
