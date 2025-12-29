package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class IntroductionLogBlock extends RotatedPillarBlock {
    public IntroductionLogBlock(Properties p_55926_) {
        super(p_55926_);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.AXE_STRIP){
            return NHerbFlowerExtBoxBlocks.Blocks.STRIPPED_JEWELED_BRANCH_LOG.get().defaultBlockState()
                    .setValue(AXIS,state.getValue(AXIS));
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
