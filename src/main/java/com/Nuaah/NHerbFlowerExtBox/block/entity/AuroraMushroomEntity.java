package com.Nuaah.NHerbFlowerExtBox.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AuroraMushroomEntity extends BlockEntity {

    public AuroraMushroomEntity(BlockPos pos, BlockState state) {
        super(NHerbFlowerExtBoxEntityTypes.AURORA_MUSHROOM.get(), pos,state);
    }

}
