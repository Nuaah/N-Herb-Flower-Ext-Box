package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.block.entity.AuroraMushroomEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.FirefliesMushroomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockAuroraMushroom extends BaseEntityBlock {

    public static final VoxelShape SHAPE = Block.box(5,0,5,11,13,11);

    public BlockAuroraMushroom() {
        super(Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .ignitedByLava()
            .lightLevel((a) -> 2));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        var offset = state.getOffset(getter, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        var offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        // 許可するブロックを指定（ここでは砂ブロックだけ）
        return belowState.is(Blocks.STONE) || belowState.is(Blocks.DEEPSLATE);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockState updateShape(BlockState p_51032_, Direction p_51033_, BlockState p_51034_, LevelAccessor p_51035_, BlockPos p_51036_, BlockPos p_51037_) {
        return !p_51032_.canSurvive(p_51035_, p_51036_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    public boolean propagatesSkylightDown(BlockState p_51039_, BlockGetter p_51040_, BlockPos p_51041_) {
        return p_51039_.getFluidState().isEmpty();
    }

    public boolean isPathfindable(BlockState p_51023_, BlockGetter p_51024_, BlockPos p_51025_, PathComputationType p_51026_) {
        return p_51026_ == PathComputationType.AIR && !this.hasCollision ? true : super.isPathfindable(p_51023_, p_51024_, p_51025_, p_51026_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AuroraMushroomEntity(pos,state);
    }
}
