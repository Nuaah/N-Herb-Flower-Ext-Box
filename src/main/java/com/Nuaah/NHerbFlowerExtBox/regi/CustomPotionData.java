package com.Nuaah.NHerbFlowerExtBox.regi;

import net.minecraft.nbt.CompoundTag;

public class CustomPotionData {
    public final String name;
    public final float level;
    public final int duration;

    public CustomPotionData(String name,float level,int duration){
        this.name = name;
        this.level = level;
        this.duration = duration;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", name);
        tag.putFloat("Level", level);
        tag.putInt("Duration", duration);
        return tag;
    }

    public static CustomPotionData fromTag(CompoundTag tag) {
        return new CustomPotionData(
            tag.getString("Name"),
            tag.getFloat("Level"),
            tag.getInt("Duration")
        );
    }
}
