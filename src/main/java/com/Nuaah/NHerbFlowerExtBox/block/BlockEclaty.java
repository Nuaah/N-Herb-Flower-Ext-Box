package com.Nuaah.NHerbFlowerExtBox.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

public class BlockEclaty extends BushBlock {

    public static final VoxelShape SHAPE = Block.box(2,0,2,14,13,14);

    public BlockEclaty() {
        super(Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .ignitedByLava()
            .lightLevel((a) -> 10));

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
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0){
            world.addParticle(ParticleTypes.END_ROD
                , pos.getX() + 0.5 + random.nextInt(-5,5) * 0.1, pos.getY() + 0.5 ,pos.getZ() + 0.5 + random.nextInt(-5,5) * 0.1
                ,0,0,0);
        }
    }
}
