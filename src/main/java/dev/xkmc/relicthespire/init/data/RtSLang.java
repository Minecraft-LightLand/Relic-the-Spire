package dev.xkmc.relicthespire.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;

public class RtSLang {

	private static final HashMap<Class<?>, EnumEntry> MAP = new HashMap<>();

	@SafeVarargs
	private static <T extends Info> void putLang(Class<T> cls, String str, T... vals) {
		MAP.put(cls, new EnumEntry(str, vals));
	}

	public record EnumEntry(String path, Info[] info) {

	}

	public record Entry(String id, String def, int count) {
	}

	public interface Info {

		Entry entry();

		default String path() {
			return MAP.get(getClass()).path();
		}

		default String desc() {
			return RelicTheSpire.MODID + "." + path() + "." + entry().id();
		}

		default MutableComponent get(MutableComponent... objs) {
			if (objs.length != entry().count())
				throw new IllegalArgumentException("for " + entry().id() + ": expect " + entry().count() + " parameters, got " + objs.length);
			return translate(desc(), (Object[]) objs);
		}

		default MutableComponent gray(MutableComponent... objs) {
			return get(objs).withStyle(ChatFormatting.GRAY);
		}

		default MutableComponent bullet(MutableComponent... objs) {
			return RtSLang.bullet(get(objs)).withStyle(ChatFormatting.GRAY);
		}

		default MutableComponent bulletBlue(MutableComponent... objs) {
			return bullet(get(objs)).withStyle(ChatFormatting.BLUE);
		}


	}

	public enum Tooltip implements Info {
		BAN("This item is disabled", 0),
		SHIFT("Press [%s] to reveal mechanics", 1),
		ELITE("Enemies with base max health between %s and %s counts as elite", 2);

		final Entry entry;

		Tooltip(String def, int count) {
			entry = new Entry(name().toLowerCase(Locale.ROOT), def, count);
		}

		@Override
		public Entry entry() {
			return entry;
		}

		public MutableComponent shift() {
			return gray(Component.literal("SHIFT").withStyle(ChatFormatting.GOLD));
		}

	}

	public enum Trigger implements Info {
		ENTER_COMBAT("At the start of combat:", 0),
		JOIN_COMBAT("When enemies join combat:", 0),
		JOIN_COMBAT_ELITE("When elite enemies join combat:", 0),
		END_COMBAT("At the end of combat:", 0),
		HEALTH("When user is below %s HP:", 1),
		INTERVAL("Every %s seconds:", 1),
		FIRST_ATTACK("For first attack in combat mode:", 0),
		NTH_ATTACK("For every %s attack in combat mode:", 1),
		USE_POTION("When player use potion:", 0);

		final Entry entry;

		Trigger(String def, int count) {
			entry = new Entry(name().toLowerCase(Locale.ROOT), def, count);
		}

		@Override
		public Entry entry() {
			return entry;
		}

	}

	public enum Effects implements Info {
		HEAL("Restore %s HP", 1),
		EFFECT_SELF("Grant %s", 1),
		EFFECT_TARGET("Inflict %s", 1),
		CD("Cool down: %s seconds", 1),
		MIN_DAMAGE("Damage you inflict will not be lower than %s", 1),
		ABSORPTION("Grant %s absorption", 1),
		DAMAGE_BOOST("+%s damage", 1),
		EFFECT_BOOST("When you inflict %s, +1 effect level", 1),
		REDUCE_HEALTH("Reduce enemy max health by %s", 1);

		final Entry entry;

		Effects(String def, int count) {
			entry = new Entry(name().toLowerCase(Locale.ROOT), def, count);
		}

		@Override
		public Entry entry() {
			return entry;
		}

	}

	public enum Special implements Info {
		POTION_BELT_DESC("May hold %s kinds of potion and %s of each kind", 2),
		POTION_BELT_USE("When you use potions, refill with the same potion in the belt", 0),
		;

		final Entry entry;

		Special(String def, int count) {
			entry = new Entry(name().toLowerCase(Locale.ROOT), def, count);
		}

		@Override
		public Entry entry() {
			return entry;
		}

	}

	static {
		putLang(Tooltip.class, "tooltip", Tooltip.values());
		putLang(Trigger.class, "trigger", Trigger.values());
		putLang(Effects.class, "effect", Effects.values());
		putLang(Special.class, "special", Special.values());
	}

