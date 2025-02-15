package dev.xkmc.relicthespire.content.items.core;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.relicthespire.content.items.util.TokenRelicComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class DiggerRelicItem extends BaseRelicItem {

	private final Multimap<Attribute, AttributeModifier> attributes;
	private final float atk, atkSpeed, miningSpeed;

	public DiggerRelicItem(Properties prop, float atk, float atkSpeed, float miningSpeed) {
		this(prop, miningSpeed, atkSpeed, atk,null);
	}

	public DiggerRelicItem(Properties prop, float atk, float atkSpeed, float miningSpeed, @Nullable TokenRelicComponent<?> token) {
		super(prop, token);
		this.atk = atk;
		this.atkSpeed = atkSpeed;
		this.miningSpeed = miningSpeed;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		buildAttributes(builder);
		this.attributes = builder.build();
	}

	public void buildAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", atk, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", atkSpeed - 4, AttributeModifier.Operation.ADDITION));

	}


	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return isCorrectToolForDrops(stack, state) ? miningSpeed : 1.0F;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		return slot == getEquipmentSlot(getDefaultInstance()) ? attributes : ImmutableMultimap.of();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return slot == getEquipmentSlot(stack) ? attributes : ImmutableMultimap.of();
	}

	@Override
	public abstract boolean isCorrectToolForDrops(BlockState state);

}
