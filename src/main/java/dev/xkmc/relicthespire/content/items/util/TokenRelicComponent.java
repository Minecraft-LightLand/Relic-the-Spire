package dev.xkmc.relicthespire.content.items.util;

import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.capability.conditionals.Context;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.capability.conditionals.TokenProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class TokenRelicComponent<T extends BaseTickingToken> implements TokenProvider<T, TokenRelicComponent<T>>, Context {

	private final TokenKey<T> key;
	private final Supplier<T> factory;

	public TokenRelicComponent(TokenKey<T> key, Supplier<T> factory) {
		this.key = key;
		this.factory = factory;
	}

	public void tick(ItemStack stack, LivingEntity user) {
		var opt = user.getCapability(ConditionalData.CAPABILITY).resolve();
		if (opt.isEmpty()) return;
		opt.get().getOrCreateData(this, this).update();
	}

	@Override
	public T getData(TokenRelicComponent<T> item) {
		return factory.get();
	}

	@Override
	public TokenKey<T> getKey() {
		return key;
	}

}
