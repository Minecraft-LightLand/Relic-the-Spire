package dev.xkmc.relicthespire.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

		default MutableComponent yellow(MutableComponent... objs) {
			return get(objs).withStyle(ChatFormatting.YELLOW);
		}

		default MutableComponent gray(MutableComponent... objs) {
			return get(objs).withStyle(ChatFormatting.GRAY);
		}

		default MutableComponent bullet(MutableComponent... objs) {
			return Component.literal("- ").append(get(objs)).withStyle(ChatFormatting.GRAY);
		}

	}

	public enum Tooltip implements Info {
		BAN("This item is disabled", 0);

		final Entry entry;

		Tooltip(String def, int count) {
			entry = new Entry(name().toLowerCase(Locale.ROOT), def, count);
		}

		@Override
		public Entry entry() {
			return entry;
		}

	}

	public enum Trigger implements Info {
		ENTER_COMBAT("At the start of combat:", 0),
		JOIN_COMBAT("When enemies join combat:", 0),
		END_COMBAT("At the end of combat:", 0),
		HEALTH("When user is below %s HP:", 1);

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
		;

		final Entry entry;

		Effects(String def, int count) {
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
	}

	public static void addLang(RegistrateLangProvider pvd) {
		for (var ent : MAP.values()) {
			for (var e : ent.info()) {
				pvd.add(e.desc(), e.entry().def());
			}
		}
	}

	private static final DecimalFormat SINGLE = new DecimalFormat("#.0");
	private static final DecimalFormat DOUBLE = new DecimalFormat("#.00");

	public static MutableComponent num(double amount) {
		if (Math.abs(amount) < 1) {
			Component.literal(DOUBLE.format(amount)).withStyle(ChatFormatting.AQUA);
		} else if (Math.abs(Math.round(amount) - amount) < 0.01) {
			return Component.literal(Math.round(amount) + "").withStyle(ChatFormatting.AQUA);
		}
		return Component.literal(SINGLE.format(amount)).withStyle(ChatFormatting.AQUA);
	}

	public static MutableComponent perc(double amount) {
		return Component.literal(Math.round(amount * 100) + "%").withStyle(ChatFormatting.AQUA);
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
