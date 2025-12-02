package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.joml.Matrix4f;

import java.util.List;

@SuppressWarnings("removal")
public class ClayCauldronRenderer implements BlockEntityRenderer<ClayCauldronEntity> {

    public static final ResourceLocation WATER_TEXTURE = new ResourceLocation("block/water_still");
    private final BlockRenderDispatcher blockRenderer;

    public ClayCauldronRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ClayCauldronEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        float water = entity.getWater();
        float maxWater = entity.getMaxWater();
        List<Float> waterColor = entity.getWaterColor();

        if (water <= 0.0F) return;

        poseStack.pushPose();

        // バニラのブロック用テクスチャアトラスから、水のSpriteを取得
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(WATER_TEXTURE);

        // 半透明の描画バッファを取得
        VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());

        // 水面の色 (R, G, B, A) - ここでは標準的な水の青色
        // バイオームの色を反映させたい場合は BiomeColors を使いますが、今回は固定色にします
        float r = waterColor.get(0);
        float g = waterColor.get(1);
        float b = waterColor.get(2);

//        float r = 63f / 255f;
//        float g = 118f / 255f;
//        float b = 228f / 255f;
        float a = 1.0f; // 透明度はRenderTypeが処理するので1.0fでOK（あるいは薄くしたいなら下げる）

        // 水面の高さと範囲の設定
        // 例: ブロックの内側 2px ~ 14px の範囲、高さは 10px (0.625)
        float y = water / maxWater * 0.8F + 0.2F; // 水面の高さ (Y=0 が底、Y=1 が天井)
        float min = 0.125f; // 2/16
        float max = 0.875f; // 14/16

        Matrix4f matrix = poseStack.last().pose();

        // --- 上面の描画 (Top Face) ---
        // 頂点の順番は反時計回り (0,0 -> 1,0 -> 1,1 -> 0,1 の順) に注意

        // 頂点 1
        builder.vertex(matrix, min, y, min)
                .color(r, g, b, a)
                .uv(sprite.getU(min * 16), sprite.getV(min * 16)) // テクスチャのUV座標をワールド座標に合わせる
                .overlayCoords(packedOverlay)
                .uv2(packedLight) // 明るさ
                .normal(0, 1, 0) // 法線（上向き）
                .endVertex();

        // 頂点 2
        builder.vertex(matrix, min, y, max)
                .color(r, g, b, a)
                .uv(sprite.getU(min * 16), sprite.getV(max * 16))
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 1, 0)
                .endVertex();

        // 頂点 3
        builder.vertex(matrix, max, y, max)
                .color(r, g, b, a)
                .uv(sprite.getU(max * 16), sprite.getV(max * 16))
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 1, 0)
                .endVertex();

        // 頂点 4
        builder.vertex(matrix, max, y, min)
                .color(r, g, b, a)
                .uv(sprite.getU(max * 16), sprite.getV(min * 16))
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 1, 0)
                .endVertex();

        // ※ もし側面も見せたい場合（ガラスなど）は、同様に側面用の頂点を4つずつ追加します。
        // 大釜のように上からしか見ない場合は上面だけで十分です。

        poseStack.popPose();
    }
}
