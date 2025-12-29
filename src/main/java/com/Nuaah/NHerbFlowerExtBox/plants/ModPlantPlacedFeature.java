package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

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
    public static final ResourceKey<PlacedFeature> PALGANT_KEY = createKey("palgant");
    public static final ResourceKey<PlacedFeature> ECLATY_KEY = createKey("eclaty");
    public static final ResourceKey<PlacedFeature> FIREFLIES_MUSHROOM_KEY = createKey("fireflies_mushroom");
    public static final ResourceKey<PlacedFeature> AURORA_MUSHROOM_KEY = createKey("aurora_mushroom");
    public static final ResourceKey<PlacedFeature> POMIUM_KEY = createKey("pomium");

    public static final ResourceKey<PlacedFeature> JEWELED_BRANCH_TREE_KEY = createKey("jeweled_branch_tree");

    //生成高度の登録
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var bellflower_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.BELLFLOWER_KEY);
        var garden_marigold_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.GARDEN_MARIGOLD_KEY);
        var lavender_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.LAVENDER_KEY);
        var shepherds_purse_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.SHEPHERDS_PURSE_KEY);
        var ginseng_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.GINSENG_KEY);
        var peyote_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PEYOTE_KEY);
        var prickly_pear_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PRICKLY_PEAR_KEY);
        var peppermint_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PEPPERMINT_KEY);
        var moonflower_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.MOONFLOWER_KEY);
        var palgant_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.PALGANT_KEY);
        var eclaty_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.ECLATY_KEY);
        var fireflies_mushroom_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.FIREFLIES_MUSHROOM_KEY);
        var aurora_mushroom_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.AURORA_MUSHROOM_KEY);
        var pomium_configured = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModPlantFeatureConfigurations.POMIUS_KEY);

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures =
                context.lookup(Registries.CONFIGURED_FEATURE);

        // 配置ルールを記述 (count: 50, in_square, heightmap, biome)
        context.register(BELLFLOWER_KEY, new PlacedFeature(
                bellflower_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16), //  CountPlacement.of(50)
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(GARDEN_MARIGOLD_KEY, new PlacedFeature(
                garden_marigold_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(LAVENDER_KEY, new PlacedFeature(
                lavender_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(SHEPHERDS_PURSE_KEY, new PlacedFeature(
                shepherds_purse_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(GINSENG_KEY, new PlacedFeature(
                ginseng_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PEYOTE_KEY, new PlacedFeature(
                peyote_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(6),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PRICKLY_PEAR_KEY, new PlacedFeature(
                prickly_pear_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(6),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PEPPERMINT_KEY, new PlacedFeature(
                peppermint_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(MOONFLOWER_KEY, new PlacedFeature(
                moonflower_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));

        context.register(PALGANT_KEY, new PlacedFeature(
                palgant_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(0)
                        ),
                        BiomeFilter.biome()
                )
        ));

        context.register(ECLATY_KEY, new PlacedFeature(
                eclaty_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(-18)
                        ),
                        BiomeFilter.biome()
                )
        ));

        context.register(FIREFLIES_MUSHROOM_KEY, new PlacedFeature(
                fireflies_mushroom_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(8),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(32)
                        ),
                        BiomeFilter.biome()
                )
        ));

        context.register(AURORA_MUSHROOM_KEY, new PlacedFeature(
                aurora_mushroom_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(8),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(24)
                        ),
                        BiomeFilter.biome()
                )
        ));

        context.register(POMIUM_KEY, new PlacedFeature(
                pomium_configured,
                List.of(
                        RarityFilter.onAverageOnceEvery(8),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(0),
                                VerticalAnchor.absolute(96)
                        ),
                        BiomeFilter.biome()
                )
        ));

        PlacementUtils.register(context, JEWELED_BRANCH_TREE_KEY
                , configuredFeatures.getOrThrow(ModPlantFeatureConfigurations.JEWELED_BRANCH_TREE_KEY)
                , VegetationPlacements.treePlacement(
                        PlacementUtils.countExtra(1, 0.1F, 0)
                        , NHerbFlowerExtBoxBlocks.Blocks.JEWELED_BRANCH_SAPLING.get())
        );
    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(NHerbFlowerExtBox.MOD_ID,name));
    }
}
