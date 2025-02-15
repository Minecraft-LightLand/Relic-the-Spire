package dev.xkmc.relicthespire.content.items.util;

import dev.xkmc.l2damagetracker.contents.curios.AttrTooltip;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.relicthespire.init.data.RtSLang;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public record AttrToken(Supplier<Attribute> attr, DoubleSupplier val, AttributeModifier.Operation op) {

	public static AttrToken add(Supplier<Attribute> attr, DoubleSupplier val) {
		return new AttrToken(attr, val, AttributeModifier.Operation.ADDITION);
	}

	public void stop(Player player, TokenKey<?> key) {
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		var uuid = MathHelper.getUUIDFromString(key.toString());
		ins.removeModifier(uuid);
	}

	public void start(Player player, TokenKey<?> key) {
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		var uuid = MathHelper.getUUIDFromString(key.toString());
		ins.removeModifier(uuid);
		ins.addPermanentModifier(new AttributeModifier(uuid, key.asLocation().toString(), val.getAsDouble(), op));
	}

	public MutableComponent lang() {
		return AttrTooltip.getDesc(attr.get(), val.getAsDouble(), op);
	}

	public MutableComponent bullet() {
		return RtSLang.bullet(AttrTooltip.getDesc(attr.get(), val.getAsDouble(), op));
	}

}
