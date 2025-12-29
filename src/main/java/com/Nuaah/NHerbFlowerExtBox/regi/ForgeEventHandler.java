package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.ConstituentsManager;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.NetworkHandler;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.PacketSyncConstituents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
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

    // サーバーがスタートする前にデータをロード
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        System.out.println("SERVER OPEN");
        ConstituentsManager.loadDataOnServer();
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // ★ デバッグログを追加
        System.out.println("PlayerEventHandler: Player logged in event triggered for " + event.getEntity().getName().getString());

        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();

        Map<String, ConstituentsData> dataToSend = ConstituentsManager.getServerData();

        // ★ 送信するデータが空でないか確認
        System.out.println("PlayerEventHandler: Sending " + dataToSend.size() + " entries to player.");

        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new PacketSyncConstituents(dataToSend));
    }
}
