package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("removal")
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
            );

    private static int packetId = 0;

    public static int nextId() {
        return packetId++;
    }

    public static void register() {
        CHANNEL.messageBuilder(ClayCauldronEvaporatePacket.class,nextId())
            .encoder(ClayCauldronEvaporatePacket::encode)
            .decoder(ClayCauldronEvaporatePacket::decode)
            .consumerMainThread(ClayCauldronEvaporatePacket::handle)
            .add();

        CHANNEL.messageBuilder(PacketSyncConstituents.class,nextId())
            .encoder(PacketSyncConstituents::encode)
            .decoder(PacketSyncConstituents::decode)
            .consumerMainThread(PacketSyncConstituents::handle)
            .add();
    }
}
