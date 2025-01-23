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

			public final ForgeConfigSpec.IntValue redSkullStrengthAmplifier;
			public final ForgeConfigSpec.DoubleValue redSkullHealthPercentage;

			public final ForgeConfigSpec.IntValue vajraStrengthAmplifier;

			private Curios(ForgeConfigSpec.Builder builder) {
				builder.push("curios");
				burningBloodHeal = builder.comment("Burning Blood: amount to heal after killing last target in combat")
						.defineInRange("burningBloodHeal", 6d, 0, 1000);

				bagOfMarblesDuration = builder.comment("Bag of Marbles: duration of fragile effect to inflict, in ticks")
						.defineInRange("bagOfMarblesDuration", 3000, 0, 1000000);

				bloodVialHeal = builder.comment("Blood Vial: amount to heal after killing last target in combat")
						.defineInRange("bloodVialHeal", 2d, 0, 1000);
				bloodVialCoolDown = builder.comment("Blood Vial: cooldown of healing in ticks")
						.defineInRange("bloodVialCoolDown", 1800, 0, 1000000);


				redSkullStrengthAmplifier = builder.comment("Red Skull: Strength effect amplifier")
						.defineInRange("redSkullStrengthAmplifier", 2, 0, 100);
				redSkullHealthPercentage = builder.comment("Red Skull: percentage max health to trigger")
						.defineInRange("redSkullHealthPercentage", 0.5d, 0, 1);

				vajraStrengthAmplifier = builder.comment("Vajra: Strength effect amplifier")
						.defineInRange("vajraStrengthAmplifier", 0, 0, 100);
				builder.pop();
			}

		}

		public static class Misc {

			private Misc(ForgeConfigSpec.Builder builder) {
				builder.push("misc");


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
