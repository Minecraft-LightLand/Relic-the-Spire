package dev.xkmc.relicthespire.content.capability;

import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.relicthespire.content.items.core.BaseRelicItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

@SerialClass
public class TrackerEntry {

	public static final int DAMAGE_TIMEOUT = 600, DAMAGE_RANGE = 64;
	public static final int SIGHT_TIMEOUT = 200, TARGET_TIMEOUT = 400, TARGET_RANGE = 48;

	public static boolean isValid(LivingEntity self, LivingEntity le) {
		if (le instanceof Player pl && (pl.isSpectator() || pl.getAbilities().instabuild))
			return false;
		if (le instanceof ArmorStand)
			return false;
		if (le.isRemoved() || !le.isAddedToWorld() || !le.isAlive()) return false;
		if (!le.canAttack(self)) return false;
		return self.level() == le.level() && !self.isAlliedTo(le) && !le.isAlliedTo(self);
	}


	@SerialClass.SerialField
	private UUID id;

	@SerialClass.SerialField
	private long lastSeePlayerTime;

	@SerialClass.SerialField
	private long lastTargetPlayerTime;

	@SerialClass.SerialField
	private long lastDamageTime;

	@SerialClass.SerialField
	private boolean prevActive;

	private LivingEntity cache;

	public static TrackerEntry create(UUID id) {
		var ans = new TrackerEntry();
		ans.id = id;
		return ans;
	}

	public boolean tickAndShouldRemove(ServerLevel sl, LivingEntity self) {
		if (cache == null) {
			var e = sl.getEntity(id);
			if (e instanceof LivingEntity le) {
				cache = le;
			} else return true;
		}
		if (!isValid(self, cache)) {
			return true;
		}
		long current = sl.getGameTime();
		if (cache.hasLineOfSight(self)) {
			lastSeePlayerTime = current;
		}
		if (cache instanceof Mob mob && mob.getTarget() == self) {
			lastTargetPlayerTime = current;
		}
		updateActivity(sl, self);
		return lastTargetPlayerTime + TARGET_TIMEOUT < current &&
				lastSeePlayerTime + SIGHT_TIMEOUT < current &&
				lastDamageTime + DAMAGE_TIMEOUT < current;
	}

	private boolean isActive(ServerLevel sl, LivingEntity self) {
		long current = sl.getGameTime();
		double dist = self.distanceTo(cache);
		if (lastDamageTime + DAMAGE_TIMEOUT >= current && dist <= DAMAGE_RANGE) {
			return true;
		}
		if (lastSeePlayerTime + SIGHT_TIMEOUT >= current || lastTargetPlayerTime + TARGET_TIMEOUT >= current) {
			return dist <= TARGET_RANGE;
		}
		return false;
	}

	public boolean isActive() {
		return prevActive;
	}

	private boolean updateActivity(ServerLevel sl, LivingEntity self) {
		boolean curActive = isActive(sl, self);
		if (!prevActive && curActive) {
			activate(sl, self);
			prevActive = true;
			return true;
		}
		prevActive = curActive;
		return false;
	}

	private void activate(ServerLevel sl, LivingEntity self) {
		BaseRelicItem.onTrigger(self, (stack, e) -> e.onMobJoinBattle(stack, self, cache));
	}

	public boolean onCombat(ServerLevel sl, LivingEntity self) {
		lastDamageTime = sl.getGameTime();
		return updateActivity(sl, self);
	}

	public boolean onTarget(ServerLevel sl, LivingEntity self) {
		lastTargetPlayerTime = sl.getGameTime();
		return updateActivity(sl, self);
	}

	public void onKill() {
		prevActive = false;
	}

}
