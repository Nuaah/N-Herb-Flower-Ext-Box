package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClayCauldronEvaporatePacket {

    private final BlockPos pos;

    public ClayCauldronEvaporatePacket(BlockPos pos) {
        this.pos = pos;
    }

    // 送信
    public static void encode(ClayCauldronEvaporatePacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    // 受信
    public static ClayCauldronEvaporatePacket decode(FriendlyByteBuf buf) {
        return new ClayCauldronEvaporatePacket(buf.readBlockPos());
    }

    // 処理（サーバー）
    public static void handle(ClayCauldronEvaporatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("CLAY NET");
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            var level = player.level();
            var be = level.getBlockEntity(msg.pos);

            if (be instanceof ClayCauldronEntity entity) {
                entity.switchEvaporate();
                entity.setChanged();
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
