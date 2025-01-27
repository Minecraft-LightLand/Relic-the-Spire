package dev.xkmc.relicthespire.content.client;

import dev.xkmc.relicthespire.content.items.special.PotionBelt;
import dev.xkmc.relicthespire.init.registrate.RtSItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.IItemDecorator;
import top.theillusivec4.curios.api.CuriosApi;

public class PotionDeco implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		if (Minecraft.getInstance().screen != null) return false;
		var player = Minecraft.getInstance().player;
		if (player == null) return false;
		if (!stack.is(Items.POTION) && !stack.is(Items.SPLASH_POTION) && !stack.is(Items.LINGERING_POTION))
			return false;
		int count = stack.getCount();
		if (count != 1) return false;
		var opt = CuriosApi.getCuriosInventory(player).resolve().flatMap(e -> e.findFirstCurio(RtSItems.POTION_BELT.get()));
		if (opt.isEmpty()) return false;
		var list = PotionBelt.getItems(opt.get().stack());
		for (var e : list) {
			if (ItemStack.isSameItemSameTags(e, stack)) {
				count += e.getCount();
			}
		}
		if (count == 1) return false;
		String s = "" + count;
		g.pose().pushPose();
		g.pose().translate(0, 0, 250);
		g.drawString(font, s, x + 17 - font.width(s), y + 9, 0xffff7f);
		g.pose().popPose();
		return false;
	}

}
