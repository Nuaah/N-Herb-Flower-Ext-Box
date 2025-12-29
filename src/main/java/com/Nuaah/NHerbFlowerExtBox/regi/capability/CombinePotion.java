package com.Nuaah.NHerbFlowerExtBox.regi.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public interface CombinePotion {
    Map<String,Float> getConstituents();
    Map<String,Integer> getDurations();

    void setConstituents(String name,float level);
    void setDurations(String name,int duration);

    void clearPotion();

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}
