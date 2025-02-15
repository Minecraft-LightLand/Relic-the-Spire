package dev.xkmc.relicthespire.content.block;

import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import dev.xkmc.relicthespire.content.items.util.VoxelBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public record BaseHorizontalBlock(VoxelShape[] shapes) implements ShapeBlockMethod {

	public static BaseHorizontalBlock of(int x0, int y0, int z0, int x1, int y1, int z1) {
		var builder = new VoxelBuilder(x0, y0, z0, x1, y1, z1);
		VoxelShape[] shapes = new VoxelShape[4];
		for (int i = 0; i < 4; i++) {
			shapes[i] = builder.rotateFromNorth(Direction.from2DDataValue(i));
		}
		return new BaseHorizontalBlock(shapes);
	}

	@Nullable
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return shapes[state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue()];
	}

}
