package com.Nuaah.NHerbFlowerExtBox.regi.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class PotionData implements CombinePotion {
    private final Map<String,Float> constituents = new HashMap<>();
    private final Map<String,Integer> durations = new HashMap<>();

    @Override
    public Map<String, Float> getConstituents() {
        return constituents;
    }

    @Override
    public Map<String, Integer> getDurations() {
        return durations;
    }

    @Override
    public void setConstituents(String name, float level) {
        if (constituents.get(name) != null){ //効果レベル
            float calc = constituents.get(name) + level;
            constituents.put(name,calc);

        } else {
            constituents.put(name,level);
        }
    }

    @Override
    public void setDurations(String name, int duration) {
        if (durations.get(name) != null){ //効果時間
            int calc = durations.get(name) + duration;
            durations.put(name,calc);
        } else {
            durations.put(name,duration);
        }
    }

    @Override
    public void clearPotion() {
        constituents.clear();
        durations.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<String, Float> entry : constituents.entrySet()) {
            mapTag.putFloat(entry.getKey(), entry.getValue());
        }

        nbt.put("Constituents", mapTag);

        CompoundTag mapTag2 = new CompoundTag();
        //効果時間
        for (Map.Entry<String, Integer> entry : durations.entrySet()) {
            mapTag2.putInt(entry.getKey(), entry.getValue());
        }

        nbt.put("Durations", mapTag2);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        constituents.clear();
        CompoundTag mapTag = nbt.getCompound("Constituents");
        for (String key : mapTag.getAllKeys()) {
            constituents.put(key, mapTag.getFloat(key));
        }

        durations.clear();
        CompoundTag mapTag2 = nbt.getCompound("Durations");
        for (String key : mapTag2.getAllKeys()) {
            durations.put(key, mapTag2.getInt(key));
        }
    }
}
