package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.List;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID, value = Dist.CLIENT)
public class EventHandler {

    // 慣性（残留入力）を保存する変数
    private static float inertiaForward = 0.0F; // 前後方向
    private static float inertiaStrafe = 0.0F;  // 左右方向

    // 減速率 (0.01F ~ 0.1F の間で調整)
    // 値が小さいほどツルツル滑ります（止まるまで時間がかかる）
    // 値が大きいほどすぐに止まります
    private static final float DECAY_RATE = 0.05F;

    // 入力が無視される閾値（完全に0にするタイミング）
    private static final float CUTOFF = 0.01F;

    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();

        if (player.hasEffect(NHerbFlowerExtBoxEffect.CONFUSION.get())) {
            float originalForward = input.forwardImpulse;
            float originalLeft = input.leftImpulse;

            //値を入れ替える
            // 左右入力 → 前後の動きに適用
            input.leftImpulse = originalForward;
            input.forwardImpulse = originalLeft;
        }

        if (player.hasEffect(NHerbFlowerExtBoxEffect.PARALYSIS.get())) {
            input.leftImpulse = 0;
            input.forwardImpulse = 0;
        }

        if (player.hasEffect(NHerbFlowerExtBoxEffect.STAGGER.get())) {
            // --- 前後の慣性処理 (W/S) ---
            if (input.forwardImpulse == 0.0F) {
                // キーを離している場合：慣性を適用して減速させる
                if (Math.abs(inertiaForward) > CUTOFF) {
                    // 現在の慣性から減速率を引く（符号に合わせて引く）
                    inertiaForward -= Math.signum(inertiaForward) * DECAY_RATE;

                    // プレイヤーの入力値を慣性値で上書きする
                    input.forwardImpulse = inertiaForward;
                } else {
                    inertiaForward = 0.0F; // 完全に停止
                }
            } else {
                // キーを押している場合：現在の入力を慣性変数に保存
                inertiaForward = input.forwardImpulse;
            }

            // --- 左右の慣性処理 (A/D) ---
            if (input.leftImpulse == 0.0F) {
                // キーを離している場合
                if (Math.abs(inertiaStrafe) > CUTOFF) {
                    inertiaStrafe -= Math.signum(inertiaStrafe) * DECAY_RATE;
                    input.leftImpulse = inertiaStrafe;
                } else {
                    inertiaStrafe = 0.0F;
                }
            } else {
                // キーを押している場合
                inertiaStrafe = input.leftImpulse;
            }

            // オプション: ジャンプ中は慣性を効かせない、などの処理を入れることも可能
            if (input.jumping) {
                // inertiaForward = 0; // ジャンプしたら慣性を切る場合
            }

        } else {
            // 条件を満たしていない場合は慣性をリセット
            inertiaForward = 0.0F;
            inertiaStrafe = 0.0F;
        }
    }

    @SubscribeEvent
    public static void onUseItemStart(LivingEntityUseItemEvent.Start event) {
        // プレイヤーのみ対象にする
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack itemStack = event.getItem();

        // 1. そのアイテムが「食べ物」かどうかチェック
        // isEdible() は FoodProperties を持つアイテム（肉、パン、ポーション以外の食べ物）なら true
        if (itemStack.isEdible()) {

            if (player.hasEffect(NHerbFlowerExtBoxEffect.ANOREXIA.get())) {
                // 3. イベントをキャンセルして食事を阻止する
                event.setCanceled(true);
            }
        }
    }

    private static final ResourceLocation CUSTOM_GRAYSCALE_SHADER = new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "shaders/post/grayscale.json");

    // シェーダーが現在適用されているかどうかのフラグ
    private static boolean isShaderActive = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return; // プレイヤーがまだ生成されていない場合は何もしない

            MonochromeShaderManager.tick();
        }
