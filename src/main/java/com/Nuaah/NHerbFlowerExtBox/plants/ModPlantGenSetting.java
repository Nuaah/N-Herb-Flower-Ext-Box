package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModPlantGenSetting extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModPlantFeatureConfigurations::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlantPlacedFeature::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModPlantBiomeModifier::bootstrap);

    public ModPlantGenSetting(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NHerbFlowerExtBox.MOD_ID));
    }
}
