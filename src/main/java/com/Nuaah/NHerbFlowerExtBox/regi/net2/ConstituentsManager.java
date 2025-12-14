package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstituentsManager {
    // サーバー側で読み込まれたデータを保持する（かつクライアントに送るデータ）
    private static Map<String, ConstituentsData> serverData = new HashMap<>();

    // クライアント側が受け取ったデータ（クライアントのみ）
    private static Map<String, ConstituentsData> clientData = new HashMap<>();

    // サーバー起動時に呼ぶ（ConstituentsJsonLoader を使ってロード）
//    public static void loadAll() {
//        Map<String, ConstituentsData> loaded = ConstituentsJsonLoader.loadFromConfigOrResources2();
//        if (loaded != null) {
//            serverData.clear();
//            serverData.putAll(loaded);
//        }
//    }

    public static Map<String, ConstituentsData> getServerData() {
        return Collections.unmodifiableMap(serverData);
    }

    public static void setClientData(Map<String, ConstituentsData> data) {
        clientData.clear();
        if (data != null) clientData.putAll(data);
    }

    public static Map<String, ConstituentsData> getClientData() {
        return Collections.unmodifiableMap(clientData);
    }
}
