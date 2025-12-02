package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MillstoneRenderer implements BlockEntityRenderer<MillstoneEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public MillstoneRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(MillstoneEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        // 1. 回転の中心へ移動 (ブロックの中心は 0.5, 0.5, 0.5)
        //    ※Blockbenchのピボット位置に合わせて微調整が必要です。
        //    通常、ブロックの中心で回すなら 0.5, 0.5, 0.5 に移動します。
        poseStack.translate(0.5, 0, 0.5);

        // 2. 回転処理 (Y軸回転)
        //    partialTickを使うことで滑らかに動きます
        float currentRotation = entity.rotate();
        // もしTick間の補完をして滑らかにするなら： + (entity.isSpinning ? partialTick * 5.0f : 0) など

        poseStack.mulPose(Axis.YP.rotationDegrees(-currentRotation));

        // 3. 元の位置に戻す (回転してから元の位置に戻して描画)
        poseStack.translate(-0.5, 0, -0.5);

        // 4. 回転部分のモデルを描画
        //    ここで「回転部専用のBlockState」を取得して描画します。
        //    ※ModBlocks.MILLSTONE_ROTOR は別途登録したダミーブロック、
        //      または回転部モデルを持つBlockStateです。
        BlockState rotorState = NHerbFlowerExtBoxBlocks.Blocks.MILLSTONE_TOP.get().defaultBlockState();

        this.blockRenderer.renderSingleBlock(
                rotorState,
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay
        );

        poseStack.popPose();
    }
}
