package dev.xkmc.relicthespire.content.entity.merchant;

import dev.xkmc.relicthespire.init.data.RtSTagGen;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MerchantEntity extends AbstractGolem implements Merchant {

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20);
	}

	public MerchantEntity(EntityType<? extends MerchantEntity> type, Level level) {
		super(type, level);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.02F, true));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}


	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	public Vec3 getDeltaMovement() {
		return super.getDeltaMovement().multiply(0, 1, 0);
	}

	public void setDeltaMovement(Vec3 vec) {
		super.setDeltaMovement(vec.multiply(0, 1, 0));
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	public void push(Entity e) {
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (player instanceof ServerPlayer sp) openMenu(sp);
		return InteractionResult.SUCCESS;
	}

	public void openMenu(Player player) {
		if (getTradingPlayer() != null && getTradingPlayer().isAlive())
			return;
		this.player = player;
		openTradingScreen(player, getName(), 0);
	}

	private Player player;
	private MerchantOffers offers;

	private MerchantOffers createOffers() {
		MerchantOffers ans = new MerchantOffers();
		var potions = new ArrayList<>(ForgeRegistries.ITEMS.tags().getTag(RtSTagGen.POTIONS).stream().toList());
		for (int i = 0; i < 3; i++) {
			var item = potions.remove(random.nextInt(potions.size()));
			int cost = switch (item.getRarity(item.getDefaultInstance())) {
				case EPIC -> 24;
				case RARE -> 16;
				default -> 12;
			};
			cost = random.nextInt(cost - cost / 4, cost);
			ans.add(offer(Items.EMERALD, cost, item.getDefaultInstance()));
		}
		var relics = new ArrayList<>(ForgeRegistries.ITEMS.tags().getTag(RtSTagGen.RELICS).stream().toList());
		for (int i = 0; i < 3; i++) {
			var item = relics.remove(random.nextInt(potions.size()));
			int cost = switch (item.getRarity(item.getDefaultInstance())) {
				case EPIC -> 64;
				case RARE -> 48;
				default -> 32;
			};
			cost = random.nextInt(cost - cost / 8, cost);
			ans.add(offer(Items.EMERALD, cost, item.getDefaultInstance()));
		}
		return ans;
	}

	private static MerchantOffer offer(Item in, int a, ItemStack b) {
		return new MerchantOffer(new ItemStack(in, a), b, 1, 0, 0);
	}


	@Override
	public void setTradingPlayer(@Nullable Player player) {
		this.player = player;
	}

	@Override
	public @Nullable Player getTradingPlayer() {
		return player;
	}

	@Override
	public MerchantOffers getOffers() {
		if (offers == null)
			offers = createOffers();
		return offers;
	}

	@Override
	public void overrideOffers(MerchantOffers offer) {
		this.offers = offer;
	}

	@Override
	public void notifyTrade(MerchantOffer offer) {

	}

	@Override
	public void notifyTradeUpdated(ItemStack stack) {

	}

	@Override
	public int getVillagerXp() {
		return 0;
	}

	@Override
	public void overrideXp(int exp) {

	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	@Override
	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.WANDERING_TRADER_YES;
	}

	@Override
	public boolean isClientSide() {
		return level().isClientSide();
	}

}
