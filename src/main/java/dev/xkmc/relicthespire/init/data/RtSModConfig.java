package dev.xkmc.relicthespire.init.data;

import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedHashMap;

public class RtSModConfig {

	public static class Client {

		Client(ForgeConfigSpec.Builder builder) {
		}

	}

	public static class Common {

		public static class Curios {

			public final ForgeConfigSpec.DoubleValue burningBloodHeal;

			public final ForgeConfigSpec.IntValue bagOfMarblesDuration;

			public final ForgeConfigSpec.DoubleValue bloodVialHeal;
			public final ForgeConfigSpec.IntValue bloodVialCoolDown;

			public final ForgeConfigSpec.IntValue bronzeScalesThornAmplifier;

			public final ForgeConfigSpec.DoubleValue theBootMinDamage;

			public final ForgeConfigSpec.IntValue oddlySmoothStoneSpeedAmplifier;

			public final ForgeConfigSpec.IntValue orichalcumInterval;
			public final ForgeConfigSpec.DoubleValue orichalcumAbsorption;

			public final ForgeConfigSpec.IntValue redSkullAttack;
			public final ForgeConfigSpec.DoubleValue redSkullHealthPercentage;

			public final ForgeConfigSpec.IntValue potionBeltMaxSlot;
			public final ForgeConfigSpec.IntValue potionBeltMaxCount;

			public final ForgeConfigSpec.IntValue vajraAttack;

			public final ForgeConfigSpec.DoubleValue preservedInsectDebuff;

			public final ForgeConfigSpec.DoubleValue akabekoDamageAdd;

			public final ForgeConfigSpec.DoubleValue penNibDamageFactor;
			public final ForgeConfigSpec.IntValue penNibDamageCount;

			public final ForgeConfigSpec.DoubleValue toyOrithopterHeal;

			private Curios(ForgeConfigSpec.Builder builder) {
				builder.push("curios");
				burningBloodHeal = builder.comment("Burning Blood: amount to heal after killing last target in combat")
						.defineInRange("burningBloodHeal", 6d, 0, 1000);

				bagOfMarblesDuration = builder.comment("Bag of Marbles: duration of fragile effect to inflict, in ticks")
						.defineInRange("bagOfMarblesDuration", 300, 0, 1000000);

				bloodVialHeal = builder.comment("Blood Vial: amount to heal after killing last target in combat")
						.defineInRange("bloodVialHeal", 2d, 0, 1000);
				bloodVialCoolDown = builder.comment("Blood Vial: cooldown of healing in ticks")
						.defineInRange("bloodVialCoolDown", 1800, 0, 1000000);

				bronzeScalesThornAmplifier = builder.comment("Bronze Scales: Thorn effect amplifier")
						.defineInRange("bronzeScalesThornAmplifier", 2, 0, 100);

				theBootMinDamage = builder.comment("The Boot: minimum damage to deal")
						.defineInRange("theBootMinDamage", 5d, 0, 10000);

				oddlySmoothStoneSpeedAmplifier = builder.comment("Oddly Smooth Stone: Speed effect amplifier")
						.defineInRange("oddlySmoothStoneSpeedAmplifier", 0, 0, 10);

				orichalcumInterval = builder.comment("Orichalcum: interval in ticks to grant absorption")
						.defineInRange("orichalcumInterval", 1800, 1, 100000);
				orichalcumAbsorption = builder.comment("Orichalcum: amount of absorption to grant")
						.defineInRange("orichalcumAbsorption", 6d, 0, 1000);

				redSkullAttack = builder.comment("Red Skull: attack bonus")
						.defineInRange("redSkullAttack", 3, 0, 100);
				redSkullHealthPercentage = builder.comment("Red Skull: percentage max health to trigger")
						.defineInRange("redSkullHealthPercentage", 0.5d, 0, 1);

				potionBeltMaxSlot = builder.comment("Potion Belt: max number of slots")
						.defineInRange("potionBeltMaxSlot", 5, 1, 9);
				potionBeltMaxCount = builder.comment("Potion Belt: max number of potions per slot")
						.defineInRange("potionBeltMaxCount", 5, 1, 64);

				vajraAttack = builder.comment("Vajra: attack bonus")
						.defineInRange("vajraAttack", 1, 0, 100);

				preservedInsectDebuff = builder.comment("Preserved Insect: max health to reduce")
						.defineInRange("perservedInsectDebuff", 0.25, 0, 1);

				akabekoDamageAdd = builder.comment("Akabeko: Damage bonus as addition")
						.defineInRange("akabekoDamageAdd", 8d, 0, 1000);

				penNibDamageFactor = builder.comment("Pen Nib: Multiplicative damage bonus")
						.defineInRange("penNibDamageFactor", 1d, 0, 10);
				penNibDamageCount = builder.comment("Pen Nib: Attack count to trigger")
						.defineInRange("penNibDamageCount", 10, 1, 100);

				toyOrithopterHeal = builder.comment("Toy Orithopter: healing amount")
						.defineInRange("toyOrithopterHeal", 5d, 0, 1000);

				builder.pop();
			}

		}

		public static class Misc {

			public final ForgeConfigSpec.DoubleValue eliteStartHealth;
			public final ForgeConfigSpec.DoubleValue eliteEndHealth;

			private Misc(ForgeConfigSpec.Builder builder) {
				builder.push("misc");

				eliteStartHealth = builder.comment("Starting base health to count as elite mob")
						.defineInRange("eliteStartHealth", 45d, 1, 1000);
				eliteEndHealth = builder.comment("Max base health to count as elite mob")
						.defineInRange("eliteEndHealth", 99d, 1, 1000);

				builder.pop();
			}

		}

		public static class Toggles {

			private final LinkedHashMap<String, ForgeConfigSpec.BooleanValue> itemToggle = new LinkedHashMap<>();

			private Toggles(ForgeConfigSpec.Builder builder) {
				builder.push("itemToggle");
				for (var e : RtSItems.ALL_CURIOS) {
					itemToggle.put(e, builder.define(e, true));
				}
				builder.pop();
			}

			public boolean get(String item) {
				var ans = itemToggle.get(item);
				if (ans == null) {
					return true;
				}
				return ans.get();
			}

		}

		public final Curios curios;
		public final Misc misc;
		public final Toggles toggles;


		Common(ForgeConfigSpec.Builder builder) {
			curios = new Curios(builder);
			misc = new Misc(builder);
			toggles = new Toggles(builder);

		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	public static String COMMON_PATH;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		COMMON_PATH = register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static String register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
		return path;
	}

}
