package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketSyncConstituents {
    private final Map<String, ConstituentsData> data;
    private static final Gson GSON = new Gson();

    public PacketSyncConstituents(Map<String, ConstituentsData> data) {
        this.data = data;
    }

    public static void encode(PacketSyncConstituents msg, FriendlyByteBuf buf) {
        // JSON にして送る（簡単）。大きくなる場合は別途圧縮/バイナリ化検討。
        String json = GSON.toJson(msg.data);
        buf.writeUtf(json);
    }

    public static PacketSyncConstituents decode(FriendlyByteBuf buf) {
        String json = buf.readUtf(32767);
        Type type = new TypeToken<Map<String, ConstituentsData>>() {}.getType();
        Map<String, ConstituentsData> map = GSON.fromJson(json, type);
        if (map == null) map = new HashMap<>();
        return new PacketSyncConstituents(map);
    }

    public static void handle(PacketSyncConstituents msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // ★ デバッグログを追加 (クライアント側で表示されるはず)
        System.out.println("PacketSyncConstituents: Received packet. Data size: " + msg.data.size());

        // クライアントスレッドで処理
        ctx.enqueueWork(() -> {
            // ★ デバッグログを追加 (クライアント側で表示されるはず)
            System.out.println("PacketSyncConstituents: Applying data to ConstituentsManager.");

            try {
                ConstituentsManager.setClientData(msg.data);

                ConstituentsJsonLoader.CONSTITUENTS_DATA.clear();
                ConstituentsJsonLoader.CONSTITUENTS_DATA.putAll(msg.data);

                // ★ セット後のデータサイズを確認 (クライアント側で表示されるはず)
                System.out.println("PacketSyncConstituents: Client data set. New size: " + ConstituentsManager.getClientData().size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.setPacketHandled(true);
    }
}
