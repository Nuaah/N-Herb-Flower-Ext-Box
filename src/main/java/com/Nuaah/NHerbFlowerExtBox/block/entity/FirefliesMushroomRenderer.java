package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FirefliesMushroomRenderer implements BlockEntityRenderer<FirefliesMushroomEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public FirefliesMushroomRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(FirefliesMushroomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        Level level = entity.getLevel();
        BlockPos pos = entity.getBlockPos();
        if (level == null) return;

        BlockState state = entity.getBlockState();

//        poseStack.translate(0.5D, 0.0D, 0.5D);

        Vec3 offset = state.getOffset(level, pos);
        poseStack.translate(offset.x, offset.y, offset.z);

        BlockState rotorState = NHerbFlowerExtBoxBlocks.Blocks.FIREFLIES_MUSHROOM_TOP.get().defaultBlockState();

        this.blockRenderer.renderSingleBlock(
                rotorState,
                poseStack,
                bufferSource,
                LightTexture.FULL_BRIGHT,
                packedOverlay
        );

        poseStack.popPose();
    }
}
