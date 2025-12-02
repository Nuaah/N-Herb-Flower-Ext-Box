package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

@SuppressWarnings("removal")
public class ModPlantPlacedFeature {
    public static final ResourceKey<PlacedFeature> BELLFLOWER_KEY = createKey("bellflower");
    public static final ResourceKey<PlacedFeature> GARDEN_MARIGOLD_KEY = createKey("garden_marigold");

    //生成高度の登録
    public static void bootstrap(BootstapContext<PlacedFeature> context){
        var bellflower_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.BELLFLOWER_KEY);
        var garden_marigold_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.GARDEN_MARIGOLD_KEY);

        // 配置ルールを記述 (count: 50, in_square, heightmap, biome)
        context.register(BELLFLOWER_KEY, new PlacedFeature(
                bellflower_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16), // または CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(GARDEN_MARIGOLD_KEY, new PlacedFeature(
                garden_marigold_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16), // または CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
    }


    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(NHerbFlowerExtBox.MOD_ID,name));
    }
}
