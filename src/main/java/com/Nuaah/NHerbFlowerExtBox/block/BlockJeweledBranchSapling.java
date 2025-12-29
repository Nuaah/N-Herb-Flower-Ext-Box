package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.plants.tree.JeweledBranchTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public class BlockJeweledBranchSapling extends SaplingBlock {

    public BlockJeweledBranchSapling() {
        super(new JeweledBranchTreeGrower(),Properties.of());
    }
}
