package com.Nuaah.NHerbFlowerExtBox.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class BlockPomium extends BushBlock {
    public static final VoxelShape SHAPE = Block.box(5,0,5,11,13,11);

    public BlockPomium() {
        super(Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .ignitedByLava());
    }

    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, dir, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader render, BlockPos pos) {
        // 1. 上のブロックの座標を取得
        BlockPos supportPos = pos.above();
        BlockState supportState = render.getBlockState(supportPos);

        // 2. 上のブロックが「面」としてしっかりしているか判定
        // FaceDetectorやisFaceSturdyを使用
        return supportState.isFaceSturdy(render, supportPos, Direction.DOWN);
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
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        DustParticleOptions dustOptions = new DustParticleOptions(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f); // 赤色
        if (random.nextInt(10) == 0){
            world.addParticle(ParticleTypes.DRIPPING_WATER
                    , pos.getX() + 0.5, pos.getY()  ,pos.getZ() + 0.5
                    ,0,0,0);
        }
    }
}
