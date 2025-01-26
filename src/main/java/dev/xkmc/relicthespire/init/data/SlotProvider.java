package dev.xkmc.relicthespire.init.data;

import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class SlotProvider extends CuriosDataProvider {

	public SlotProvider(String modId, PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
		super(modId, output, fileHelper, registries);
	}

	@Override
	public void generate(HolderLookup.Provider provider, ExistingFileHelper existingFileHelper) {
		createSlot("feet").icon(RelicTheSpire.loc("curios/feet")).order(200);
		createEntities("spire")
				.addEntities(EntityType.PLAYER)
				.addSlots("head", "charm", "belt", "body", "feet");
	}

}
