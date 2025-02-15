package dev.xkmc.relicthespire.content.block;

import dev.xkmc.l2modularblock.mult.CreateBlockStateBlockMethod;
import dev.xkmc.l2modularblock.mult.DefaultStateBlockMethod;
import dev.xkmc.l2modularblock.mult.PlacementBlockMethod;
import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public record WallHorizontalBlock(VoxelShape[] ground, VoxelShape[] wall)
		implements CreateBlockStateBlockMethod, DefaultStateBlockMethod, PlacementBlockMethod, ShapeBlockMethod {

	public static final BooleanProperty WALL = BooleanProperty.create("wall");

	public static WallHorizontalBlock of(int x0, int y0, int z0, int x1, int y1, int z1, int dx, int dy, int dz) {
		return new WallHorizontalBlock(
				BaseHorizontalBlock.of(x0, y0, z0, x0 + dx, y0 + dy, z0 + dz).shapes(),
				BaseHorizontalBlock.of(x1, y1, z1, x1 + dx, y1 + dy, z1 + dz).shapes()
		);
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WALL);
	}

	@Override
	public BlockState getDefaultState(BlockState state) {
		return state.setValue(WALL, false);
	}

	@Override
	public BlockState getStateForPlacement(BlockState state, BlockPlaceContext ctx) {
		return state.setValue(WALL, ctx.getClickedFace().getAxis().isHorizontal());
	}

	@Nullable
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return (state.getValue(WALL) ? wall : ground)[state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue()];
	}

}
