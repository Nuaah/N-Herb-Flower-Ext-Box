package com.Nuaah.NHerbFlowerExtBox.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockStrippedJeweledBranchLog extends IntroductionLogBlock {

    public BlockStrippedJeweledBranchLog() {
        super(Properties.of()
                .strength(4F,6F)
                .requiresCorrectToolForDrops());
    }
}
