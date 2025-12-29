package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.block.BlockMoonflower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MoonflowerEntity extends BlockEntity {

    public MoonflowerEntity(BlockPos pos, BlockState state)  {
        super(NHerbFlowerExtBoxEntityTypes.MOONFLOWER.get(), pos,state);
    }

    public boolean isNight(Level level){

        long dayTime = level.getDayTime() % 24000L; // 念のため24000で割った余りを取得

        boolean isNight = dayTime >= 13000;

        if (dayTime >= 23000 || dayTime < 1000) {
            // 深夜/夜明け (暗い)
        } else if (dayTime >= 13000 && dayTime < 23000) {
            return true;
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MoonflowerEntity be) {
        if (level.isClientSide) return; // サーバー側のみで実行

        // 現在が「夜」かどうかを判定 (13000 ~ 23000 が夜)
        long time = level.getDayTime() % 24000;
        boolean isNightNow = time >= 13000 && time <= 23000;

        // 現在のブロックの状態を取得
        boolean isBlockInNightMode = state.getValue(BlockMoonflower.IS_NIGHT);

        // 実際の時間とブロックの状態が一致していない場合のみ更新
        if (isNightNow != isBlockInNightMode) {
            level.setBlock(pos, state.setValue(BlockMoonflower.IS_NIGHT, isNightNow), 3);
        }
    }
}
