package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.capability.NHerbFlowerExtBoxCapabilities;
import com.Nuaah.NHerbFlowerExtBox.regi.shader.BloodshotShaderManager;
import com.Nuaah.NHerbFlowerExtBox.regi.shader.MonochromeShaderManager;
import com.Nuaah.NHerbFlowerExtBox.regi.shader.RainbowVisionShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

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
                    int level = player.getEffect(NHerbFlowerExtBoxEffect.STAGGER.get()).getAmplifier();
                    inertiaForward -= Math.signum(inertiaForward) * (DECAY_RATE - level * 0.01F);

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

            // プレイヤーが存在しない場合は処理を中断
            if (mc.player == null) {
                // 念のためシェーダーを無効化
                mc.gameRenderer.shutdownEffect();
                return;
            }

            // --- 優先度チェック (高い順に実行) ---

            // 優先度 1: グレースケール (Monochrome)
            if (mc.player.hasEffect(NHerbFlowerExtBoxEffect.COLORBLIND.get())) {
                // グレースケールを適用し、ここで処理を終了（下のシェーダーはチェックしない）
                Minecraft.getInstance().gameRenderer.loadEffect(MonochromeShaderManager.SHADER);
                return;
            }

            // 優先度 2: 虹視 (Rainbow Vision)
            if (mc.player.hasEffect(NHerbFlowerExtBoxEffect.RAINBOW_VISION.get())) {
                // ブラッドショットを適用し、ここで処理を終了
                Minecraft.getInstance().gameRenderer.loadEffect(RainbowVisionShaderManager.SHADER_LOC);
                return;
            }

            // 優先度 3: ブラッドショット (Bloodshot)
            if (mc.player.hasEffect(NHerbFlowerExtBoxEffect.BLOODSHOT.get())) {
                // ブラッドショットを適用し、ここで処理を終了
                Minecraft.getInstance().gameRenderer.loadEffect(BloodshotShaderManager.SHADER);
                return;
            }

            // どちらの条件も満たさない場合：
            // 適用中のシェーダーがあれば無効化します
            mc.gameRenderer.shutdownEffect();
        }
    }



//    @SubscribeEvent
//    public static void onAttackEntity(AttackEntityEvent event) {
//        Player player = event.getEntity();
//        if (!(event.getTarget() instanceof LivingEntity target)) return;
//
//        ItemStack item = player.getMainHandItem();
//
//        if (player instanceof ServerPlayer serverPlayer) {
//            serverPlayer.sendSystemMessage(Component.literal("LIVI"));
//        }
//        System.out.println("ON");
//
//        item.getCapability(NHerbFlowerExtBoxCapabilities.POTION_CAP).ifPresent(cap -> {
//            int level = 0;
//            int duration = 0;
//
//            if (player instanceof ServerPlayer serverPlayer) {
//                serverPlayer.sendSystemMessage(Component.literal("LIVING攻撃検知: "));
//            }
//
//            System.out.println("ATTACK");
//
//            for (Map.Entry<String, Float> entry : cap.getConstituents().entrySet()) {
//                level = (int)Math.floor(entry.getValue());
//                duration = cap.getDurations().get(entry.getKey()) / 20;
//
//                if ((level-1) >= 0) {
//                    System.out.println("EEEEE");
//                    ResourceLocation effectId = new ResourceLocation(entry.getKey());
//                    MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);
//                    MobEffectInstance instance = new MobEffectInstance(effect, duration * 20, level-1);
//                    target.addEffect(instance);
//                }
//            }
//        });
//    }



    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ConstituentsJsonLoader.INSTANCE);
    }

}
