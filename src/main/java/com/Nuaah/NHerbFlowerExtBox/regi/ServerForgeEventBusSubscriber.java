package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.capability.NHerbFlowerExtBoxCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
@SuppressWarnings("removal")
public class ServerForgeEventBusSubscriber {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ConstituentsJsonLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onLivingBreathe(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player player) {
            if (player.hasEffect(NHerbFlowerExtBoxEffect.SUFFOCATION.get())) {
                event.setCanBreathe(false);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 攻撃者がプレイヤーでないなら終了
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.literal("LIVI"));
        }

        // 攻撃対象
        LivingEntity target = event.getEntity();
        ItemStack item = player.getMainHandItem();

        // サーバー側のみで処理（効果付与はサーバー側で行えばクライアントに同期されます）
        if (!player.level().isClientSide) {
            item.getCapability(NHerbFlowerExtBoxCapabilities.POTION_CAP).ifPresent(cap -> {

                for (Map.Entry<String, Float> entry : cap.getConstituents().entrySet()) {
                    int level = (int) Math.floor(entry.getValue());
                    Integer durationTicks = cap.getDurations().get(entry.getKey());

                    if (durationTicks != null && level > 0) {
                        ResourceLocation effectId = new ResourceLocation(entry.getKey());
                        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);

                        if (effect != null) {
                            // durationTicksが既に20倍されているならそのまま、秒単位なら * 20
                            MobEffectInstance instance = new MobEffectInstance(effect, durationTicks, level - 1);
                            target.addEffect(instance);

                            // デバッグ用メッセージ
                            if (player instanceof ServerPlayer sp) {
                                sp.sendSystemMessage(Component.literal("効果付与: " + entry.getKey() + " Lv:" + level));
                            }
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(NHerbFlowerExtBoxEffect.PARALYSIS.get())) {

            // 1. 移動速度をゼロにする
            entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);

            // 2. もし完全に空中で止めたいなら Y軸も0にする
            // entity.setDeltaMovement(Vec3.ZERO);

            // 3. AIによる回転や移動を制限（1.20.1のメソッド名）
            if (entity instanceof Mob mob) {
                mob.setNoAi(true); // AIを完全に停止させる（もっとも強力）
            }
        } else {
            // 効果が切れたらAIを再開させる処理が必要な場合（NoAiを使った場合）
            if (entity instanceof Mob mob && mob.isNoAi()) {
                mob.setNoAi(false);
            }
        }

        if (event.getEntity() instanceof Player player) {

            // サーバー側でのみ実行 (クライアント側で実行すると同期問題が起こるため)
            if (player.level().isClientSide) {
                return;
            }

            if (!player.hasEffect(NHerbFlowerExtBoxEffect.SATIETY.get())) return;

            FoodData foodData = player.getFoodData();

            // FoodDataの内部メソッド setSaturation を使って、
            // Saturationレベルを常に最大にリセットする
            if (foodData.getSaturationLevel() <= 0) {
                foodData.setSaturation(0.5F);
            }
        }
    }
}