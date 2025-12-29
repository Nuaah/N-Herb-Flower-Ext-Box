package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class AuroraMushroomRenderer implements BlockEntityRenderer<AuroraMushroomEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public AuroraMushroomRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
        Random random = new Random();
    }

    @Override
    public void render(AuroraMushroomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        Level level = entity.getLevel();
        BlockPos pos = entity.getBlockPos();
        if (level == null) return;

        BlockState state = entity.getBlockState();

        // ★ ブロック中心
//        poseStack.translate(0.5D, 0.0D, 0.5D);

        // ★ Block と同じ offset を適用（これが核心）
        Vec3 offset = state.getOffset(level, pos);
        poseStack.translate(offset.x, offset.y, offset.z);

        BlockState rotorState = NHerbFlowerExtBoxBlocks.Blocks.AURORA_MUSHROOM_TOP.get().defaultBlockState();

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
