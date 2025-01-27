
package dev.xkmc.relicthespire.content.client;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record PotionTooltip(List<ItemStack> list) implements TooltipComponent {

}
