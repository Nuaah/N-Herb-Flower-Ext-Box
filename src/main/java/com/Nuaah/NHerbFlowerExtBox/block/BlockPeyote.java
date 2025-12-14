package com.Nuaah.NHerbFlowerExtBox.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockPeyote extends BushBlock {

    public static final VoxelShape SHAPE = Block.box(5,0,5,11,13,11);

    public BlockPeyote() {
        super(Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
                .ignitedByLava()
                .noOcclusion());
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
        return belowState.is(Blocks.SAND);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        // サーバー側でのみ処理を行い、かつLivingEntity（プレイヤーやMob）のみ対象にする
        if (!world.isClientSide && entity instanceof LivingEntity) {

            // エンティティが動いている時のみダメージを与えたい場合は以下のようにチェックを入れる
            // スイートベリーは「動いた時」にダメージ判定が強くなる仕様ですが、
            // 単に「触れていればダメージ」ならこのif文(xOld != x...)は外してもOKです
            if (entity.xOld != entity.getX() || entity.zOld != entity.getZ()) {

                DamageSource damageSource = world.damageSources().cactus();

                // ダメージを与える (1.0F = ハート0.5個分)
                entity.hurt(damageSource, 1.0F);
            }
        }

        // オプション: スイートベリーのように「移動速度を低下」させたい場合
        // クライアント・サーバー両方で実行する必要があります
        entity.makeStuckInBlock(state, new Vec3(0.8D, 0.75D, 0.8D));
    }
}
