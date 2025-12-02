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

@SuppressWarnings("removal")
public class ModPlantFeatureConfigurations {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BELLFLOWER_KEY = createKey("bellflower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GARDEN_MARIGOLD_KEY = createKey("garden_marigold");

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
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(NHerbFlowerExtBox.MOD_ID, name));
    }
}
