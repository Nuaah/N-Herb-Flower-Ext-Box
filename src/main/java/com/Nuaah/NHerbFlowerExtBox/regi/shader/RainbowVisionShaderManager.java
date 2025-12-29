package com.Nuaah.NHerbFlowerExtBox.regi.shader;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.mixin.PostChainMixin;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.List;

// Forgeのイベントバスに自動登録するためのアノテーション
@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class RainbowVisionShaderManager {

    private static PostChain shaderChain;
    public static final ResourceLocation SHADER_LOC =
            new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "shaders/post/rainbow_vision.json");

    // 直前の画面サイズを記録し、リサイズ検知に使用
    private static int lastWidth = 0;
    private static int lastHeight = 0;

    /**
     * レンダリングイベント (Tickイベントではなくこちらを使います)
     * ワールド描画の完了直後(AFTER_LEVEL)に割り込みます
     */
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        // ワールド描画が終わった段階でのみ実行
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // エフェクトを持っているかチェック
        if (mc.player.hasEffect(NHerbFlowerExtBoxEffect.RAINBOW_VISION.get())) {
            int level = mc.player.getEffect(NHerbFlowerExtBoxEffect.RAINBOW_VISION.get()).getAmplifier();
            renderShader(mc, event.getPartialTick(),level);
        } else {
            // エフェクトがないならシェーダーを解放
            cleanup();
        }
    }

    /**
     * シェーダーのロード、更新、描画を行うメイン処理
     */
    private static void renderShader(Minecraft mc, float partialTicks,int level) {
        // 1. シェーダーが未ロード、または画面サイズが変わった場合に再ロード
        if (shaderChain == null || mc.getWindow().getWidth() != lastWidth || mc.getWindow().getHeight() != lastHeight) {
            loadShader(mc);
        }

        if (shaderChain != null) {
            try {
                // 2. ユニフォーム変数の更新 (ここで時間を渡す！)
                updateUniforms(mc, partialTicks * level);

                // 3. シェーダーの実行
                shaderChain.process(partialTicks);

                // 4. マイクラのメインフレームバッファに戻す (これをしないと画面がおかしくなることがある)
                mc.getMainRenderTarget().bindWrite(false);

            } catch (Exception e) {
                // エラーが起きたら安全のために無効化してログ出力
                e.printStackTrace();
                cleanup();
            }
        }
    }

    private static void loadShader(Minecraft mc) {
        // 既存のものをクリーンアップ
        cleanup();

        try {
            // PostChainを手動で作成
            shaderChain = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), SHADER_LOC);

            // 画面サイズに合わせてリサイズ
            shaderChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());

            // サイズ記録
            lastWidth = mc.getWindow().getWidth();
            lastHeight = mc.getWindow().getHeight();

        } catch (IOException e) {
            shaderChain = null; // 失敗時はnullにしておく
        }
    }

    /**
     * ユニフォーム変数 (u_Time) を安全に更新する
     */
    private static void updateUniforms(Minecraft mc, float partialTicks) {
        if (shaderChain == null) return;

        // shaderChainをMixinインターフェースにキャスト
        PostChainMixin accessor = (PostChainMixin) shaderChain;
        List<PostPass> passes = accessor.getPasses();

        // リストが空でないことを確認
        if (passes.isEmpty()) return;

        // JSONの最初のパスを取得
        PostPass shaderPass = passes.get(0);
        // -----------------------------------------------------
        //        // Uniform変数の取得---

        Uniform timeUniform = shaderPass.getEffect().getUniform("u_Time");

        if (timeUniform != null) {

            // 1. シェーダープログラムのIDを取得 (Program object)
            int programId = shaderPass.getEffect().getId();

            // 2. プログラムをアクティブ化 (バインド)
            // GlStateManager.glUseProgram は GL30.glUseProgram のラッパー
            GlStateManager._glUseProgram(programId);

            // 3. 値の計算とセット
            float time = (mc.level.getGameTime() + partialTicks) * 0.05f;
            timeUniform.set(time);

            // 4. GPUに送信 (この時点でプログラムはアクティブ)
            timeUniform.upload();

            // 5. プログラムを非アクティブ化 (解放)
            // 0を渡すことで非アクティブな状態に戻します
            GlStateManager._glUseProgram(0);
        }
    }

    /**
     * リソース解放処理
     */
    private static void cleanup() {
        if (shaderChain != null) {
            shaderChain.close();
            shaderChain = null;
        }
        lastWidth = 0;
        lastHeight = 0;
    }
}