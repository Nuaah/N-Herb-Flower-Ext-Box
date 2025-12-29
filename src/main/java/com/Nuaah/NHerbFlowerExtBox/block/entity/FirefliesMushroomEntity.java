package com.Nuaah.NHerbFlowerExtBox.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FirefliesMushroomEntity extends BlockEntity {

    public FirefliesMushroomEntity(BlockPos pos, BlockState state) {
        super(NHerbFlowerExtBoxEntityTypes.FIREFLIES_MUSHROOM.get(), pos,state);
    }

}
