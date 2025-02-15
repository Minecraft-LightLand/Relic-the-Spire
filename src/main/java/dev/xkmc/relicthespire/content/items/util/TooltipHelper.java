package dev.xkmc.relicthespire.content.items.util;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TooltipHelper {

	public static boolean showLore(@Nullable Level level) {
		if (level == null || !level.isClientSide()) return true;
		return !Screen.hasShiftDown();
	}

	public static boolean showDesc(@Nullable Level level) {
		if (level == null || !level.isClientSide()) return true;
		return Screen.hasShiftDown();
	}

}
