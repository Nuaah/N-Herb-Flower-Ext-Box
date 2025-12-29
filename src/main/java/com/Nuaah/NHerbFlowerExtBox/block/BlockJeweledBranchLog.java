package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockJeweledBranchLog extends IntroductionLogBlock {

    public BlockJeweledBranchLog() {
        super(Properties.of()
                .strength(4F,6F)
                .requiresCorrectToolForDrops());
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem){
            if (state.is(NHerbFlowerExtBoxBlocks.Blocks.JEWELED_BRANCH_LOG.get())){
                return NHerbFlowerExtBoxBlocks.Blocks.STRIPPED_JEWELED_BRANCH_LOG.get().defaultBlockState()
                        .setValue(AXIS,state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
