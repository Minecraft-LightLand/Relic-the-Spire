package dev.xkmc.relicthespire.init.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import dev.xkmc.relicthespire.content.entity.merchant.MerchantEntity;
import dev.xkmc.relicthespire.content.entity.merchant.MerchantRenderer;
import dev.xkmc.relicthespire.init.RelicTheSpire;
import net.minecraft.world.entity.MobCategory;

public class RtSEntities {

	public static final EntityEntry<MerchantEntity> MERCHANT;

	static {

		MERCHANT = RelicTheSpire.REGISTRATE
				.entity("merchant", MerchantEntity::new, MobCategory.MISC)
				.properties(e -> e.sized(0.6F, 1.32F).clientTrackingRange(4))
				.attributes(MerchantEntity::createAttributes)
				.renderer(() -> MerchantRenderer::new)
				.spawnEgg(0xffffffff, 0xff000000).build()
				.register();

	}

	public static void register() {

	}

}
