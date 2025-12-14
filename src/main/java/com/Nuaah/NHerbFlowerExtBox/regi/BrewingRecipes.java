package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BrewingRecipes {

    @SubscribeEvent
    public static void registerRecipes(RegisterEvent event) {
        net.minecraftforge.common.brewing.BrewingRecipeRegistry.addRecipe(new CustomBrewRecipe());
    }
}