	public static void addLang(RegistrateLangProvider pvd) {
		for (var ent : MAP.values()) {
			for (var e : ent.info()) {
				pvd.add(e.desc(), e.entry().def());
			}
		}
		pvd.add("curios.identifier.feet", "Feet");
		pvd.add("curios.modifiers.feet", "When on feet:");

		lore(pvd, "burning_blood", "Your body's own blood burns with an undying rage.");
		lore(pvd, "ring_of_the_snake", "Made from a fossilized snake. Represents great skill as a huntress.");
		lore(pvd, "cracked_core", "The mysterious life force which powers the Automatons within the Spire. It appears to be cracked.");
		lore(pvd, "pure_water", "Filtered through fine sand and free of impurities.");
		lore(pvd, "bag_of_marbles", "A once popular toy in the City. Useful for throwing enemies off balance.");
		lore(pvd, "blood_vial", "A vial containing the blood of a pure and elder vampire.");
		lore(pvd, "bronze_scales", "The sharp scales of the Guardian. Rearranges itself to protect its user.");
		lore(pvd, "centennial_puzzle", "Upon solving the puzzle, you feel a powerful warmth in your chest.");
		lore(pvd, "data_disk", "This disk contains precious data on birds and snakes.");
		lore(pvd, "the_boot", "When wound up, the boot grows larger in size.");
		lore(pvd, "dream_catcher", "The northern tribes would often use dream catchers at night, believing they led to self improvement.");
		lore(pvd, "happy_flower", "This unceasingly joyous plant is a popular novelty item among nobles.");
		lore(pvd, "lantern", "An eerie lantern which illuminates only for the wielder.");
		lore(pvd, "oddly_smooth_stone", "You have never seen something so smooth and pristine. This must be the work of the Ancients.");
		lore(pvd, "orichalcum", "A green tinted metal of an unknown origin. Seemingly indestructible.");
		lore(pvd, "red_skull", "A small skull covered in ornamental paint.");
		lore(pvd, "smiling_mask", "Mask worn by the Merchant. He must have spares...");
		lore(pvd, "snake_skull", "A snecko skull in pristine condition. Mysteriously clean and smooth, dirt and grime fall off inexplicably.");
		lore(pvd, "potion_belt", "I can hold more Potions using this belt!");
		lore(pvd, "tiny_chest", "\"A fine prototype.\" - The Architect");
		lore(pvd, "vajra", "An ornamental relic given to warriors displaying glory in battle.");
		lore(pvd, "preserved_insect", "The insect seems to create a shrinking aura that targets particularly large enemies.");
		lore(pvd, "ceramic_fish", "Meticulously painted, these fish were revered to bring great fortune.");
		lore(pvd, "akabeko", "\"Muuu~\"");
		lore(pvd, "damaru", "The sound of the small drum keeps your mind awake, revealing a path forward.");
		lore(pvd, "pen_nib", "Holding the nib, you can see everyone ever slain by a previous owner of the pen. A violent history.");
		lore(pvd, "toy_ornithopter", "\"This little toy is the perfect companion for the lone adventurer!\"");
		lore(pvd, "juzu_bracelet", "A ward against the unknown.");
		lore(pvd, "bag_of_preparation", "Oversized adventurer's pack. Has many pockets and straps.");
		lore(pvd, "anchor", "Holding this miniature trinket, you feel heavier and more stable.");
		lore(pvd, "art_of_war", "This ancient manuscript contains wisdom from a past age.");

	}

	private static void lore(RegistrateLangProvider pvd, String id, String lore) {
		pvd.add(RelicTheSpire.MODID + ".item_lore." + id, lore);
	}

	private static final DecimalFormat SINGLE = new DecimalFormat("#.0");
	private static final DecimalFormat DOUBLE = new DecimalFormat("#.00");
	private static final Style NUM = Style.EMPTY.withColor(0x87ceeb);

	public static MutableComponent bullet(Component comp) {
		return Component.literal(" ").append(comp);
	}

	public static MutableComponent num(double amount) {
		if (Math.abs(amount) < 1) {
			Component.literal(DOUBLE.format(amount)).withStyle(NUM);
		} else if (Math.abs(Math.round(amount) - amount) < 0.01) {
			return Component.literal(Math.round(amount) + "").withStyle(NUM);
		}
		return Component.literal(SINGLE.format(amount)).withStyle(NUM);
	}

	public static MutableComponent perc(double amount) {
		return Component.literal(Math.round(amount * 100) + "%").withStyle(NUM);
	}

	public static MutableComponent effect(MobEffect eff, int amp) {
		return effect(eff, amp, 0);
	}

	public static MutableComponent effect(MobEffectInstance ins) {
		return effect(ins.getEffect(), ins.getAmplifier(), ins.getDuration());
	}

	public static MutableComponent effect(MobEffect eff, int amp, int dur) {
		MutableComponent desc = Component.translatable(eff.getDescriptionId());
		if (amp > 0) {
			desc = Component.translatable("potion.withAmplifier", desc, Component.translatable("potion.potency." + amp));
		}
		if (dur == -1) {
			desc = Component.translatable("potion.withDuration", desc, Component.translatable("effect.duration.infinite"));
		} else if (dur >= 20) {
			desc = Component.translatable("potion.withDuration", desc, Component.literal(StringUtil.formatTickDuration(dur)));
		}
		return desc.withStyle(eff.getCategory().getTooltipFormatting());
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}


}
