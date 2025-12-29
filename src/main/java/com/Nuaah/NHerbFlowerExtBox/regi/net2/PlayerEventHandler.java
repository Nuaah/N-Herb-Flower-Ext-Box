package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.Map;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        Map<String, ConstituentsData> dataToSend = ConstituentsManager.getServerData();
        // send to single player
        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new PacketSyncConstituents(dataToSend));
    }
}
