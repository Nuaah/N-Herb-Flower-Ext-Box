package com.Nuaah.NHerbFlowerExtBox.regi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class ConstituentsJsonLoader extends SimpleJsonResourceReloadListener {

    public static final ConstituentsJsonLoader INSTANCE = new ConstituentsJsonLoader();
    private static final Gson GSON = new GsonBuilder().create();

    public static final Map<String, ConstituentsData> CONSTITUENTS_DATA = new HashMap<>();

    public ConstituentsJsonLoader() {
        super(GSON, "constituents");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        CONSTITUENTS_DATA.clear();

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
        });


    }
}
