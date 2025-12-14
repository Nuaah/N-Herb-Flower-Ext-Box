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
    public static final ResourceKey<PlacedFeature> LAVENDER_KEY = createKey("lavender");
    public static final ResourceKey<PlacedFeature> SHEPHERDS_PURSE_KEY = createKey("shepherds_purse");
    public static final ResourceKey<PlacedFeature> GINSENG_KEY = createKey("ginseng");
    public static final ResourceKey<PlacedFeature> PEYOTE_KEY = createKey("peyote");
    public static final ResourceKey<PlacedFeature> PRICKLY_PEAR_KEY = createKey("prickly_pear");
    public static final ResourceKey<PlacedFeature> PEPPERMINT_KEY = createKey("peppermint");
    public static final ResourceKey<PlacedFeature> MOONFLOWER_KEY = createKey("moonflower");

    //生成高度の登録
    public static void bootstrap(BootstapContext<PlacedFeature> context){
        var bellflower_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.BELLFLOWER_KEY);
        var garden_marigold_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.GARDEN_MARIGOLD_KEY);
        var lavender_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.LAVENDER_KEY);
        var shepherds_purse_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.SHEPHERDS_PURSE_KEY);
        var ginseng_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.GINSENG_KEY);
        var peyote_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PEYOTE_KEY);
        var prickly_pear_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PRICKLY_PEAR_KEY);
        var peppermint_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PEPPERMINT_KEY);
        var moonflower_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.MOONFLOWER_KEY);

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

        context.register(LAVENDER_KEY, new PlacedFeature(
            lavender_configured,
            List.of(
                RarityFilter.onAverageOnceEvery(16), // または CountPlacement.of(50)
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
            )
        ));

        context.register(SHEPHERDS_PURSE_KEY, new PlacedFeature(
            shepherds_purse_configured,
            List.of(
                RarityFilter.onAverageOnceEvery(16), // または CountPlacement.of(50)
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
            )
        ));

        context.register(GINSENG_KEY, new PlacedFeature(
            ginseng_configured,
            List.of(
                RarityFilter.onAverageOnceEvery(4), // または CountPlacement.of(50)
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
            )
        ));

        context.register(PEYOTE_KEY, new PlacedFeature(
                peyote_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(6), // または CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PRICKLY_PEAR_KEY, new PlacedFeature(
                prickly_pear_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(6), // または CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PEPPERMINT_KEY, new PlacedFeature(
                peppermint_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16), // または CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(MOONFLOWER_KEY, new PlacedFeature(
                moonflower_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(8), // または CountPlacement.of(50)
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
