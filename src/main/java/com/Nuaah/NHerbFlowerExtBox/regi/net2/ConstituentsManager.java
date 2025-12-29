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

    public static void loadDataOnServer() {
        // ConstituentsJsonLoaderを使ってJSONを読み込む
        Map<String, ConstituentsData> loadedData = ConstituentsJsonLoader.CONSTITUENTS_DATA; // 恐らくConstituentsJsonLoader内にloadメソッドがあるはず

        serverData.clear();
        if (loadedData != null) {
            serverData.putAll(loadedData);
        }
        // デバッグ用: サーバーログにデータが読み込まれたか出力
        System.out.println("ConstituentsManager: Loaded " + serverData.size() + " entries on the server.");
    }

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