//        if (event.phase != TickEvent.Phase.END) return;
//
//        Minecraft mc = Minecraft.getInstance();
//        Player player = mc.player;
//
//        if (player == null || mc.isPaused()) return;
//
//        GameRenderer gameRenderer = mc.gameRenderer;
//
//        // ----------------------------------------------------
//        // 1. 条件判定と強度の設定 (ここを調整)
//        // ----------------------------------------------------
//        // 例として、常に適用し、強度は 75% モノクロに設定します。
//        boolean conditionMet = true;
//        float targetGrayscale = 0.75f; // 0.0f (カラー) から 1.0f (完全モノクロ)
//
//        // ----------------------------------------------------
//        // 2. シェーダーの適用と解除
//        // ----------------------------------------------------
//        if (conditionMet) {
//            // まだ適用されていなければロード
//            if (!isShaderActive) {
//                gameRenderer.loadEffect(CUSTOM_GRAYSCALE_SHADER);
//                isShaderActive = true;
//            }
//
//            // 適用中なら強度を更新
//            if (isShaderActive) {
//                try {
//                    // 1. 現在適用されているポストプロセスチェーンを取得
//                    PostChain postChain = gameRenderer.currentEffect();
//
//                    if (postChain != null) {
//                        // 2. リフレクションを使って 'passes' (シェーダーの工程リスト) を取得
//                        // "passes" というフィールド名は難読化されている可能性があるため、本来はSRG名が必要ですが、
//                        // 開発環境では "passes" で動くことが多いです。エラーが出る場合はSRG名("f_110009_")を使います。
//                        List<PostPass> passes = ObfuscationReflectionHelper.getPrivateValue(
//                            PostChain.class,
//                            postChain,
//                            "passes" // 開発環境用。本番でエラーが出るなら "f_110009_"
//                        );
//
//                        // 3. 全てのパスを走査して、目的のUniformを探す
//                        if (passes != null) {
//                            for (PostPass pass : passes) {
//                                // 各パスが持っているシェーダーインスタンスからUniformを取得
//                                Uniform uniform = pass.getEffect().getUniform("GrayscaleAmount");
//
//                                if (uniform != null) {
//                                    uniform.set(targetGrayscale);
//                                    // 変更を適用する必要がある場合は upload などを呼ぶこともあるが、
//                                    // set だけでも次の描画フレームで反映されることが多い
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    // リフレクション失敗時のエラーログ
//                    e.printStackTrace();
//                }
//            }
//
//        } else {
//            // 解除処理
//            if (isShaderActive) {
//                gameRenderer.shutdownEffect();
//                isShaderActive = false;
//            }
//        }
    }

//    // バニラ標準の「彩度を下げる」シェーダー
//    private static final ResourceLocation DESATURATE_SHADER = new ResourceLocation("shaders/post/desaturate.json");
//
//    // シェーダーが現在適用されているかどうかのフラグ
//    private static boolean isShaderActive = false;
//
//    @SubscribeEvent
//    public static void onClientTick(TickEvent.ClientTickEvent event) {
//        // ティックの終了時にのみ処理を行う
//        if (event.phase != TickEvent.Phase.END) return;
//
//        Minecraft mc = Minecraft.getInstance();
//        Player player = mc.player;
//
//        // プレイヤーが存在しない、またはポーズ中は処理しない
//        if (player == null || mc.isPaused()) return;
//
//        GameRenderer gameRenderer = mc.gameRenderer;
//
//        // ----------------------------------------------------
//        // 1. 条件判定 (ここをあなたの条件に変えてください)
//        // ----------------------------------------------------
//        // 例: プレイヤーがあなたのMODのエフェクトを持っている場合
//        // boolean conditionMet = player.hasEffect(ModEffects.YOUR_EFFECT.get());
//
//        // テスト用: 常に true (常にモノクロになる)
//        boolean conditionMet = true;
//
//        // ----------------------------------------------------
//        // 2. シェーダーの適用と解除
//        // ----------------------------------------------------
//        if (conditionMet) {
//            // 条件を満たしており、まだシェーダーが適用されていない場合
//            if (!isShaderActive) {
//                // シェーダーを読み込む
//                gameRenderer.loadEffect(DESATURATE_SHADER);
//                isShaderActive = true;
//            }
//        } else {
//            // 条件を満たしておらず、シェーダーが適用中の場合
//            if (isShaderActive) {
//                // シェーダーを解除する
//                gameRenderer.shutdownEffect();
//                isShaderActive = false;
//            }
//        }
//    }
}
