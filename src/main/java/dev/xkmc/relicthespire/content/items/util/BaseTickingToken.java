package dev.xkmc.relicthespire.content.items.util;

import dev.xkmc.l2library.capability.conditionals.ConditionalToken;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.player.Player;

@SerialClass
public class BaseTickingToken extends ConditionalToken {

	@SerialClass.SerialField
	public int life = 2;
	@SerialClass.SerialField
	public boolean activated = false;

	protected final void update() {
		life = 2;
	}

	@Override
	public final boolean tick(Player player) {
		life--;
		if (life <= 0) {
			if (activated) {
				stop(player);
			}
			return true;
		}
		boolean valid = isValid(player);
		if (!activated && valid) {
			activated = true;
			start(player);
		}
		if (activated && !valid) {
			activated = false;
			stop(player);
		}
		if (activated) {
			tickImpl(player);
		}
		return false;
	}

	protected boolean isValid(Player player) {
		return true;
	}

	protected void tickImpl(Player player) {

	}

	protected void start(Player player) {

	}

	protected void stop(Player player) {
	}

}
