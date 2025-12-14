package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("removal")
public class ModPlantFeatureConfigurations {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BELLFLOWER_KEY = createKey("bellflower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GARDEN_MARIGOLD_KEY = createKey("garden_marigold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVENDER_KEY = createKey("lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHEPHERDS_PURSE_KEY = createKey("shepherds_purse");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINSENG_KEY = createKey("ginseng");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEYOTE_KEY = createKey("peyote");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRICKLY_PEAR_KEY = createKey("prickly_pear");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEPPERMINT_KEY = createKey("peppermint");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOONFLOWER_KEY = createKey("moonflower");

    // ここで中身を定義（Bootstrap）
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        // JSONで書いていた「鉄板構成」をここで再現
        context.register(BELLFLOWER_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                64, // tries
                7,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.BELLFLOWER.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(GARDEN_MARIGOLD_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                64, // tries
                7,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.GARDEN_MARIGOLD.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(LAVENDER_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                64, // tries
                7,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.LAVENDER.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(SHEPHERDS_PURSE_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                64, // tries
                7,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.SHEPHERDS_PURSE.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(GINSENG_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                32, // tries
                7,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.GINSENG.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(PRICKLY_PEAR_KEY, new ConfiguredFeature<>(
                Feature.FLOWER, // または random_patch
                new RandomPatchConfiguration(
                        16, // tries
                        30,  // xz_spread
                        3,  // y_spread
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.PRICKLY_PEAR.get()) // 自分のブロックを指定
                                )
                        )
                )
        ));

        context.register(PEYOTE_KEY, new ConfiguredFeature<>(
                Feature.FLOWER, // または random_patch
                new RandomPatchConfiguration(
                        16, // tries
                        30,  // xz_spread
                        3,  // y_spread
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.PEYOTE.get()) // 自分のブロックを指定
                                )
                        )
                )
        ));

        context.register(PEPPERMINT_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                32, // tries
                6,  // xz_spread
                3,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.PEPPERMINT.get()) // 自分のブロックを指定
                    )
                )
            )
        ));

        context.register(MOONFLOWER_KEY, new ConfiguredFeature<>(
            Feature.FLOWER, // または random_patch
            new RandomPatchConfiguration(
                8, // tries
                12,  // xz_spread
                10,  // y_spread
                PlacementUtils.onlyWhenEmpty(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            BlockStateProvider.simple(NHerbFlowerExtBoxBlocks.Blocks.MOONFLOWER.get()) // 自分のブロックを指定
                    )
                )
            )
        ));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(NHerbFlowerExtBox.MOD_ID, name));
    }
}
