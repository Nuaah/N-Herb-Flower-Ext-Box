package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.brewingRecipes.CustomBrewRecipe;
import com.Nuaah.NHerbFlowerExtBox.regi.brewingRecipes.CustomSplashBrewRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BrewingRecipes {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new CustomBrewRecipe());
            BrewingRecipeRegistry.addRecipe(new CustomSplashBrewRecipe());

        });
    }
}
