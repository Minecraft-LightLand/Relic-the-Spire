package dev.xkmc.relicthespire.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.relicthespire.content.capability.BattleTracker;
import dev.xkmc.relicthespire.event.RtSAttackListener;
import dev.xkmc.relicthespire.init.data.RtSLang;
import dev.xkmc.relicthespire.init.data.RtSModConfig;
import dev.xkmc.relicthespire.init.data.SlotProvider;
import dev.xkmc.relicthespire.init.registrate.RtSEffect;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(RelicTheSpire.MODID)
@Mod.EventBusSubscriber(modid = RelicTheSpire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RelicTheSpire {

	public static final String MODID = "relicthespire";

	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public RelicTheSpire() {
		RtSItems.register();
		RtSEffect.register();
		RtSModConfig.init();
		BattleTracker.register();

		AttackEventHandler.register(4573, new RtSAttackListener());
	}

	@SubscribeEvent
	public static void onDataGen(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.LANG, RtSLang::addLang);

		var gen = event.getGenerator();
		var out = gen.getPackOutput();
		var file = event.getExistingFileHelper();
		var reg = event.getLookupProvider();
		var run = event.includeServer();

		gen.addProvider(run, new SlotProvider(MODID, out, file, reg));
	}

	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MODID, id);
	}

}
