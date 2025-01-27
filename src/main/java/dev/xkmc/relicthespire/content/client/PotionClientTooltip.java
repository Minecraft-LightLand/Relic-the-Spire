package dev.xkmc.relicthespire.content.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;

public record PotionClientTooltip(PotionTooltip inv) implements ClientTooltipComponent {

	@Override
	public int getHeight() {
		return 20;
	}

	@Override
	public int getWidth(Font font) {
		return 18 * inv.list().size();
	}

	@Override
	public void renderImage(Font font, int mx, int my, GuiGraphics g) {
		var list = inv.list();
		for (int i = 0; i < list.size(); i++) {
			renderSlot(font, mx + i * 18, my, g, list.get(i));
		}
	}

	private void renderSlot(Font font, int x, int y, GuiGraphics g, ItemStack stack) {
		if (stack.isEmpty()) {
			return;
		}
		g.renderItem(stack, x + 1, y + 1, 0);
		g.renderItemDecorations(font, stack, x + 1, y + 1);
	}

}
