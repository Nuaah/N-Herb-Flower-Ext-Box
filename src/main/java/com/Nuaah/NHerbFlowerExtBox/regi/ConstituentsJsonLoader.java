package com.Nuaah.NHerbFlowerExtBox.regi;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstituentsJsonLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    public static final ConstituentsJsonLoader INSTANCE = new ConstituentsJsonLoader();

    public static final Map<String, ConstituentsData> CONSTITUENTS_DATA = new HashMap<>();

    public ConstituentsJsonLoader() {
        super(GSON, "constituents");
    }

//    public static Map<String, ConstituentsData> loadFromConfigOrResources2() {
//        Map<String, ConstituentsData> result = new HashMap<>();
//
//        // =========================
//        // 1. config フォルダ優先
//        // =========================
//        Path dir = FMLPaths.CONFIGDIR.get()
//                .resolve("nherbflowerextbox")
//                .resolve("constituents");
//
//        if (Files.exists(dir) && Files.isDirectory(dir)) {
//            try (Stream<Path> paths = Files.list(dir)) {
//                paths
//                        .filter(p -> p.toString().endsWith(".json"))
//                        .forEach(path -> {
//                            String id = stripExtension(path.getFileName().toString());
//                            try {
//                                String json = Files.readString(path, StandardCharsets.UTF_8);
//                                ConstituentsData data =
//                                        GSON.fromJson(json, ConstituentsData.class);
//                                if (data != null) {
//                                    result.put(id, data);
//                                }
//                            } catch (Exception e) {
//                                System.err.println("[NHerb] Failed to load " + path);
//                                e.printStackTrace();
//                            }
//                        });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // config に1つでもあればそれを使う
//            if (!result.isEmpty()) {
//                return result;
//            }
//        }
//
//        // =========================
//        // 2. assets fallback
//        // =========================
//        try {
//            InputStream in = ConstituentsJsonLoader.class.getResourceAsStream(
//                    "/assets/nherbflowerextbox/constituents"
//            );
//
//            if (in != null) {
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(in, StandardCharsets.UTF_8))) {
//
//                    // assets 直読みは通常フォルダ列挙できないため
//                    // 単一 fallback 用（必要なら削除してOK）
//                    ConstituentsData data =
//                            GSON.fromJson(br, ConstituentsData.class);
//                    result.put("default", data);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    private static String stripExtension(String name) {
//        int i = name.lastIndexOf('.');
//        return (i > 0) ? name.substring(0, i) : name;
//    }
//
//    public static Map<String, ConstituentsData> loadFromConfigOrResources() {
//        // config/nherbflowerextbox/constituents/*.json
//        Path configDir = FMLPaths.CONFIGDIR.get().resolve("nherbflowerextbox").resolve("constituents");
//        Map<String, ConstituentsData> merged = new HashMap<>();
//
//        try {
//            if (Files.exists(configDir) && Files.isDirectory(configDir)) {
//                try (Stream<Path> paths = Files.list(configDir)) {
//                    paths.filter(p -> p.toString().endsWith(".json")).forEach(p -> {
//                        try {
//                            String content = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
//                            Type type = new TypeToken<Map<String, ConstituentsData>>() {
//                            }.getType();
//                            Map<String, ConstituentsData> map = GSON.fromJson(content, type);
//                            if (map != null) merged.putAll(map);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//                if (!merged.isEmpty()) {
//                    return merged;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // fallback: assets 内の単一ファイルを読む (jar 内)
//        try (InputStream in = ConstituentsJsonLoader.class.getResourceAsStream("/assets/nherbflowerextbox/constituents/constituents.json")) {
//            if (in != null) {
//                String text;
//                try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
//                    text = br.lines().collect(Collectors.joining("\n"));
//                }
//                Type type = new TypeToken<Map<String, ConstituentsData>>() {
//                }.getType();
//                Map<String, ConstituentsData> map = GSON.fromJson(text, type);
//                if (map != null) merged.putAll(map);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return merged;
//    }
//
//    public static void loadAllFromJar() {
//        CONSTITUENTS_DATA.clear();
//
//        String indexPath = "/assets/nherbflowerextbox/constituents/index.txt";
//
//        try (InputStream indexStream =
//                     ConstituentsJsonLoader.class.getResourceAsStream(indexPath)) {
//
//            if (indexStream == null) {
//                System.out.println("[ConstituentsJsonLoader] index.txt が見つかりません: " + indexPath);
//                return;
//            }
//
//            try (BufferedReader br =
//                         new BufferedReader(new InputStreamReader(indexStream, StandardCharsets.UTF_8))) {
//
//                String filename;
//                while ((filename = br.readLine()) != null) {
//                    filename = filename.trim();
//                    if (filename.isEmpty() || filename.startsWith("#")) continue;
//
//                    loadOneJson(filename);
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("[ConstituentsJsonLoader] Loaded " + CONSTITUENTS_DATA.size() + " JSON files.");
//    }
//
//    /** 個別 JSON を読み込む */
//    private static void loadOneJson(String filename) {
//        String path = "/assets/nherbflowerextbox/constituents/" + filename;
//
//        try (InputStream is =
//                     ConstituentsJsonLoader.class.getResourceAsStream(path)) {
//
//            if (is == null) {
//                System.out.println("[ConstituentsJsonLoader] File not found: " + path);
//                return;
//            }
//
//            try (BufferedReader br =
//                         new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
//
//                ConstituentsData data = GSON.fromJson(br, ConstituentsData.class);
//                if (data == null) return;
//
//                // id はファイル名から決める (bellflower.json → bellflower)
//                String id = filename.replace(".json", "");
//                CONSTITUENTS_DATA.put(id, data);
//
//            } catch (JsonSyntaxException e) {
//                System.out.println("[ConstituentsJsonLoader] JSON syntax error in: " + filename);
//                e.printStackTrace();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {

        CONSTITUENTS_DATA.clear();

        System.out.println("[Constituents] reload size = "
                + jsonMap.size());
        System.out.println(jsonMap.keySet());

        jsonMap.forEach((location, json) -> {
            ConstituentsData data = GSON.fromJson(json, ConstituentsData.class);
            CONSTITUENTS_DATA.put(location.getPath(), data);

            if (location.getPath().equals("bellflower")) {
                CONSTITUENTS_DATA.put("bellflower_powder", data);
                CONSTITUENTS_DATA.put("dried_bellflower", data);
            }

            if (location.getPath().equals("garden_marigold")) {
                CONSTITUENTS_DATA.put("garden_marigold_powder", data);
                CONSTITUENTS_DATA.put("dried_garden_marigold", data);
            }

            if (location.getPath().equals("shepherds_purse")) {
                CONSTITUENTS_DATA.put("shepherds_purse_powder", data);
                CONSTITUENTS_DATA.put("dried_shepherds_purse", data);
            }

            if (location.getPath().equals("lavender")) {
                CONSTITUENTS_DATA.put("lavender_powder", data);
                CONSTITUENTS_DATA.put("dried_lavender", data);
            }

            if (location.getPath().equals("ginseng")) {
                CONSTITUENTS_DATA.put("ginseng_powder", data);
                CONSTITUENTS_DATA.put("dried_ginseng", data);
            }

            if (location.getPath().equals("peppermint")) {
                CONSTITUENTS_DATA.put("peppermint_powder", data);
                CONSTITUENTS_DATA.put("dried_peppermint", data);
            }

            if (location.getPath().equals("moonflower")) {
                CONSTITUENTS_DATA.put("moonflower_powder", data);
                CONSTITUENTS_DATA.put("dried_moonflower", data);
            }

            if (location.getPath().equals("prickly_pear")) {
                CONSTITUENTS_DATA.put("prickly_pear_powder", data);
                CONSTITUENTS_DATA.put("dried_prickly_pear", data);
            }

            if (location.getPath().equals("peyote")) {
                CONSTITUENTS_DATA.put("peyote_powder", data);
                CONSTITUENTS_DATA.put("dried_peyote", data);
            }
        });


    }
}
