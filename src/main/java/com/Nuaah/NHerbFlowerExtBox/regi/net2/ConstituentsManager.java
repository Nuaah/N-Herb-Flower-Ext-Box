package com.Nuaah.NHerbFlowerExtBox.regi.net2;

import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstituentsManager {
    private static Map<String, ConstituentsData> serverData = new HashMap<>();

    private static Map<String, ConstituentsData> clientData = new HashMap<>();

    public static void loadDataOnServer() {
        // ConstituentsJsonLoaderを使ってJSONを読み込む
        Map<String, ConstituentsData> loadedData = ConstituentsJsonLoader.CONSTITUENTS_DATA;

        serverData.clear();
        if (loadedData != null) {
            serverData.putAll(loadedData);
        }

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
