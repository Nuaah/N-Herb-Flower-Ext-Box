package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("removal")
public class ModPlantBiomeModifier {
    public static final ResourceKey<BiomeModifier> BELLFLOWER_KEY = createKey("bellflower");
    public static final ResourceKey<BiomeModifier> GARDEN_MARIGOLD_KEY = createKey("garden_marigold");
    public static final ResourceKey<BiomeModifier> LAVENDER_KEY = createKey("lavender");
    public static final ResourceKey<BiomeModifier> SHEPHERDS_PURSE_KEY = createKey("shepherds_purse");
    public static final ResourceKey<BiomeModifier> GINSENG_KEY = createKey("ginseng");
    public static final ResourceKey<BiomeModifier> PEYOTE_KEY = createKey("peyote");
    public static final ResourceKey<BiomeModifier> PRICKLY_PEAR_KEY = createKey("prickly_pear");
    public static final ResourceKey<BiomeModifier> PEPPERMINT_KEY = createKey("peppermint");
    public static final ResourceKey<BiomeModifier> MOONFLOWER_KEY = createKey("moonflower");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        // Biome/PlacedFeature の参照をセットアップ
        // Biome のタグを管理する Registry を参照 (例: 全てのオーバーワールドのバイオーム)
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        // PlacedFeature の Registry を参照
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        TagKey<Biome> IS_DESERT = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath("forge", "is_desert"));

        // Biome Modifier の登録
        context.register(BELLFLOWER_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                // Biome の指定: オーバーワールドの全てのバイオームタグを指定
                // BiomeTags.IS_OVERWORLD の代わりに、特定のタグ (例: BiomeTags.IS_FOREST) を使うことも可能
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.BELLFLOWER_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(GARDEN_MARIGOLD_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

            // Placed Feature の指定: 作成した bellflower_placed を参照
            HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.GARDEN_MARIGOLD_KEY)),

            // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(LAVENDER_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.LAVENDER_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(SHEPHERDS_PURSE_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.SHEPHERDS_PURSE_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(GINSENG_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

            biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),

            // Placed Feature の指定: 作成した bellflower_placed を参照
            HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.GINSENG_KEY)),

            // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(PEYOTE_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(IS_DESERT),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.PEYOTE_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(PRICKLY_PEAR_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(IS_DESERT),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.PRICKLY_PEAR_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(PEPPERMINT_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.PEPPERMINT_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(MOONFLOWER_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.MOONFLOWER_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    public static ResourceKey<BiomeModifier> createKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,new ResourceLocation(NHerbFlowerExtBox.MOD_ID,name));
    }
}
