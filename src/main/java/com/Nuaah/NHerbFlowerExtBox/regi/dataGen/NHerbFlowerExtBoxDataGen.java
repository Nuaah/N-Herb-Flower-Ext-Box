package com.Nuaah.NHerbFlowerExtBox.regi.dataGen;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.plants.ModPlantGenSetting;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class NHerbFlowerExtBoxDataGen {

    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),new ModPlantGenSetting(packOutput,lookupProvider));

   }
}
