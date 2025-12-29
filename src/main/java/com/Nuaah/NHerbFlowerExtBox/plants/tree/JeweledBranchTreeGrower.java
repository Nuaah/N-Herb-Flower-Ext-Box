package com.Nuaah.NHerbFlowerExtBox.plants.tree;

import com.Nuaah.NHerbFlowerExtBox.plants.ModPlantFeatureConfigurations;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class JeweledBranchTreeGrower extends AbstractTreeGrower {
    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222910_, boolean p_222911_) {
        return ModPlantFeatureConfigurations.JEWELED_BRANCH_TREE_KEY;
    }
}
