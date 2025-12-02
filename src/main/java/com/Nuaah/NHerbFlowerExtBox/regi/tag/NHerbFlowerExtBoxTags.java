package com.Nuaah.NHerbFlowerExtBox.regi.tag;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("removal")
public class NHerbFlowerExtBoxTags {
    public static class Blocks {
//        public static final TagKey<Block> ORES_AQUAMARINE = tag("ores_aquamarine");

        public static TagKey<Block> tag (String name){
            return BlockTags.create(new ResourceLocation(NHerbFlowerExtBox.MOD_ID,name));
        }
    }

    public static class Items {
        public static final TagKey<Item> HERBS = tag("herbs");
        public static final TagKey<Item> DRIED_HERBS = tag("dried_herbs");
        public static final TagKey<Item> HERB_POWDERS = tag("herb_powders");

        public static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(NHerbFlowerExtBox.MOD_ID, name));
        }
    }
}
