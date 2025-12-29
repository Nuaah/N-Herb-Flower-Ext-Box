package com.Nuaah.NHerbFlowerExtBox.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class Atest extends BlockEntity {

    public Atest(BlockPos pos, BlockState state) {
        super(NHerbFlowerExtBoxEntityTypes.CLAY_CAULDRON.get(), pos,state);
    }

}
