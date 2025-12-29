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

    @Override
    public void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {

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

            if (location.getPath().equals("wither_rose")) {
                CONSTITUENTS_DATA.put("wither_rose_powder", data);
                CONSTITUENTS_DATA.put("dried_wither_rose", data);
            }

            if (location.getPath().equals("cornflower")) {
                CONSTITUENTS_DATA.put("cornflower_powder", data);
                CONSTITUENTS_DATA.put("dried_cornflower", data);
            }

            if (location.getPath().equals("lily_of_the_valley")) {
                CONSTITUENTS_DATA.put("lily_of_the_valley_powder", data);
                CONSTITUENTS_DATA.put("dried_lily_of_the_valley", data);
            }

            if (location.getPath().equals("dandelion")) {
                CONSTITUENTS_DATA.put("dandelion_powder", data);
                CONSTITUENTS_DATA.put("dried_dandelion", data);
            }

            if (location.getPath().equals("poppy")) {
                CONSTITUENTS_DATA.put("poppy_powder", data);
                CONSTITUENTS_DATA.put("dried_poppy", data);
            }

            if (location.getPath().equals("allium")) {
                CONSTITUENTS_DATA.put("allium_powder", data);
                CONSTITUENTS_DATA.put("dried_allium", data);
            }

            if (location.getPath().equals("palgant")) {
                CONSTITUENTS_DATA.put("palgant_powder", data);
                CONSTITUENTS_DATA.put("dried_palgant", data);
            }

            if (location.getPath().equals("eclaty")) {
                CONSTITUENTS_DATA.put("eclaty_powder", data);
                CONSTITUENTS_DATA.put("dried_eclaty", data);
            }

            if (location.getPath().equals("fireflies_mushroom")) {
                CONSTITUENTS_DATA.put("fireflies_mushroom_powder", data);
                CONSTITUENTS_DATA.put("dried_fireflies_mushroom", data);
            }

            if (location.getPath().equals("aurora_mushroom")) {
                CONSTITUENTS_DATA.put("aurora_mushroom_powder", data);
                CONSTITUENTS_DATA.put("dried_aurora_mushroom", data);
            }

            if (location.getPath().equals("pomium")) {
                CONSTITUENTS_DATA.put("pomium_powder", data);
                CONSTITUENTS_DATA.put("dried_pomium", data);
            }
        });
    }
}
