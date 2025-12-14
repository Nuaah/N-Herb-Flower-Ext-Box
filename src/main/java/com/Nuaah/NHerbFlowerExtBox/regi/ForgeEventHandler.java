package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.ConstituentsManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Map;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ConstituentsJsonLoader.INSTANCE);
    }



//    @SubscribeEvent
//    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
//        if (!(event.getEntity() instanceof ServerPlayer player)) return;
//
//        Map<String, ConstituentsData> data = ConstituentsManager.getServerData();
//
//        NHerbFlowerExtBox.CHANNEL.send(
//            PacketDistributor.PLAYER.with(() -> player),
//            new PacketSyncConstituents(data)
//        );
//    }
    // プレイヤーがサーバーに参加（ログイン）した時に発火
//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            player.getServer().execute(() -> {
//                PacketSyncConstituents packet =
//                        new PacketSyncConstituents(ConstituentsJsonLoader.CONSTITUENTS_DATA);
//
//                NHerbFlowerExtBox.CHANNEL.send(
//                        PacketDistributor.PLAYER.with(() -> player),
//                        packet
//                );
//            });
//        }
//    }
//
//    // プレイヤーがログアウトした時にクライアントのキャッシュをクリア（シングルのデータ漏れ防止）
//    @SubscribeEvent
//    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
//        if (event.getEntity().level().isClientSide()) {
//            ConstituentsJsonLoader.CONSTITUENTS_DATA.clear();
//        }
//    }
}
