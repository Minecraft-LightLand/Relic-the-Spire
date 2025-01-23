package dev.xkmc.relicthespire.content.capability;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

@SerialClass
public class BattleTracker extends PlayerCapabilityTemplate<BattleTracker> {

	public static final Capability<BattleTracker> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final PlayerCapabilityHolder<BattleTracker> HOLDER =
			new PlayerCapabilityHolder<>(RelicTheSpire.loc("battle_tracker"),
					CAPABILITY, BattleTracker.class, BattleTracker::new, PlayerCapabilityNetworkHandler::new);


	@SerialClass.SerialField
	private final HashMap<UUID, TrackerEntry> entries = new HashMap<>();

	@SerialClass.SerialField
	private boolean prevActive = false;

	@Override
	public void onClone(boolean isWasDeath) {
		entries.clear();
		if (prevActive) {
			if (player instanceof ServerPlayer sp) {
				deactivate(sp, false);
			}
			prevActive = false;
		}
	}

	@Override
	public void tick() {
		if (player instanceof ServerPlayer sp) {
			updateActivity(sp, false);
		}
	}

	private void updateActivity(ServerPlayer sp, boolean kill) {
		entries.entrySet().removeIf(e -> e.getValue().tickAndShouldRemove(sp.serverLevel(), sp));
		boolean curActive = false;
		for (var e : entries.values()) {
			if (e.isActive()) {
				curActive = true;
				break;
			}
		}
		if (prevActive && !curActive) {
			deactivate(sp, kill);
			prevActive = false;
		}
		if (!prevActive && curActive) {
			activate(sp);
			prevActive = true;
		}

	}

	private void activate(ServerPlayer sp) {
		BaseRelicItem.onTrigger(sp, (stack, e) -> e.onEnterCombatMode(stack, sp));
	}

	private void deactivate(ServerPlayer sp, boolean kill) {
		if (kill) {
			BaseRelicItem.onTrigger(sp, (stack, e) -> e.killLastTarget(stack, sp));
		}
	}

	private void tryActivate(ServerPlayer sp) {
		if (!prevActive) {
			activate(sp);
			prevActive = true;
		}
	}

	public void onAttack(LivingEntity target) {
		var entry = entries.computeIfAbsent(target.getUUID(), TrackerEntry::create);
		if (player instanceof ServerPlayer sp) {
			if (entry.onCombat(sp.serverLevel(), sp)) {
				tryActivate(sp);
			}
		}
	}

	public void onTarget(LivingEntity target) {
		if (player instanceof ServerPlayer sp) {
			var entry = entries.computeIfAbsent(target.getUUID(), TrackerEntry::create);
			if (entry.onTarget(sp.serverLevel(), sp)) {
				tryActivate(sp);
			}
		}
	}

	public void onAttacked(@Nullable LivingEntity attacker) {
		if (attacker == null) return;
		if (player instanceof ServerPlayer sp) {
			var entry = entries.computeIfAbsent(attacker.getUUID(), TrackerEntry::create);
			if (entry.onCombat(sp.serverLevel(), sp)) {
				tryActivate(sp);
			}
		}
	}

	public void onKill(LivingEntity entity) {
		if (player instanceof ServerPlayer sp) {
			var entry = entries.computeIfAbsent(entity.getUUID(), TrackerEntry::create);
			entry.onKill();
			updateActivity(sp, true);
		}
	}

	public static void register() {
	}
}
